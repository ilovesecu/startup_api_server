package kr.chosun.capstone.startup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.chosun.capstone.startup.repository.dao.ChallengeDAO;
import kr.chosun.capstone.startup.repository.dto.Challenge;
import kr.chosun.capstone.startup.service.ChallengeService;

@Service
public class ChallengeServiceImpl implements ChallengeService{
	@Autowired
	private ChallengeDAO dao;
	
	@Override
	public List<Challenge> getChallenges(int id) {
		return dao.selectChallenge(id);
	}
	
}
