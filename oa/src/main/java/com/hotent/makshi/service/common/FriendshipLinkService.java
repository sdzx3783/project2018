package com.hotent.makshi.service.common;
import java.util.Map;

import javax.annotation.Resource;

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
import com.hotent.makshi.dao.common.FriendshipLinkDao;
import com.hotent.makshi.model.common.FriendshipLink;


@Service
public class FriendshipLinkService extends BaseService<FriendshipLink>
{
	@Resource
	private FriendshipLinkDao dao;
	
	public FriendshipLinkService()
	{
	}
	
	@Override
	protected IEntityDao<FriendshipLink,Long> getEntityDao() 
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
			FriendshipLink friendshipLink=getFriendshipLink(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				friendshipLink.setId(genId);
				this.add(friendshipLink);
			}else{
				friendshipLink.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(friendshipLink);
			}
			cmd.setBusinessKey(friendshipLink.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取FriendshipLink对象
	 * @param json
	 * @return
	 */
	public FriendshipLink getFriendshipLink(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		FriendshipLink friendshipLink = (FriendshipLink)JSONObject.toBean(obj, FriendshipLink.class);
		return friendshipLink;
	}
	/**
	 * 保存 友情链接 信息
	 * @param friendshipLink
	 */

	public void save(FriendshipLink friendshipLink) throws Exception{
		Long id=friendshipLink.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			friendshipLink.setId(id);
		    this.add(friendshipLink);
		}
		else{
		    this.update(friendshipLink);
		}
	}
}
