package com.github.tagRetrieval.output.json.d3hierarchy;

import java.util.List;

import com.github.tagRetrieval.model.PackageModel;

public interface IFetchChildren {
	
	public List<Children> fetchChildren(PackageModel package_);

}
