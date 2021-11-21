package kr.chosun.capstone.startup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.chosun.capstone.startup.repository.dto.Challenge;
import kr.chosun.capstone.startup.service.ChallengeService;

@RestController
public class ChallengeController {
	@Autowired
	private ChallengeService challengeService;
	//공모전 분야별로 나눠서 볼 수 있도록 하려면 파라미터(field)있는 메소드 추가하기
	@GetMapping("/challenges")
	//id 값은 [0,2,4,5,12,15]이 있다.
	//0은 공모전 전체, 2:기획, 4:디자인, 5:광고마케팅, 12:게임소프트웨어, 15:취업창업
	//http://localhost:8080/startup/challenges?id=0
	public List<Challenge> getChallenges(@RequestParam(value="id", required=false, defaultValue="0") int id){
		return challengeService.getChallenges(id);
	}
}
