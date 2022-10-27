package com.github.tagRetrieval.interfaces;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.tagRetrieval.model.CodeElementModel;

public interface ICodeElementMetricCollector {

    public void execute(CompilationUnit cu, CodeElementModel codeElementMetricModel, Node codeElementNode);

}
