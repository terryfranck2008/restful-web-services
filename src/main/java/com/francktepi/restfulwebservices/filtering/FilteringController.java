package com.francktepi.restfulwebservices.filtering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {
	
	@GetMapping(path="/filtering")
	public SomeBean retrieveSomeBean() {
		return new SomeBean("value1","value2","value3");
	}
}
