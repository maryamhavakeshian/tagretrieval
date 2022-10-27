package com.github.tagRetrieval;

import com.github.javaparser.*;
import com.github.tagRetrieval.model.AMReport;
import com.github.tagRetrieval.output.IReport;
import com.github.tagRetrieval.parameters.Parameters;
import com.github.tagRetrieval.utils.FileUtils;
import com.github.tagRetrieval.utils.ReportTypeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ASniffer {

	private String projectsPath = "";
	private String reportPath = "";
	private IReport reportType;

	private static final Logger logger =
			LogManager.getLogger(ASniffer.class);

	public ASniffer(String projectPath, String reportPath, IReport reportType) {
		this.projectsPath = projectPath;
		this.reportPath = reportPath;
		this.reportType = reportType;
	}

	public ASniffer(String projectPath, String reportPath) {
		this.projectsPath = projectPath;
		this.reportPath = reportPath;
		this.reportType = ReportTypeUtils.getReportInstance(Parameters.DEFAULT_PROJECT_REPORT);
	}

	//project path is a root directory to multiple project directories
	public List<AMReport> collectMultiple() {
		List<AMReport> reports = new ArrayList<AMReport>();
		for (Path projectPath : FileUtils.getProjectsPath(projectsPath))
			reports.add(collect(projectPath));
		return reports;
	}

	//project path is a directory to a single
	public AMReport collectSingle() {
		return collect(Paths.get(projectsPath));
	}

	private AMReport collect(Path projectPath) {

		StaticJavaParser.setConfiguration(
				StaticJavaParser.getConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17_PREVIEW)
		);

		String projectName = FileUtils.getProjectName(projectPath);
		logger.info("Initializing extraction for project " + projectName);
		AMReport report = AmFactory.createAm(projectPath.toString(), projectName).calculate();
		logger.info("Extraction concluded for project " + projectName);

		generateOutput(report);
		return report;
	}

	private void generateOutput(AMReport report) {
		reportType.generateReport(report, reportPath);
	}
}