package com.hotent.makshi.service.party;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.party.PartyFiles;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.dao.party.PartyFilesDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class PartyFilesService extends BaseService<PartyFiles>
{
	@Resource
	private PartyFilesDao dao;
	
	public PartyFilesService()
	{
	}
	
	@Override
	protected IEntityDao<PartyFiles,Long> getEntityDao() 
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
			PartyFiles partyFiles=getPartyFiles(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				partyFiles.setId(genId);
				this.add(partyFiles);
			}else{
				partyFiles.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(partyFiles);
			}
			cmd.setBusinessKey(partyFiles.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取PartyFiles对象
	 * @param json
	 * @return
	 */
	public PartyFiles getPartyFiles(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		PartyFiles partyFiles = (PartyFiles)JSONObject.toBean(obj, PartyFiles.class);
		return partyFiles;
	}
	/**
	 * 保存 党员档案 信息
	 * @param partyFiles
	 */

	public void save(PartyFiles partyFiles) throws Exception{
		Long id=partyFiles.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			partyFiles.setId(id);
		    this.add(partyFiles);
		}
		else{
		    this.update(partyFiles);
		}
	}
	
	
	/**
	 * 根据党员编号获取党员信息
	 * @param partyNum
	 * @return
	 */
	public PartyFiles getByPartyNum(String partyNum){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("partyNum", partyNum);
		List<PartyFiles> partyFilesList = dao.getBySqlKey("getByPartyNum", params);
		if(partyFilesList!=null && partyFilesList.size()>0){
			PartyFiles file = partyFilesList.get(0);
			return file;
		}
		
		return null;
	}
}
