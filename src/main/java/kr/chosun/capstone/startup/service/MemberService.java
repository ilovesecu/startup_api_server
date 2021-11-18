package kr.chosun.capstone.startup.service;

import java.util.List;

import kr.chosun.capstone.startup.repository.dto.Member;

public interface MemberService {
	public List<Member> getMembers(); //Character까지 맵핑필요. Member리스트 조회
	public Member register(Member member); //멤버 등록 (여러 개의 작업을 하나의 트랜잭션으로 구성)
	public Member getMember(int memSeq); //특정 멤버 정보조회(조인없는 딱 멤버만 조회)
	public Member getMemberProfile(int memSeq); //특정 멤버에 대한 (수상내역, 스킬, 포트폴리오 조회)
	public int updateMemStat(int memSeq, String memStat); //memStat 업데이트
	public int getMemIdCnt(String memId); //멤버 아이디 갯수 확인 (중복확인에 활용)
	public int getMemEmailCnt(String memEmail); //멤버 이메일 갯수 확인(중복확인에 활용)
}
