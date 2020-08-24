package kr.or.cmcnu.ixonbatch.configuration;

import org.springframework.batch.item.ItemProcessor;

import kr.or.cmcnu.ixonbatch.model.*;

public class BillProcessor implements ItemProcessor<Usage, Bill> {

	  @Override
	  public Bill process(Usage usage) {
	     Double billAmount = usage.getDataUsage() * .001 + usage.getMinutes() * .01;
	     return new Bill(usage.getId(), usage.getFirstName(), usage.getLastName(),
	           usage.getDataUsage(), usage.getMinutes(), billAmount);
	  }
	}
