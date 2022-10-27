package com.github.tagRetrieval.metric;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.tagRetrieval.interfaces.ICodeElementMetricCollector;
import com.github.tagRetrieval.model.CodeElementModel;
import com.github.tagRetrieval.utils.AnnotationUtils;

public class AED implements ICodeElementMetricCollector {

    @Override
    public void execute(CompilationUnit cu, CodeElementModel codeElementMetricModel, Node codeElementNode) {

        int aed = AnnotationUtils.checkForAnnotations(codeElementNode).size();

//        codeElementMetricModel.setAed(aed);

    }
}
