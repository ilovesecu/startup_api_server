package kr.chosun.capstone.startup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.chosun.capstone.startup.repository.dto.Member;
import kr.chosun.capstone.startup.repository.dto.University;
import kr.chosun.capstone.startup.service.MemberService;
import kr.chosun.capstone.startup.service.UniversityService;
import kr.chosun.capstone.startup.utils.DateUtil;

@RestController
public class MemberRegisterController {
	@Autowired
	private UniversityService universityService;
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/university")
	public List<University> getUniversitys() {
		return universityService.getUniversity();
	}
	
	@PostMapping("/member")
	public void register(@RequestBody Member member) {
		System.out.println(member);
		//스프링이 파싱하면서 자동으로 null을 넣어주고, DAO에서 모든 컬럼을 넣으니까 DB에 설정된 Default값이 안들어가고 null이 들어가므로 JAVA에서 넣어준다.
		String createDate = DateUtil.currentDateTime();
		member.setCreateDate(createDate);
		memberService.register(member);
	}
}
