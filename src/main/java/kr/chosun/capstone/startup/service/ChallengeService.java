package kr.chosun.capstone.startup.service;

import java.util.List;

import kr.chosun.capstone.startup.repository.dto.Challenge;


public interface ChallengeService {
	public List<Challenge> getChallenges();
}
