package kr.chosun.capstone.startup.scheduled;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.chosun.capstone.startup.repository.dao.ChallengeDAO;
import kr.chosun.capstone.startup.repository.dto.Challenge;
import kr.chosun.capstone.startup.utils.DateUtil;

@EnableScheduling
//@Configuration //일단은 스케쥴링 막아놓음
public class ChallengeScheduler {
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ChallengeDAO dao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, SQLException.class}, readOnly = false)
	@Scheduled(fixedRate = 86400) //24시간 이후
	public void autoDelete() {
		List<Integer> cptList = new ArrayList<Integer>();
		int cpt_seq;
		String period;
		String today = DateUtil.getYYYYMMddStr().replace(File.separator, "-");
		List<Challenge> chall = dao.selectChallenge(0);
		Iterator<Challenge> iter = chall.iterator();
		while(iter.hasNext()) {
			Challenge item = iter.next();
			cpt_seq = item.getCpt_seq();
			period = item.getPeriod();
			period = period.substring(13); // '~' 이후 날짜만
			if(period.compareTo(today)<0)
				cptList.add(cpt_seq);
		}
		Iterator<Integer> cptIter = cptList.iterator();
		while(cptIter.hasNext()) {
			dao.deleteChallenge(cptIter.next());
		}
		System.out.println("공모전 갱신");
	}
}
