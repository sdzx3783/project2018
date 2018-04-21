/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;

@Component("drawMoneyBillFetcher")
public class DrawMoneyBillFetcher implements IFetcher {
	@Resource
	private SysUserService userService;
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	private static Logger logger=Logger.getLogger(DrawMoneyBillFetcher.class);
	
	@Override
	public void fetcheData(String tableName, String businessKey) throws Exception {
		String message ="流程信息同步失败";
		try {
			if(!StringUtil.isEmpty(businessKey)){
				String drawsql = "select F_drawtype from w_draw_money_bill where ID ="+businessKey; 
				String drawType = jdbcTemplate.queryForObject(drawsql, String.class);
				if(StringUtil.isNotEmpty(drawType) && ("1").equals(drawType)){
					return;
				}
				String usersql = "select F_nameID from w_draw_money_bill where ID ="+businessKey; 
				String userId = jdbcTemplate.queryForObject(usersql, String.class);
				SysUser user = userService.getById(Long.parseLong(userId));
				if(user!=null){
					//user.setUserStatus("离职");
					user.setIsLock((short) 1);
					userService.update(user);
				}else{
					message = "用户不存在";
				}
			}else{
				message = "未找到流程数据";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw  new Exception(message);
		}
		
	}

}
