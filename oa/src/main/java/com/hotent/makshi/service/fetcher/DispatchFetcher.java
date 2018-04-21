package com.hotent.makshi.service.fetcher;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.model.dispatch.Dic;
import com.hotent.makshi.model.dispatch.Dispatch;
import com.hotent.makshi.model.dispatch.DocDic;
import com.hotent.makshi.model.doc.Doc;
import com.hotent.makshi.service.dispatch.DicService;
import com.hotent.makshi.service.dispatch.DispatchService;
import com.hotent.makshi.service.dispatch.DocDicService;
import com.hotent.makshi.service.doc.DocService;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.system.DictionaryService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserService;

@Component("dispatchFetcher")
public class DispatchFetcher implements IFetcher {
	@Resource
	private DispatchService dispatchService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private DocService docService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private DocDicService docDicService;
	@Resource
	private DicService dicService;
	
	private static Logger logger=Logger.getLogger(DispatchFetcher.class);
	
	@Override
	public void fetcheData(String tableName, String businessKey) throws Exception {
		Dispatch dispatch = dispatchService.getById(Long.valueOf(businessKey));
		ProcessRun processRun = processRunService.getByBusinessKey(businessKey);
		Long runId = processRun.getRunId();
		String sendType = dispatch.getDisOrgs();
		String title = dispatch.getTitle();
		SysUser curUser = new SysUser();
		curUser = sysUserService.getById(Long.valueOf(dispatch.getDraft_personID()));
		String receiverId = "";
		String receiverName = "";
		if(StringUtil.isNotEmpty(sendType)){
			Integer type = Integer.valueOf(sendType);
			//发给申请人
			if(type==1){
				receiverId = dispatch.getDraft_personID();
				receiverName = dispatch.getDraft_person();
			}else{
				String roleName  = "";
				//发给资产管理员
				if(type==2){
					 roleName = "bpm_asset_manager";		
				}
				//发给公司领导、部门经理
				if(type==3){
					roleName = "bpm_bmld";
				}
				SysRole list = sysRoleService.getByRoleAlias(roleName);
				List<SysUser> sysUserList = sysUserService.getByRoleId(list.getRoleId());
				for(SysUser sysUser : sysUserList ){
					receiverId = receiverId +sysUser.getUserId().toString()+",";
					receiverName = receiverName + sysUser.getFullname()+",";
				}
			}
			dispatchService.sendDispatch(receiverId,receiverName,curUser,runId,title,"");
			dispatch.setState("1");
			dispatchService.update(dispatch);
			//获取docId、dicId
			Doc doc = docService.getbyDocName(DateUtil.formatDate(dispatch.getTime(),"yyyy"));
			if(null==doc){
				//新建一个目录
				throw new RuntimeException("未找到文件夹"+ DateUtil.formatDate(dispatch.getTime(),"yyyy"));
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("docId", doc.getDocid());
			param.put("dicName", dispatch.getType());
			List<Dic> dicList = dicService.getByDocIdAndDicName(param);
			if(dicList.size()==0){
				throw new RuntimeException(doc.getDocname()+"目录未找到"+ dispatch.getType()+"子目录");
			}
			DocDic docDic = new DocDic();
			docDic.setDicId(dicList.get(0).getDicId());
			docDic.setDocId(doc.getDocid());
			docDic.setDisId(dispatch.getId());
			docDicService.save(docDic);
		}
	}
}
	

