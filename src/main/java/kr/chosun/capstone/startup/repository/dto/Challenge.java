package kr.chosun.capstone.startup.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Challenge {
	private int cpt_seq;
	private String host;
	private String apply_way;
	private String qualifi;
	private String award_type;
	private String period;
	private String homepage;
	private int file_seq;
	private int field;
	private String title;
}
