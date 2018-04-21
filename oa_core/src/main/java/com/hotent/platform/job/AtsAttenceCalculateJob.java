package com.hotent.platform.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;

import com.hotent.core.scheduler.BaseJob;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.DateFormatUtil;
import com.hotent.platform.dao.ats.AtsAttenceCycleDetailDao;
import com.hotent.platform.dao.ats.AtsAttencePolicyDao;
import com.hotent.platform.model.ats.AtsAttenceCycleDetail;
import com.hotent.platform.model.ats.AtsAttencePolicy;
import com.hotent.platform.service.ats.AtsAttenceCalculateService;

/**
 * 
 * 考勤自动计算
 * 
 * <pre>
 * 开发公司:广州宏天软件有限公司 
 * 开发人员:zxh 
 * 创建时间:2015-07-06 13:51:08
 * </pre>
 * 
 */
public class AtsAttenceCalculateJob extends BaseJob {
	private Log logger = LogFactory.getLog(AtsAttenceCalculateJob.class);

	/**
	 * 1、循环所有的考勤制度
	 * 2、当前年月在这个制度下的考勤周期
	 * 3、执行这个考勤制度下人员
	 */
	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		AtsAttencePolicyDao atsAttencePolicyDao = (AtsAttencePolicyDao) AppUtil
				.getBean(AtsAttencePolicyDao.class);
		AtsAttenceCycleDetailDao atsAttenceCycleDetailDao = (AtsAttenceCycleDetailDao) AppUtil
				.getBean(AtsAttenceCycleDetailDao.class);
		AtsAttenceCalculateService atsAttenceCalculateService = (AtsAttenceCalculateService) AppUtil
				.getBean(AtsAttenceCalculateService.class);
		List<AtsAttencePolicy> list = atsAttencePolicyDao.getAll();
		String date = DateFormatUtil.format(new Date(), "yyyy-MM");
		// 1、循环所有的考勤制度
		for (AtsAttencePolicy atsAttencePolicy : list) {
			// 2、当前年月在这个制度下的考勤周期
			AtsAttenceCycleDetail atsAttenceCycleDetail = atsAttenceCycleDetailDao
					.getByCycleIdName(atsAttencePolicy.getAttenceCycle(), date);
			if (BeanUtils.isEmpty(atsAttenceCycleDetail))
				continue;
			//3、执行这个考勤制度下人员
			atsAttenceCalculateService.allCalculate(
					atsAttenceCycleDetail.getStartTime(),
					atsAttenceCycleDetail.getEndTime(),
					atsAttencePolicy.getId());
		}

	}
}
