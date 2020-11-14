package com.francktepi.restfulwebservices.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.francktepi.restfulwebservices.user.User;
import com.francktepi.restfulwebservices.user.UserRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperReportService {

	@Autowired
	private UserRepository userRepository;
	
	private final String path = "C:\\Users\\frltepi\\Desktop\\Report";
	
	public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
		
		List<User> users = userRepository.findAll();
		// load file and compile it
		File file = ResourceUtils.getFile("classpath:users.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);
		// adding some comment
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy", "Franck Tepi");
		
		//print file 
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);

		if (reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint,path + "\\users.html");
		}

		if (reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,path + "\\users.pdf");
		}
		
		return "report generated in path:" + path;
	}
}
