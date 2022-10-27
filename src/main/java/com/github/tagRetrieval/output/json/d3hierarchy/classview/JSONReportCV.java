package com.github.tagRetrieval.output.json.d3hierarchy.classview;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.tagRetrieval.model.AMReport;
import com.github.tagRetrieval.output.IReport;
import com.github.tagRetrieval.output.json.d3hierarchy.FetchClassViewIMP;
import com.github.tagRetrieval.output.json.d3hierarchy.ProjectReport;
import com.github.tagRetrieval.utils.ReportTypeUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONReportCV implements IReport {
	
	private ProjectReport projectReport;

	@Override
	public void generateReport(AMReport report, String path) {
		
		projectReport = prepareJson(report);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		Path jsonFilePath = Paths.get(path + File.separator + report.getProjectName() + "-CV.json").normalize();
		
		String json = gson.toJson(projectReport);
		
		try {
			FileWriter writer = new FileWriter(jsonFilePath.toString());
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ProjectReport prepareJson(AMReport report) {
		
		ProjectReport projectReport = new ProjectReport(report.getProjectName());
		
		projectReport.addPackages(ReportTypeUtils.
				fetchPackages(report.getPackages(), new FetchClassViewIMP()));
		
		return projectReport;
	}

}