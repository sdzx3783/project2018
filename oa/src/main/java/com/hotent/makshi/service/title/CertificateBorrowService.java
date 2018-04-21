package com.hotent.makshi.service.title;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.assetregistration.AssetRegistration;
import com.hotent.makshi.model.title.CertificateBorrow;
import com.hotent.makshi.dao.title.CertificateBorrowDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;


@Service
public class CertificateBorrowService extends WfBaseService<CertificateBorrow>
{
	@Resource
	private CertificateBorrowDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public CertificateBorrowService()
	{
	}
	
	@Override
	protected IEntityDao<CertificateBorrow,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<CertificateBorrow> getAll(QueryFilter queryFilter){
		List<CertificateBorrow> certificateBorrowList=super.getAll(queryFilter);
		List<CertificateBorrow> certificateBorrows=new ArrayList<CertificateBorrow>();
		for(CertificateBorrow certificateBorrow:certificateBorrowList){
			ProcessRun processRun=processRunService.getByBusinessKey(certificateBorrow.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				certificateBorrow.setRunId(processRun.getRunId());
			}
			certificateBorrows.add(certificateBorrow);
		}
		return certificateBorrows;
	}
	
	public List<CertificateBorrow> getMyCertificateBorrow(Long userId) {
		return dao.getBySqlKey("getMyCertificateBorrow", userId);
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
			CertificateBorrow certificateBorrow=getCertificateBorrow(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				certificateBorrow.setId(genId);
				this.add(certificateBorrow);
			}else{
				certificateBorrow.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(certificateBorrow);
			}
			cmd.setBusinessKey(certificateBorrow.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取CertificateBorrow对象
	 * @param json
	 * @return
	 */
	public CertificateBorrow getCertificateBorrow(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		CertificateBorrow certificateBorrow = (CertificateBorrow)JSONObject.toBean(obj, CertificateBorrow.class);
		return certificateBorrow;
	}
	/**
	 * 保存 个人证书借阅申请 信息
	 * @param certificateBorrow
	 */

	@WorkFlow(flowKey="certificate_borrow",tableName="certificate_borrow")
	public void save(CertificateBorrow certificateBorrow) throws Exception{
		Long id=certificateBorrow.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			certificateBorrow.setId(id);
		    this.add(certificateBorrow);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(certificateBorrow.getId().toString(), null , true,  "certificate_borrow");
		}
		else{
		    this.update(certificateBorrow);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
