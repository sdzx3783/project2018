
package com.hotent.makshi.dao.msgscheduler;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.hr.EntryJtcy;
import com.hotent.makshi.model.msgscheduler.SendMsgUser;

@Repository
public class SendMsgUserDao extends WfBaseDao<SendMsgUser>
{
	@Override
	public Class<?> getEntityClass()
	{
		return SendMsgUser.class;
	}

	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
	
	public List<SendMsgUser> getByMainId(Long refId) {
		return this.getBySqlKey("getSendMsgUserList", refId);
	}
	
}