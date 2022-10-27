package com.github.tagRetrieval.output.json.d3hierarchy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.tagRetrieval.model.ClassModel;
import com.github.tagRetrieval.model.CodeElementType;
import com.github.tagRetrieval.model.PackageModel;


public class FetchSystemViewIMP implements IFetchChildren {

	@Override
	public List<Children> fetchChildren(PackageModel package_) {
		List<Children> annotationSV = new ArrayList<Children>();
		
		Map<String, Integer> schemaMap = new HashMap<String, Integer>();
		
		for (ClassModel classReport : package_.getResults()) {
			if(classReport.getClassMetric("AC")==0)//Eliminate classes without annotation
				continue;
			
			classReport.getAnnotationSchemasMap().forEach((name,schema) -> {
				schemaMap.compute(schema, (k,v) -> (v == null ? 0 : v) + 1);
			});
		}
		schemaMap.forEach((k,v) -> {
			Children annotaSV = 
					new Children(k, CodeElementType.SCHEMA,v);
			annotationSV.add(annotaSV);
		});
		return annotationSV;
	}
	
	
}
