package com.joapps.astronautData.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.joapps.astronautData.model.AstonautModel;
import com.joapps.astronautData.service.AstronautService;

/*
 * 
 * RestController to Handle 
 * the endpoints
 */
@RestController
public class GetAstronautDataController {

	@Autowired
	private AstronautService astSService;
	
	public GetAstronautDataController(AstronautService astSService) {
		this.astSService = astSService;
	}
	
	 @RequestMapping("/astronauts") public List<Object> getAstronautsData() {
		  astSService.getAstronautData();
		  return Arrays.asList(astSService.getAstronautData());
	 }
	 
	 @RequestMapping("astronauts/{id}")
	 public @ResponseBody AstonautModel getAttr(@PathVariable(value="id") String id ) throws Exception {
		 AstonautModel astonautModel = astSService.getAstronaut(id);
										return astonautModel;
	 }
}
