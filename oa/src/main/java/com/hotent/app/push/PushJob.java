package com.hotent.app.push;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fr.bi.cube.engine.third.edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;
import com.hotent.core.util.AppUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.model.appPush.AppPushJob;
import com.hotent.makshi.model.appPush.AppPushJobHistory;
import com.hotent.makshi.model.appPush.AppPushUser;
import com.hotent.makshi.service.appPush.AppPushJobHistoryService;
import com.hotent.makshi.service.appPush.AppPushJobService;
import com.hotent.makshi.service.appPush.AppPushUserService;
import com.hotent.platform.webservice.util.PropUtils;

public class PushJob implements Job{
	private Logger logger = LogManager.getLogger(PushJob.class);
	private static AtomicInteger  atomicInteger= new AtomicInteger(0);
	private static int restCount = Integer.valueOf(PropUtils.getPropertyByDirKey("restCount", "appPush"));
	private UmengPushService umengPushService = AppUtil.getBean(UmengPushService.class);
	private AppPushJobHistoryService appPushJobHistoryService = AppUtil.getBean(AppPushJobHistoryService.class);
	private AppPushJobService appPushJobService = AppUtil.getBean(AppPushJobService.class);
	private AppPushUserService appPushUserService = AppUtil.getBean(AppPushUserService.class);
	
/*	@Autowired
	private UmengPushService umengPushService;
	@Autowired
	private AppPushJobService appPushJobService;*/
	
	
	public void execute2(JobExecutionContext arg0) throws JobExecutionException {
		
		if(atomicInteger.intValue()==0){
			try {
				atomicInteger.getAndIncrement();
				// TODO Auto-generated method stub
//				AppPushJobService appPushJobService = AppUtil.getBean(AppPushJobService.class);
				List<AppPushJob> appPushJobList = appPushJobService.getAll();
//				AppPushUserService appPushUserService = AppUtil.getBean(AppPushUserService.class);
//				AppPushJobHistoryService appPushJobHistoryService=null;
//				UmengPushService umengPushService = null;
				QueryFilter queryFilter = new QueryFilter(new net.sf.json.JSONObject());
				queryFilter.getFilters().clear();
				//查询推送任务
				for(AppPushJob apppushjob:appPushJobList){
					if(apppushjob.getUserId()!=null){
						queryFilter.getFilters().put("userId", apppushjob.getUserId());
						List<AppPushUser> appPushUserList = appPushUserService.getAll(queryFilter);
						//获取推送信息
						for(AppPushUser appPushUser:appPushUserList){
							/*if(umengPushService==null){
								umengPushService = AppUtil.getBean(UmengPushService.class);
							}*/
							//向友盟推送消息
							JSONObject jsonObject = null;
							String appKey="";
							String appMastersecret="";
							//手机类型  ios 苹果, android 安卓
							if(appPushUser!=null&&"larva_android".equalsIgnoreCase(appPushUser.getAppType())){	
								appKey=UmengPushService.getAndroidappkey();
								appMastersecret=UmengPushService.getAndroidappmastersecret();
								jsonObject = umengPushService.sendAndroidUnicast(appPushUser.getDevicetoken(), apppushjob.getTicker(), apppushjob.getTitle(), apppushjob.getContent(), apppushjob.getUrl());
							}else if(appPushUser!=null&&"larva_ios".equalsIgnoreCase(appPushUser.getAppType())){
								appKey=UmengPushService.getIosappkey();
								appMastersecret=UmengPushService.getIosappmastersecret();
								jsonObject = umengPushService.sendIOSUnicast(appPushUser.getDevicetoken(), apppushjob.getContent(), apppushjob.getUrl());
							}else{
								logger.error(" 手机推送类型  未知！");
								return;
							}
							try {
								//推送完成将数据转存至历史表  以方便后续查询
								JSONObject pushjson = (JSONObject) jsonObject.get("data");
/*								if(appPushJobHistoryService==null){
									appPushJobHistoryService = AppUtil.getBean(AppPushJobHistoryService.class);
								}*/
								queryFilter.getFilters().clear();
								queryFilter.getFilters().put("jobId", apppushjob.getId());
								List<AppPushJobHistory> appPushJobHistories =  appPushJobHistoryService.getAll(queryFilter);
								AppPushJobHistory appPushJobHistory = new AppPushJobHistory();
								
								if(appPushJobHistories.size()>0){
									appPushJobHistory = appPushJobHistories.get(0);
								}else{									
									appPushJobHistory.setResetCount(1L);
								}
								appPushJobHistory.setAppType(appPushUser.getAppType());
								appPushJobHistory.setTicker(apppushjob.getTicker());
								appPushJobHistory.setTitle(apppushjob.getTitle());
								appPushJobHistory.setContent(apppushjob.getContent());
								appPushJobHistory.setJobId(apppushjob.getId());
								appPushJobHistory.setRunId(apppushjob.getRunId());
								appPushJobHistory.setTaskId(apppushjob.getTaskId());
								appPushJobHistory.setUrl(apppushjob.getUrl());
								appPushJobHistory.setUserId(apppushjob.getUserId());
								appPushJobHistory.setAppKey(appKey);
								appPushJobHistory.setAppMastersecret(appMastersecret);
								//推送友盟成功 则删 除任务和历史数据
								if("SUCCESS".equals(jsonObject.get("ret"))){
									appPushJobHistory.setError_code(0L);
									appPushJobService.delJobAndAddOrUpdateHistory(appPushJobHistory);
								}else{
									appPushJobHistory.setError_code(Long.valueOf(pushjson.get("error_code").toString()));
									appPushJobHistory.setResetCount(appPushJobHistory.getResetCount()+1);
									//重试次数过多  则直接删除任务
									if(appPushJobHistory.getResetCount()>restCount){
										appPushJobService.delById(apppushjob.getId());
									}else{
										if(appPushJobHistory.getId()==null){
											appPushJobHistoryService.add(appPushJobHistory);
										}else{
											appPushJobHistoryService.update(appPushJobHistory);
										}
									}
								}
							} catch (JSONException e) {
								throw e;
							}
						}
					}else{
						System.out.println("userId"+apppushjob.getUserId());
						logger.error("userId 为空！ jobId:"+apppushjob.getId());
					}
				}
				atomicInteger.getAndDecrement();
			} catch (Exception e) {
				if(atomicInteger.intValue()==1){
					atomicInteger.getAndDecrement();
				}
				e.printStackTrace();
				logger.error(e);
			}
		}

	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		if(atomicInteger.intValue()==0){
			try {
				atomicInteger.getAndIncrement();
				// TODO Auto-generated method stub
//				AppPushJobService appPushJobService = AppUtil.getBean(AppPushJobService.class);
				List<AppPushJob> appPushJobList = appPushJobService.getAll();
//				AppPushUserService appPushUserService = AppUtil.getBean(AppPushUserService.class);
//				UmengPushService umengPushService = null;
//				AppPushJobHistoryService appPushJobHistoryService=null;
				QueryFilter queryFilter = new QueryFilter(new net.sf.json.JSONObject());
				queryFilter.getFilters().clear();
				//查询推送任务
				for(AppPushJob apppushjob:appPushJobList){
					//"0"表示为个人推送 
					if(apppushjob.getPushType().equals("0")){
						if(apppushjob.getUserId()!=null){
							queryFilter.getFilters().put("userId", apppushjob.getUserId());
							List<AppPushUser> appPushUserList = appPushUserService.getAll(queryFilter);
							//获取推送信息
							for(AppPushUser appPushUser:appPushUserList){
								/*if(umengPushService==null){
									umengPushService = AppUtil.getBean(UmengPushService.class);
								}*/
								sendOrReset(appPushUser,apppushjob);
							}
						}else{
							System.out.println("userId"+apppushjob.getUserId());
							logger.error("userId 为空！ jobId:"+apppushjob.getId());
						}
					}else if(apppushjob.getPushType().equals("1")){
						//"1"表示为组织推送
						if(apppushjob.getOrgId()!=null){
							//向该组织下 app在线用户推送消息
							List<AppPushUser> appPushUserList = appPushUserService.getByOrgId(apppushjob.getOrgId());
							for(AppPushUser appPushUser:appPushUserList){	
								//排除已推送成功的用户
								/*<if test="@Ognl@isNotEmpty(jobId)"> AND jobId  =#{jobId} </if>
								<if test="@Ognl@isNotEmpty(pushType)"> AND PUSHTYPE  = #{pushType}  </if>
								<if test="@Ognl@isNotEmpty(orgId)"> AND ORGID  = #{orgId}  </if>
								<if test="@Ognl@isNotEmpty(userId)"> AND USERID  = #{userId}  </if>*/
								queryFilter.getFilters().clear();
								queryFilter.getFilters().put("jobId", apppushjob.getId());
								queryFilter.getFilters().put("pushType", apppushjob.getPushType());
								queryFilter.getFilters().put("orgId", apppushjob.getOrgId());
								queryFilter.getFilters().put("userId", appPushUser.getUserId());
								List<AppPushJobHistory> appPushJobHistories = appPushJobHistoryService.getAll(queryFilter);
								if(appPushJobHistories.size()<=1){
									if(appPushJobHistories.size()==0||(appPushJobHistories.get(0).getError_code()!=0L&&appPushJobHistories.get(0).getResetCount()<restCount)){										
										sendOrReset(appPushUser,apppushjob);
									}
								}else{
									logger.error("同一条记录  在历史表中不唯一！jobId="+ apppushjob.getId()+" pushType= "+apppushjob.getPushType()+" orgId= "+apppushjob.getOrgId()+" userId= "+appPushUser.getUserId());
								}
							}
						}
					}
				}
				atomicInteger.getAndDecrement();
			} catch (Exception e) {
				if(atomicInteger.intValue()==1){
					atomicInteger.getAndDecrement();
				}
				e.printStackTrace();
				logger.error(e);
			}
		}

	}
	public void sendOrReset(AppPushUser appPushUser,AppPushJob apppushjob) throws Exception{
		//向友盟推送消息
		JSONObject jsonObject = null;
		String appKey="";
		String appMastersecret="";
		//手机类型  ios 苹果, android 安卓
		if(appPushUser!=null&&"larva_android".equalsIgnoreCase(appPushUser.getAppType())){	
			appKey=UmengPushService.getAndroidappkey();
			appMastersecret=UmengPushService.getAndroidappmastersecret();
			jsonObject = umengPushService.sendAndroidUnicast(appPushUser.getDevicetoken(), apppushjob.getTicker(), apppushjob.getTitle(), apppushjob.getContent(), apppushjob.getUrl());
		}else if(appPushUser!=null&&"larva_ios".equalsIgnoreCase(appPushUser.getAppType())){
			appKey=UmengPushService.getIosappkey();
			appMastersecret=UmengPushService.getIosappmastersecret();
			jsonObject = umengPushService.sendIOSUnicast(appPushUser.getDevicetoken(), apppushjob.getContent(), apppushjob.getUrl());
		}else{
			logger.error(" 手机推送类型  未知！");
			return;
		}
		try {
			//推送完成将数据转存至历史表  以方便后续查询
			JSONObject pushjson = (JSONObject) jsonObject.get("data");
/*			if(appPushJobHistoryService==null){
				appPushJobHistoryService = AppUtil.getBean(AppPushJobHistoryService.class);
			}*/
			QueryFilter queryFilter = new QueryFilter(new net.sf.json.JSONObject());
			queryFilter.getFilters().clear();
			queryFilter.getFilters().put("jobId", apppushjob.getId());
			queryFilter.getFilters().put("userId", appPushUser.getUserId());
			if(apppushjob.getPushType()=="1"){
				queryFilter.getFilters().put("orgId", apppushjob.getOrgId());
			}
			List<AppPushJobHistory> appPushJobHistories =  appPushJobHistoryService.getAll(queryFilter);
			AppPushJobHistory appPushJobHistory = new AppPushJobHistory();
			
			if(appPushJobHistories.size()>0){
				appPushJobHistory = appPushJobHistories.get(0);
			}else{									
				appPushJobHistory.setResetCount(1L);
			}
			appPushJobHistory.setAppType(appPushUser.getAppType());
			appPushJobHistory.setTicker(apppushjob.getTicker());
			appPushJobHistory.setTitle(apppushjob.getTitle());
			appPushJobHistory.setContent(apppushjob.getContent());
			appPushJobHistory.setJobId(apppushjob.getId());
			appPushJobHistory.setRunId(apppushjob.getRunId());
			appPushJobHistory.setTaskId(apppushjob.getTaskId());
			appPushJobHistory.setPushType(apppushjob.getPushType());
			appPushJobHistory.setOrgId(apppushjob.getOrgId());
			appPushJobHistory.setUrl(apppushjob.getUrl());
			appPushJobHistory.setUserId(appPushUser.getUserId());
			appPushJobHistory.setAppKey(appKey);
			appPushJobHistory.setAppMastersecret(appMastersecret);
			//推送友盟成功 则删除任务
			if("SUCCESS".equals(jsonObject.get("ret"))){
				appPushJobHistory.setError_code(0L);
				appPushJobService.delJobAndAddOrUpdateHistory(appPushJobHistory);
			}else{
				appPushJobHistory.setError_code(Long.valueOf(pushjson.get("error_code").toString()));
				appPushJobHistory.setResetCount(appPushJobHistory.getResetCount()+1);
				//重试次数过多  则直接删除任务
				if(appPushJobHistory.getResetCount()>restCount){
					//组织消息  则历史记录所有失败记录都得等于失败次数 财经行删除任务操作
					if(apppushjob.getPushType()=="1"){
						queryFilter.getFilters().remove("userId");
						queryFilter.getFilters().put("resetCount", restCount);
						List<AppPushJobHistory> allOrgHistoryList =  appPushJobHistoryService.getAll(queryFilter);
						if(allOrgHistoryList.size()>1){
							return;
						}
					}
					//非组织消息 则直接删除
					appPushJobService.delById(apppushjob.getId());
				}else{
					if(appPushJobHistory.getId()==null){
						appPushJobHistoryService.add(appPushJobHistory);
					}else{
						appPushJobHistoryService.update(appPushJobHistory);
					}
				}
			}
		} catch (JSONException e) {
			throw e;
		}
	}
/*	@PostConstruct
	public void test() throws SchedulerException{
			schedulerService.addTrigger(jobName, "trigName", 1);
	}*/
}
