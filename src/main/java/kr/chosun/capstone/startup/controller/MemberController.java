package kr.chosun.capstone.startup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.chosun.capstone.startup.repository.dto.Member;
import kr.chosun.capstone.startup.repository.dto.Skill;
import kr.chosun.capstone.startup.repository.dto.University;
import kr.chosun.capstone.startup.response.DefaultResponse;
import kr.chosun.capstone.startup.response.ResponseMessage;
import kr.chosun.capstone.startup.response.StatusCode;
import kr.chosun.capstone.startup.service.MemberService;
import kr.chosun.capstone.startup.service.SkillService;
import kr.chosun.capstone.startup.service.UniversityService;
import kr.chosun.capstone.startup.utils.DateUtil;

@RestController
public class MemberController {
	@Autowired
	private UniversityService universityService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private SkillService skillService;
	
	//대학 목록 조회
	@GetMapping("/university")
	public List<University> getUniversitys() {
		return universityService.getUniversity();
	}
	
	//스킬 목록 조회
	@GetMapping("/skills")
	public List<Skill> getSkills(){
		return skillService.getSkills();
	}
	
	//특정 멤버 조회 (학생일 시 character, award, skill, pp 모두 가져온다.)
	@GetMapping("/member/{mem_seq}")
	public Member getMemberTest(@PathVariable(value="mem_seq")int memSeq) {
		return memberService.getMemberProfile(memSeq);
	}
	
	//회원가입(일반 / 학생)
	@PostMapping("/member")
	public ResponseEntity<DefaultResponse> register(@RequestBody Member member) {
		System.out.println(member);
		//스프링이 파싱하면서 자동으로 null을 넣어주고, DAO에서 모든 컬럼을 넣으니까 DB에 설정된 Default값이 안들어가고 null이 들어가므로 JAVA에서 넣어준다.
		String createDate = DateUtil.currentDateTime();
		member.setCreateDate(createDate);
		
		//아이디 중복 확인
		if(memberService.getMemIdCnt(member.getMemId()) > 0) {
			return new ResponseEntity(DefaultResponse.res(StatusCode.DB_ERROR, ResponseMessage.DUPLICATED_ID), HttpStatus.OK);
		}
		//이메일 중복 확인
		if(memberService.getMemEmailCnt(member.getMemEmail()) > 0) {
			return new ResponseEntity(DefaultResponse.res(StatusCode.DB_ERROR, ResponseMessage.DUPLICATED_EMAIL), HttpStatus.OK);	
		}
		//회원가입 진행
		Member registerMember = memberService.register(member);
		if(registerMember != null)
			return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.CREATED_USER, registerMember), HttpStatus.OK);
		else
			return new ResponseEntity(DefaultResponse.res(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR), HttpStatus.OK);
	}
}
