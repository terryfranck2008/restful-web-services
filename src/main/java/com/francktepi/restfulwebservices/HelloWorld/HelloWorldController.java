package com.francktepi.restfulwebservices.HelloWorld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	@Autowired
	private MessageSource messageSource;
	
	//@RequestMapping(path="/hello-world")
	@GetMapping(path="/hello-world")
	public String HelloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path="/hello-world-internationalized")
	public String HelloWorldInternazionalized(@RequestHeader(name="Accept-Language", required = false) Locale locale) {
		return messageSource.getMessage("good.morning.message", null, locale);
	}
	
	///hello-world/path-variable/franck
	@GetMapping(path="/hello-world/path-variable/{name}")
	public HelloWorldBean HelloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}
}
