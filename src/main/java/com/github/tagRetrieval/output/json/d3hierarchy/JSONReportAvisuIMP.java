package com.github.tagRetrieval.output.json.d3hierarchy;

import java.io.File;

import com.github.tagRetrieval.model.AMReport;
import com.github.tagRetrieval.output.IReport;
import com.github.tagRetrieval.output.json.d3hierarchy.classview.JSONReportCV;
import com.github.tagRetrieval.output.json.d3hierarchy.packageview.JSONReportPV;
import com.github.tagRetrieval.output.json.d3hierarchy.systemview.JSONReportSV;

public class JSONReportAvisuIMP implements IReport {

	@Override
	public void generateReport(AMReport report, String path) {
		
		String dirPathResults = path + File.separator + "asniffer_results";
		
		new File(dirPathResults).mkdir();
		
		new JSONReportCV().generateReport(report, dirPathResults);
		new JSONReportSV().generateReport(report, dirPathResults);
		new JSONReportPV().generateReport(report, dirPathResults);
		
	}

}