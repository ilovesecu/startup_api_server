package kr.chosun.capstone.startup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.chosun.capstone.startup.repository.dao.UniversityDAO;
import kr.chosun.capstone.startup.repository.dto.University;
import kr.chosun.capstone.startup.service.UniversityService;

@Service
public class UniversityServiceImpl implements UniversityService {
	
	@Autowired
	private UniversityDAO universityDao;
	
	@Override
	public List<University> getUniversity() {
		return universityDao.selectUniversitys();
	}

}
