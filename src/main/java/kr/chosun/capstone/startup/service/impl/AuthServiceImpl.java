package kr.chosun.capstone.startup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.chosun.capstone.startup.repository.dao.MemberAuthEmailDAO;
import kr.chosun.capstone.startup.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private MemberAuthEmailDAO memberAuthEmailDao;
	
	@Override
	public int getAuthCodeCnt(String authCode) {
		return memberAuthEmailDao.selectAuthcodeCnt(authCode);
	}

}
