package kr.or.cmcnu.bucbatch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonDataset {

	private String dataset_name;
	private String row_id;
	private String column_id;
	private String column_value;
	private String fstrgstrid;
	private String lastupdtrid;

}
