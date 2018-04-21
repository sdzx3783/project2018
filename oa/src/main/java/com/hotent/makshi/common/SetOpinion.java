package com.hotent.makshi.common;

import com.hotent.makshi.model.bidding.BiddingOption;
import com.hotent.platform.model.bpm.TaskOpinion;

public class SetOpinion {
	public static  void setBiddingOpinion(BiddingOption biddingOption,TaskOpinion taskOpinion) {
		biddingOption.setName(taskOpinion.getExeFullname());
		biddingOption.setTime(taskOpinion.getEndTime());
		biddingOption.setOption(taskOpinion.getOpinion());
		biddingOption.setTaskName(taskOpinion.getTaskName());
	}
}
