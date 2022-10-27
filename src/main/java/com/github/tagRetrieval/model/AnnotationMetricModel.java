package com.github.tagRetrieval.model;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationMetricModel {

	private String annotationName;
//	private List<String> annotationValue = new ArrayList<>();
	private Map<String, String> annotationValue = new HashMap<String, String>();
	private int sourceCodeLine;

	private String schema;

//	private Map<String, Integer> annotationMetrics = new HashMap<String, Integer>();


	
	public   <E extends AnnotationExpr> AnnotationMetricModel(String annotationName, int sourceCodeLine, String schema, E annotationExpr) {
		this.annotationName = annotationName;

		this.sourceCodeLine = sourceCodeLine;
		this.schema = schema;

		if (annotationExpr.isNormalAnnotationExpr()) {
			NodeList<MemberValuePair> pairs = annotationExpr.asNormalAnnotationExpr().getPairs();
			SimpleName name;
			Expression value;
			for (MemberValuePair pair : pairs) {
				name = pair.getName(); // "param1"
				value = pair.getValue(); // "value1"
				annotationValue.put(name.toString(),value.toString());
				System.out.println("name = " + name + ", value = " + value);
			}
		} else if (annotationExpr.isSingleMemberAnnotationExpr()) {
			SingleMemberAnnotationExpr singleMemberAnnotationExpr = annotationExpr.asSingleMemberAnnotationExpr();
			Expression memberValue = singleMemberAnnotationExpr.getMemberValue();
			String memberValueName = singleMemberAnnotationExpr.getName().getIdentifier();
			annotationValue.put(memberValueName,memberValue.toString());

		}
	}
//	public AnnotationMetricModel(String annotationName, int sourceCodeLine, String schema, List<String> annotationValue) {
//		this.annotationName = annotationName;
//		this.annotationValue = annotationValue;
//		this.sourceCodeLine = sourceCodeLine;
//		this.schema = schema;
//	}
//	public void addAnnotationMetric(String metricName, int metricValue) {
//		annotationMetrics.put(metricName, metricValue);
//	}
//
//	public Map<String, Integer> getAnnotationMetrics() {
//		return annotationMetrics;
//	}

//		public void addAnnotationValue(String annotationIdentifier, String annotationExpressionValue) {
//		annotationValue.put(annotationIdentifier, annotationExpressionValue);
//	}

//	public Map<String, Integer> getAnnotationMetrics() {
//		return annotationMetrics;
//	}



	public String getName() {
		return this.annotationName;
	}

	public int getLine() {
		return this.sourceCodeLine;
	}
	
	public String getSchema() {
		return schema;
	}
}