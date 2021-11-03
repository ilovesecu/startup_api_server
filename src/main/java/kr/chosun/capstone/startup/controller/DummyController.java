package kr.chosun.capstone.startup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.chosun.capstone.startup.repository.dto.University;
import kr.chosun.capstone.startup.service.UniversityService;

@RestController
public class DummyController {
	
	@Autowired
	private UniversityService universityService;
	
	@GetMapping("/test")
	public List<University> test() {
		System.out.println(System.currentTimeMillis());
		return universityService.getUniversity();
	}
}
