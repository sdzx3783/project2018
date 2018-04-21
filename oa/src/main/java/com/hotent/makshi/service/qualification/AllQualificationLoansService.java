package com.hotent.makshi.service.qualification;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.qualification.AllQualificationLoans;
import com.hotent.makshi.dao.qualification.AllQualificationLoansDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;

import com.hotent.makshi.model.qualification.Jyzz;
import com.hotent.makshi.dao.qualification.JyzzDao;

import com.hotent.core.service.BaseService;


@Service
public class AllQualificationLoansService extends BaseService<AllQualificationLoans>
{
	@Resource
	private AllQualificationLoansDao dao;
	
	@Resource
	private JyzzDao jyzzDao;
	public AllQualificationLoansService()
	{
	}
	
	@Override
	protected IEntityDao<AllQualificationLoans,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param id
	 */
	private void delByPk(Long id){
		jyzzDao.delByMainId(id);
	}
	
	/**
	 * 删除数据 包含相应子表记录
	 * @param lAryId
	 */
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	/**
	 * 添加数据 
	 * @param allQualificationLoans
	 * @throws Exception
	 */
	public void addAll(AllQualificationLoans allQualificationLoans) throws Exception{
		super.add(allQualificationLoans);
		addSubList(allQualificationLoans);
	}
	
	/**
	 * 更新数据
	 * @param allQualificationLoans
	 * @throws Exception
	 */
	public void updateAll(AllQualificationLoans allQualificationLoans) throws Exception{
		super.update(allQualificationLoans);
		delByPk(allQualificationLoans.getId());
		addSubList(allQualificationLoans);
	}
	
	/**
	 * 添加子表记录
	 * @param allQualificationLoans
	 * @throws Exception
	 */
	public void addSubList(AllQualificationLoans allQualificationLoans) throws Exception{
		
		List<Jyzz> jyzzList=allQualificationLoans.getJyzzList();
		if(BeanUtils.isNotEmpty(jyzzList)){
			
			for(Jyzz jyzz:jyzzList){
				jyzz.setRefId(allQualificationLoans.getId());
				Long id=UniqueIdUtil.genId();
				jyzz.setId(id);				
				jyzzDao.add(jyzz);
			}
		}
	}
	
	/**
	 * 根据外键获得借阅资质列表
	 * @param id
	 * @return
	 */
	public List<Jyzz> getJyzzList(Long id) {
		return jyzzDao.getByMainId(id);
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
			AllQualificationLoans allQualificationLoans=getAllQualificationLoans(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				allQualificationLoans.setId(genId);
				this.addAll(allQualificationLoans);
			}else{
				allQualificationLoans.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(allQualificationLoans);
			}
			cmd.setBusinessKey(allQualificationLoans.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取AllQualificationLoans对象
	 * @param json
	 * @return
	 */
	public AllQualificationLoans getAllQualificationLoans(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("jyzzList", Jyzz.class);
		AllQualificationLoans allQualificationLoans = (AllQualificationLoans)JSONObject.toBean(obj, AllQualificationLoans.class,map);
		return allQualificationLoans;
	}
	/**
	 * 保存 公司各类资质原件借用流程 信息
	 * @param allQualificationLoans
	 */

	public void save(AllQualificationLoans allQualificationLoans) throws Exception{
		Long id=allQualificationLoans.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			allQualificationLoans.setId(id);
			this.addAll(allQualificationLoans);
		}
		else{
		    this.updateAll(allQualificationLoans);
		}
	}
}
