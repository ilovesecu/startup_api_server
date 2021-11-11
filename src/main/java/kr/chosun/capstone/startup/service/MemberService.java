package kr.chosun.capstone.startup.service;

import java.util.List;

import kr.chosun.capstone.startup.repository.dto.Member;

public interface MemberService {
	public List<Member> getMembers(); //Character까지 맵핑필요. Member리스트 조회
	public Member register(Member member); //멤버 등록 (여러 개의 작업을 하나의 트랜잭션으로 구성)
	public Member getMember(int memSeq); //특정 멤버 정보조회
}
