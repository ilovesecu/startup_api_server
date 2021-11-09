package kr.chosun.capstone.startup.service;

import java.util.List;

import kr.chosun.capstone.startup.repository.dto.Skill;

public interface SkillService {
	public List<Skill> getSkills(); //스킬목록 조회
}
