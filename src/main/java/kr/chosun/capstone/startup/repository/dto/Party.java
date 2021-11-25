package kr.chosun.capstone.startup.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Party {
	private Integer cpt_seq;
	private Integer leader;
	private String cpt_title;
	private String description;
	private int headcount;
	private Integer mem_seq; //null 일 경우가 있으면 references type으로
}
