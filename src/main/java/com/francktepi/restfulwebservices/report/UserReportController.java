package com.francktepi.restfulwebservices.report;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JRException;

@RestController
public class UserReportController {
	
	@Autowired
	private JasperReportService jasperReportService;
	
	@GetMapping(path="/jpa/report/{format}")
	public String generatedReport(@PathVariable String format) throws FileNotFoundException, JRException {
		if(format == null || format.trim().isEmpty()){
			format ="pdf";
		}
		return jasperReportService.exportReport(format);
	}
}
