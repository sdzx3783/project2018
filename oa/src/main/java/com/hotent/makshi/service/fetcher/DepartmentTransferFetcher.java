/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.cache.ICache;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.makshi.model.hr.DepartmentTransfer;
import com.hotent.makshi.service.hr.DepartmentTransferService;
import com.hotent.platform.dao.system.UserUnderDao;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.service.bpm.impl.ScriptImpl;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserPositionService;

/**
 * @author cesc
 *
 */
@Component("departmentTransferFetcher")
public class DepartmentTransferFetcher implements IFetcher {
	@Resource
	private DepartmentTransferService departmentTransferService;
	@Resource
	private UserPositionService userPositionService;
	@Resource
	private ICache iCache;
	@Resource
	private UserUnderDao userUnderDao;
	@Resource
	private SysUserService sysUserService;
	
	
	
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		try {
			if(!StringUtil.isEmpty(businessKey)){
				DepartmentTransfer departmentTransfer = departmentTransferService.getById(Long.parseLong(businessKey));
				if(departmentTransfer!=null){
					Long userId = departmentTransfer.getUser_id();
					//组织
					//岗位
					Long orgId = Long.parseLong(departmentTransfer.getIn_departmentID());
					Long jobId = Long.parseLong(departmentTransfer.getIn_postID());
					UserPosition userPosition = userPositionService.getUserPosition(departmentTransfer.getUser_id(), Long.parseLong(departmentTransfer.getBeforePostID()));
					if(userPosition!=null){
						userPosition.setPosId(Long.parseLong(departmentTransfer.getIn_postID()));
						userPosition.setOrgId(orgId);
						userPosition.setJobId(jobId);
						userPositionService.update(userPosition);
						iCache.delByKey(ContextUtil.getOrgKey(userId));
						iCache.delByKey(ContextUtil.getPositionKey(userId));
					}
				}
			}
					//上级
			/*		ApplicationContext context = AppUtil.getContext();
					ScriptImpl script =(ScriptImpl) context.getBean(com.hotent.platform.service.bpm.impl.ScriptImpl.class);
					//组织负责人
					List<TaskExecutor> executor = script.getExecutor(orgId);
					//获取个人信息
					List<SysUser> list = sysUserService.getByUserIdAndUplow(userId);
					if(list.size()>0){
						SysUser sysUser = list.get(0);
						Long[] superiorIds = new Long[3];
						Set<ISysUser> set;
						Iterator iter = set.iterator();
						for(int i = 0 ;i<executor.size() ; i++){
							set = executor.get(i).getSysUser();
							 iter.next();
							//superiorIds[i] = executor.get(i).getSysUser().
						}
						sysUser.setSuperiorIds(superiorIds);
					}
					//executor.get(0).getSysUser()
					//List<UserUnder> userList  = userUnderDao.getMyLeader(userId);
					for(TaskExecutor taskExecutor:executor){
						userUnder.setLeaderName(departmentTransfer.getLeader());
						userUnder.setUserid(Long.parseLong(departmentTransfer.getLeaderID()));
						userUnderDao.update(userUnder);
					}*/
			
		} catch (Exception e) {
			String  message = "信息同步失败";
			throw new RuntimeException(message,e.getCause());
		}
	}
}
