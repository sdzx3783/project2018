package com.hotent.makshi.service.vacation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.vacation.CompanyYearVaction;
import com.hotent.platform.dao.worktime.VacationDao;
import com.hotent.platform.model.worktime.Vacation;
import com.hotent.makshi.dao.vacation.CompanyYearVactionDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class CompanyYearVactionService extends BaseService<CompanyYearVaction>
{
	@Resource
	private CompanyYearVactionDao dao;
	@Resource 
	private VacationDao vacationDao;
	
	public CompanyYearVactionService()
	{
	}
	
	@Override
	protected IEntityDao<CompanyYearVaction,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 流程处理器方法 用于处理业务数据
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd)throws Exception{
		Map data=cmd.getFormDataMap();
		if(BeanUtils.isNotEmpty(data)){
			String json=data.get("json").toString();
			CompanyYearVaction CompanyYearVaction=getCompanyYearVaction(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				CompanyYearVaction.setId(genId);
				this.add(CompanyYearVaction);
			}else{
				CompanyYearVaction.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(CompanyYearVaction);
			}
			cmd.setBusinessKey(CompanyYearVaction.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取CompanyYearVaction对象
	 * @param json
	 * @return
	 */
	public CompanyYearVaction getCompanyYearVaction(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		CompanyYearVaction CompanyYearVaction = (CompanyYearVaction)JSONObject.toBean(obj, CompanyYearVaction.class);
		return CompanyYearVaction;
	}
	/**
	 * 保存 公司年假福利 信息
	 * @param CompanyYearVaction
	 */

	public void save(CompanyYearVaction CompanyYearVaction) throws Exception{
		Long id=CompanyYearVaction.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			CompanyYearVaction.setId(id);
		    this.add(CompanyYearVaction);
		}
		else{
		    this.update(CompanyYearVaction);
		}
	}

	public CompanyYearVaction getByYear(String year) {
		Map queryFilter=new HashMap<>();
		queryFilter.put("year", year);
		List<CompanyYearVaction> bySqlKey = dao.getBySqlKey("getByYear", queryFilter);
		if(bySqlKey!=null && bySqlKey.size()>0){
			return bySqlKey.get(0);
		}else{
			return null;
		}
	}
	
	public List<Vacation> getVactionByYearAndName(String year,String name){
		Map<String,String> queryFilter=new HashMap<>();
		queryFilter.put("year", year);
		queryFilter.put("name", name);
		List<Vacation> bySqlKey = vacationDao.getBySqlKey("getVactionByYearAndName", queryFilter);
		if(bySqlKey!=null){
			return bySqlKey;
		}else{
			return null;
		}
	}
}
