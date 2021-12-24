package kr.chosun.capstone.startup.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.chosun.capstone.startup.repository.dto.Party;
import kr.chosun.capstone.startup.service.PartyService;

@RestController
public class PartyController {
	@Autowired(required=true)
	PartyService partyService;
	
	@GetMapping("/parties")
	public List<Party> getParties(){

		return partyService.getParties();
	}
	
	@PostMapping("/party")//json으로 party테이블 형식에 맞게 요청하면 insert
	public int makeParty(@RequestBody Party party) {
		int key = partyService.makeParty(party);
		return key;
	}
}
