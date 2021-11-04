package kr.chosun.capstone.startup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.chosun.capstone.startup.repository.dto.University;
import kr.chosun.capstone.startup.service.UniversityService;

@RestController
public class MemberRegisterController {
	@Autowired
	private UniversityService universityService;
	
	@GetMapping("/university")
	public List<University> getUniversitys() {
		return universityService.getUniversity();
	}
}
