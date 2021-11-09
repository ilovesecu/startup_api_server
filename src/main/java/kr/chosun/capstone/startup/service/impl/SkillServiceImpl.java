package kr.chosun.capstone.startup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.chosun.capstone.startup.repository.dao.SkillDAO;
import kr.chosun.capstone.startup.repository.dto.Skill;
import kr.chosun.capstone.startup.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService {
	@Autowired
	private SkillDAO skillDao;
	
	@Override
	public List<Skill> getSkills() {
		return skillDao.selectSkills();
	}

}
