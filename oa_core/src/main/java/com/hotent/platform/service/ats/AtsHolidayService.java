package com.hotent.platform.service.ats;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.model.bpm.ProcessRun;
import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.ats.AtsHoliday;
import com.hotent.platform.dao.ats.AtsHolidayDao;
import com.hotent.core.service.BaseService;

/**
 *<pre>
 * 对象功能:考勤请假单 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zxh
 * 创建时间:2015-07-15 14:59:52
 *</pre>
 */
@Service
public class AtsHolidayService extends  BaseService<AtsHoliday>
{
	@Resource
	private AtsHolidayDao dao;
	
	
	
	public AtsHolidayService()
	{
	}
	
	@Override
	protected IEntityDao<AtsHoliday, Long> getEntityDao() 
	{
		return dao;
	}
	
	
	/**
	 * 保存 考勤请假单 信息
	 * @param atsHoliday
	 */
	public void save(AtsHoliday atsHoliday){
		Long id=atsHoliday.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			atsHoliday.setId(id);
			this.add(atsHoliday);
		}
		else{
			this.update(atsHoliday);
		}
	}
	
}
