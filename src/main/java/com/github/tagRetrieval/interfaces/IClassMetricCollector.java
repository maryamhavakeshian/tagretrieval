package com.github.tagRetrieval.interfaces;

import com.github.javaparser.ast.CompilationUnit;
import com.github.tagRetrieval.model.AMReport;
import com.github.tagRetrieval.model.ClassModel;


public interface IClassMetricCollector {
	
	void execute(CompilationUnit cu, ClassModel result, AMReport report);
	void setResult(ClassModel result);
	
}
