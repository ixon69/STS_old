package kr.or.cmcnu.bucbatch.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.batch.item.ItemProcessor;

import kr.or.cmcnu.bucbatch.model.CommonDataset;
import kr.or.cmcnu.bucbatch.model.DataSet;

public class ConvertDatasetProcessor implements ItemProcessor<HashMap, List<CommonDataset>> {

	private String dataset_name = DataSet.name;
	private String fstrgstrid = DataSet.worker;
	private String lastupdtrid = DataSet.worker;
	private String[] keys = DataSet.keys.split(",");
	
	@Override
	public List<CommonDataset> process(HashMap hash) throws Exception {
		List<CommonDataset> list = new ArrayList<>();
		String uniqueKey = dataset_name;
		for (int i = 0; i < keys.length; i++) {
			uniqueKey = uniqueKey + " " + hash.get(keys[i]).toString();
		}
		String row_id = DigestUtils.sha256Hex(uniqueKey);
		hash.forEach((key, value) -> {
			String column_id = key.toString();
			String column_value = value.toString();
			list.add(new CommonDataset(dataset_name, row_id, column_id, column_value, fstrgstrid, lastupdtrid));
		});
		return list;
	}

}
