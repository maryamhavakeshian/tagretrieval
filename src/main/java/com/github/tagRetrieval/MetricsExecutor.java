package com.github.tagRetrieval;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import com.github.tagRetrieval.interfaces.IClassMetricCollector;
import com.github.tagRetrieval.interfaces.ICodeElementMetricCollector;
import com.github.tagRetrieval.metric.LOCCalculator;
import com.github.tagRetrieval.model.*;
import com.github.tagRetrieval.utils.AnnotationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class MetricsExecutor {

    private AMReport report;
    private Map<String, PackageModel> packagesModel;
    private ClassModel result = null;
    private Callable<List<IClassMetricCollector>> classMetrics;
    private List<ICodeElementMetricCollector> codeElementMetrics;

    private static final Logger logger = LogManager.getLogger(MetricsExecutor.class);

    public MetricsExecutor(
            Callable<List<IClassMetricCollector>> classMetrics,
            List<ICodeElementMetricCollector> codeElementMetrics,
            String projectName
    ) {
        this.classMetrics = classMetrics;
        this.codeElementMetrics = codeElementMetrics;
        this.report = new AMReport(projectName);
        this.packagesModel = new HashMap<String, PackageModel>();
    }


    public void accept(List<CompilationUnit> compilationUnitList) {
        compilationUnitList.forEach(cu -> {
            try {
                ClassInfo info = new ClassInfo(cu);
                cu.accept(info, null);

                if (info.getClassName() == null) return;

                String packageName = info.getPackageName();

                PackageModel packageModel = getPackageModel(packageName);

                String sourceFilePath = cu.getStorage().get().getPath().toString();
                int loc = new LOCCalculator().calculate(new FileInputStream(sourceFilePath));
                int nec = info.getCodeElementsInfo().size();

                result = new ClassModel(sourceFilePath, info.getClassName(), info.getType(), loc, nec);
                logger.info("Initializing extraction of annotationSchemaMaps//class metrics for class: " + info.getClassName());
                //Obtain class metrics
                for (IClassMetricCollector visitor : classMetrics.call()) {
                    visitor.execute(cu, result, report);
                    visitor.setResult(result);
                }
                logger.info("Finished extracting annotationSchemaMaps//class metrics.");
                info.getCodeElementsInfo().entrySet().forEach(entry -> {
                    Node codeElementBody = entry.getKey();
                    CodeElementModel codeElementModel = entry.getValue();
                    logger.info("Initializing extraction of code element metrics for element: " + codeElementModel.getElementName());
                    //Obtain code element metrics
                    for (ICodeElementMetricCollector visitor : codeElementMetrics) {
                        visitor.execute(cu, codeElementModel, codeElementBody);
                    }
                    logger.info("Finished extraction of code element metrics for element: " + codeElementModel.getElementName());
                    //Annotation Metrics
                    List<AnnotationExpr> annotations = AnnotationUtils.checkForAnnotations(codeElementBody);
                    List<AnnotationExpr> singleMemberAnnotationExpr = annotations.stream().filter(antn -> antn.isSingleMemberAnnotationExpr()).collect(Collectors.toList());
                    List<AnnotationExpr> normalAnnotationExpr = annotations.stream().filter(antn -> antn.isNormalAnnotationExpr()).collect(Collectors.toList());

                    logger.info("Initializing extraction of annotation metrics for code element: " + codeElementModel.getElementName());

                    singleMemberAnnotationExpr.forEach(annotation -> {
                        AnnotationMetricModel annotationMetricModel = new AnnotationMetricModel(
                                annotation.getName().getIdentifier(),
                                annotation.getTokenRange().get().toRange().get().begin.line,
                                result.getAnnotationSchema(annotation.getName().getIdentifier()
                                        + "-" + annotation.getTokenRange().get().toRange().get().begin.line),
                                annotation);

                        codeElementModel.addAnnotationMetric(annotationMetricModel);
                    });

                    normalAnnotationExpr.forEach(annotation -> {
                        AnnotationMetricModel annotationMetricModel = new AnnotationMetricModel(
                                annotation.getName().getIdentifier(),
                                annotation.getTokenRange().get().toRange().get().begin.line,
                                result.getAnnotationSchema(annotation.getName().getIdentifier()
                                        + "-" + annotation.getTokenRange().get().toRange().get().begin.line),
                                annotation);
                        codeElementModel.addAnnotationMetric(annotationMetricModel);
                    });





                    logger.info("Finished extraction of annotation metrics for code element: " + codeElementModel.getElementName());
                    result.addElementReport(codeElementModel);
                });

                packageModel.addClassModel(result);
                report.addPackageModel(packageModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public AMReport getReport() {
        return report;
    }

    private PackageModel getPackageModel(String packageName) {
        if (packagesModel.containsKey(packageName))
            return packagesModel.get(packageName);
        PackageModel packageModel = new PackageModel(packageName);
        packagesModel.put(packageName, packageModel);
        return packageModel;
    }

}