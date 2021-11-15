package kr.chosun.capstone.startup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.chosun.capstone.startup.repository.dto.Challenge;
import kr.chosun.capstone.startup.service.ChallengeService;

@RestController
public class ChallengeController {
	@Autowired
	private ChallengeService challengeService;
	
	@GetMapping("/challenges")
	public List<Challenge> getChallenges(){
		return challengeService.getChallenges();
	}
}
