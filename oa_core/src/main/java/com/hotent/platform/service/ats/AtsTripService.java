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
import com.hotent.platform.model.ats.AtsTrip;
import com.hotent.platform.dao.ats.AtsTripDao;
import com.hotent.core.service.BaseService;

/**
 *<pre>
 * 对象功能:考勤出差单 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zxh
 * 创建时间:2015-07-15 14:59:30
 *</pre>
 */
@Service
public class AtsTripService extends  BaseService<AtsTrip>
{
	@Resource
	private AtsTripDao dao;
	
	
	
	public AtsTripService()
	{
	}
	
	@Override
	protected IEntityDao<AtsTrip, Long> getEntityDao() 
	{
		return dao;
	}
	
	
	/**
	 * 保存 考勤出差单 信息
	 * @param atsTrip
	 */
	public void save(AtsTrip atsTrip){
		Long id=atsTrip.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			atsTrip.setId(id);
			this.add(atsTrip);
		}
		else{
			this.update(atsTrip);
		}
	}
	
}
