package com.github.tagRetrieval.output;

import com.github.tagRetrieval.model.AMReport;

public interface IReport {

	public void generateReport(AMReport report, String path);

}