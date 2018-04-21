package com.hotent.platform.service.ats;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hotent.platform.model.ats.AtsAttenceCalculate;
import com.hotent.platform.model.ats.AtsModel;
import com.hotent.platform.model.ats.AtsScheduleShift;

/**
 * 考勤计算
 * 
 * @author hugh zhuang
 * 
 */
public interface IAtsCalculate {

	/**
	 * 保存数据
	 * 
	 * @param atsModel
	 * @throws Exception
	 */
	void saveData(AtsModel atsModel) throws Exception;

	/**
	 * 返回计算的结果列表
	 * 
	 * @param atsModel
	 *            中的 useId和开始、结束时间必填
	 * @param scheduleShiftMap
	 * @param cardRecordList
	 * @return
	 * @throws Exception
	 */
	List<AtsAttenceCalculate> calculate(AtsModel atsModel, Map<String, AtsScheduleShift> scheduleShiftMap, Set<Date> cardRecordList) throws Exception;
	
}
