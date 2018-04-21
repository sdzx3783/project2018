package com.hotent.makshi.service.hr;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.hr.InfochangeDao;
import com.hotent.makshi.model.hr.Infochange;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;


@Service
public class InfochangeService extends BaseService<Infochange>
{
	@Resource
	private InfochangeDao dao;
	@Resource
	private SysUserDao sysUserdao;
	
	
	public InfochangeService()
	{
	}
	
	@Override
	protected IEntityDao<Infochange,Long> getEntityDao() 
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
			Infochange infochange=getInfochange(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				infochange.setId(genId);
				this.add(infochange);
			}else{
				infochange.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(infochange);
			}
			cmd.setBusinessKey(infochange.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取Infochange对象
	 * @param json
	 * @return
	 */
	public Infochange getInfochange(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Infochange infochange = (Infochange)JSONObject.toBean(obj, Infochange.class);
		return infochange;
	}
	
	
	public List<Infochange> getInfochangeByAccount(HttpServletRequest request,Long userId){
		QueryFilter filter = new QueryFilter(request);
		SysUser sysUser = sysUserdao.getById(userId);
		filter.addFilter("account", sysUser.getAccount());
		return dao.getAll(filter);
	}
	/**
	 * 保存 信息变更 信息
	 * @param infochange
	 */

	public void save(Infochange infochange) throws Exception{
		Long id=infochange.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			infochange.setId(id);
		    this.add(infochange);
		}
		else{
		    this.update(infochange);
		}
	}
}
