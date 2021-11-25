package kr.chosun.capstone.startup.service;

import java.util.List;
import java.util.Map;

import kr.chosun.capstone.startup.repository.dto.Party;

public interface PartyService {
	public List<Party> getParties(); //파티 조회
	public int makeParty(Party party); //파티 생성
}
