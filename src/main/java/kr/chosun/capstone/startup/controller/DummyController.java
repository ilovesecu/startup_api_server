package kr.chosun.capstone.startup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.chosun.capstone.startup.repository.dto.Member;
import kr.chosun.capstone.startup.repository.dto.University;
import kr.chosun.capstone.startup.service.MemberService;
import kr.chosun.capstone.startup.service.UniversityService;

@RestController
public class DummyController {
	
	@Autowired
	private UniversityService universityService;
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/test")
	public List<University> test() {
		System.out.println(System.currentTimeMillis());
		return universityService.getUniversity();
	}
	
	@GetMapping("/user/test")
	public List<University> authenticationTest() {
		System.out.println(System.currentTimeMillis());
		return universityService.getUniversity();
	}
	
	@GetMapping("/api/v1/user")
	public String authenticationTest2() {
		System.out.println(System.currentTimeMillis());
		return "Test";
	}
	
	/*
	@GetMapping("/member/{mem_seq}")
	public Member getMemberTest(@PathVariable(value="mem_seq")int memSeq) {
		return memberService.getMemberProfile(memSeq);
	}
	*/
}
