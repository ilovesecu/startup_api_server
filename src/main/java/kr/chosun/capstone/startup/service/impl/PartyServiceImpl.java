package kr.chosun.capstone.startup.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.chosun.capstone.startup.repository.dao.PartyDAO;
import kr.chosun.capstone.startup.repository.dto.Party;
import kr.chosun.capstone.startup.service.PartyService;

@Service
public class PartyServiceImpl implements PartyService{
	@Autowired
	PartyDAO dao;
	
	@Override
	public List<Party> getParties() {
		return dao.selectParty();
	}

	@Override
	public int makeParty(Party party) {
		int key = dao.insertParty(party);
		return key;
	}

}
