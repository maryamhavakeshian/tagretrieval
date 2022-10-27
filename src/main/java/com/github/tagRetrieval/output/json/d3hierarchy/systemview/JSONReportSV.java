package com.github.tagRetrieval.output.json.d3hierarchy.systemview;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.tagRetrieval.output.json.d3hierarchy.ProjectReport;
import com.github.tagRetrieval.output.IReport;
import com.github.tagRetrieval.output.json.d3hierarchy.FetchSystemViewIMP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.github.tagRetrieval.model.AMReport;
import com.github.tagRetrieval.utils.ReportTypeUtils;

public class JSONReportSV implements IReport {

	private ProjectReport projectReportJson;
	
	@Override
	public void generateReport(AMReport report, String path) {
		
		projectReportJson = prepareJson(report);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		Path jsonFilePath = Paths.get(path + File.separator + report.getProjectName() + "-SV.json").normalize();
		
		String json = gson.toJson(projectReportJson);
		
		try {
			FileWriter writer = new FileWriter(jsonFilePath.toString());
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public ProjectReport prepareJson(AMReport report) {

		ProjectReport projectReportJson =
					new ProjectReport(report.getProjectName());
		
		projectReportJson.addPackages(ReportTypeUtils.fetchPackages(report.getPackages(), new FetchSystemViewIMP()));
		
		return projectReportJson;
		
	}

}