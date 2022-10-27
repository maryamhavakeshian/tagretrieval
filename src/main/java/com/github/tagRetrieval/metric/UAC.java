package com.github.tagRetrieval.metric;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.tagRetrieval.interfaces.IClassMetricCollector;
import com.github.tagRetrieval.model.AMReport;
import com.github.tagRetrieval.model.ClassModel;

import java.util.HashSet;
import java.util.Set;


public class UAC extends VoidVisitorAdapter<Object> implements IClassMetricCollector {

    Set<String> uniqueAnnotations = new HashSet<>();

    @Override
    public void visit(MarkerAnnotationExpr node, Object obj) {
        uniqueAnnotations.add(node.getNameAsString());
        super.visit(node, obj);
    }

    @Override
    public void visit(NormalAnnotationExpr node, Object obj) {
        uniqueAnnotations.add(node.getTokenRange().get().toString().replaceAll("(\t|\n)", ""));
        super.visit(node, obj);
    }

    @Override
    public void visit(SingleMemberAnnotationExpr node, Object obj) {
        uniqueAnnotations.add(node.getTokenRange().get().toString().replaceAll("(\t|\n)", ""));
        super.visit(node, obj);
    }


    @Override
    public void execute(CompilationUnit cu, ClassModel result, AMReport report) {
        this.visit(cu, null);
    }

    @Override
    public void setResult(ClassModel result) {
        result.addClassMetric("UAC", uniqueAnnotations.size());

    }
}