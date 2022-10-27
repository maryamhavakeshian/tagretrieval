package com.github.tagRetrieval.metric;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.tagRetrieval.interfaces.IClassMetricCollector;
import com.github.tagRetrieval.model.AMReport;
import com.github.tagRetrieval.model.ClassModel;
import com.github.tagRetrieval.utils.AnnotationsGlossary;
import com.google.common.collect.ImmutableSet;

import java.util.*;


public class ASC extends VoidVisitorAdapter<Object> implements IClassMetricCollector {


	List<ImportDeclaration> imports = new ArrayList<>();
	HashMap<String, String> schemasMapper = new HashMap<>();
	CompilationUnit cu;

	//predefined java annotations
	private static Set<String> javaLangPredefined = ImmutableSet.of("Override","Deprecated","SuppressWarnings","SafeVarargs","FunctionalInterface");

	@Override
	public void visit(MarkerAnnotationExpr node, Object obj) {
		findSchema(node);
		super.visit(node, obj);
	}
	
	@Override
	public void visit(NormalAnnotationExpr node, Object obj) {
//		NormalAnnotationExpr normalAnnotationExpr = node.asNormalAnnotationExpr();
		// A "Normal" annotation has one or more keys and values (e.g. `@Annotation3(param1 = "value1", param2 = "value2")`)
//		NodeList<MemberValuePair> pairs = normalAnnotationExpr.getPairs();
		NodeList<MemberValuePair> pairs = node.getPairs();
		SimpleName name ;
		Expression value;
		for(MemberValuePair pair: pairs){
			name = pair.getName(); // "param1"
			value = pair.getValue(); // "value1"
			System.out.println("name = " + name + ", value = " + value);

		}

		findSchema(node);
		super.visit(node, obj);
	}
	
	@Override
	public void visit(SingleMemberAnnotationExpr node, Object obj) {
//		SingleMemberAnnotationExpr singleMemberAnnotationExpr = node.asSingleMemberAnnotationExpr();
//		Expression memberValue =singleMemberAnnotationExpr.getMemberValue();
		String parentName = node.getParentNode().get().getClass().getPackageName();
		String parentName1 = node.getParentNode().get().getClass().getSimpleName();
//		String parentName3 = node.getParentNode().get().getClass().getSimpleName();
		Expression memberValue = node.getMemberValue();
		System.out.println("memberValue = " + memberValue);
		findSchema(node);
		super.visit(node, obj);
	}

	@Override
	public void execute(CompilationUnit cu, ClassModel result, AMReport report) {
		findImports(cu);
		this.cu = cu;
		this.visit(cu, null);
	}

	@Override
	public void setResult(ClassModel result) {

		result.setSchemas(schemasMapper);
		result.addClassMetric("ASC", result.getAnnotationSchemas().size());
		
	}


	
	private void findSchema(AnnotationExpr annotation) {

		String qualifier = annotation.getName().getQualifier().isPresent() ? getQualifier(annotation) : "";
//		String annotationValue = "";//= annotation.getName().getQualifier().isPresent() ? getQualifier(annotation) : "";
//		String annotationName ="";
		int annotationLineNumber = annotation.getTokenRange().get().toRange().get().begin.line;
		String annotationName = qualifier.isEmpty() ? annotation.getNameAsString() : annotation.getName().asString().replaceFirst(qualifier.concat("\\."),"");
		String annotationNameAndLine = annotationName + "-" + annotationLineNumber;

		if (!qualifier.isEmpty()) {
			schemasMapper.put(annotationNameAndLine, qualifier);
			return;
		}
		
		//check if annotations was imported
		for (ImportDeclaration import_ : imports) {
			String annotationNameTemp = annotationName;
			if(annotationName.contains("."))//it is inner annotations declaration. THe first name should be mapped to the import
				annotationNameTemp = annotationName.substring(0,annotationName.indexOf("."));
			if (import_.getName().getIdentifier().equals(annotationNameTemp)){
				import_.getName().getQualifier().ifPresent(s -> {
					schemasMapper.put(annotationNameAndLine,s.toString());
				});
				return;
			}
		}

		String schema = "";
		
		Optional<String> wildCardSchemaOptional = imports.stream()
			.filter(importDeclaration -> importDeclaration.isAsterisk())
			.filter(importDeclaration -> {
				var optionalAnnotationSet = AnnotationsGlossary.get(importDeclaration.getName().toString());
				return optionalAnnotationSet.isPresent() && optionalAnnotationSet.get().contains(annotationName);			
			})
			.map(impd -> impd.getName().toString())
			.findFirst();

		if (wildCardSchemaOptional.isPresent()) {
			schema = wildCardSchemaOptional.get();
		} else if(javaLangPredefined.contains(annotation.getNameAsString()))
			schema = "java.lang";
		else //if not, the annotation was declared on the package being used
			schema = cu.getPackageDeclaration().get().getNameAsString();

		schemasMapper.put(annotationNameAndLine, schema);
		schemasMapper.put(annotationName, qualifier);
	}

	private String getQualifier(AnnotationExpr annotation) {

		Name qualifier = annotation.getName().getQualifier().get();

		while(Character.isUpperCase(qualifier.getId().charAt(0))){
			if(qualifier.getQualifier().isPresent())
				qualifier = qualifier.getQualifier().get();
			else
				return "";
		}
		return qualifier.asString();
	}

	private String getAnnotationContent(AnnotationExpr annotation)   {

		String annotationContent="";

		if(annotation.getClass().equals(SingleMemberAnnotationExpr.class)){
		      System.out.println("B!");
		    }else if (annotation.getClass().equals(MarkerAnnotationExpr.class)){

			
		      System.out.println("NOT B!");
		    }else if (annotation.getClass().equals(NormalAnnotationExpr.class)){
			           System.out.println("NOT B!");
		}

				 return annotationContent;

	}


	private void findImports(CompilationUnit cu) {
		for (ImportDeclaration import_ : cu.getImports()) {
			if(!import_.isStatic())
				imports.add(import_);
		}
	}
}