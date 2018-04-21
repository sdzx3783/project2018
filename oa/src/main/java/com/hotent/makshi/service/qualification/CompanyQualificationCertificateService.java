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
import com.hotent.makshi.model.qualification.CompanyQualificationCertificate;
import com.hotent.makshi.dao.qualification.CompanyQualificationCertificateDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class CompanyQualificationCertificateService extends BaseService<CompanyQualificationCertificate>
{
	@Resource
	private CompanyQualificationCertificateDao dao;
	
	public CompanyQualificationCertificateService()
	{
	}
	
	@Override
	protected IEntityDao<CompanyQualificationCertificate,Long> getEntityDao() 
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
			CompanyQualificationCertificate companyQualificationCertificate=getCompanyQualificationCertificate(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				companyQualificationCertificate.setId(genId);
				this.add(companyQualificationCertificate);
			}else{
				companyQualificationCertificate.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(companyQualificationCertificate);
			}
			cmd.setBusinessKey(companyQualificationCertificate.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取CompanyQualificationCertificate对象
	 * @param json
	 * @return
	 */
	public CompanyQualificationCertificate getCompanyQualificationCertificate(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		CompanyQualificationCertificate companyQualificationCertificate = (CompanyQualificationCertificate)JSONObject.toBean(obj, CompanyQualificationCertificate.class);
		return companyQualificationCertificate;
	}
	/**
	 * 保存 公司资质证书 信息
	 * @param companyQualificationCertificate
	 */

	public void save(CompanyQualificationCertificate companyQualificationCertificate) throws Exception{
		Long id=companyQualificationCertificate.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			companyQualificationCertificate.setId(id);
		    this.add(companyQualificationCertificate);
		}
		else{
		    this.update(companyQualificationCertificate);
		}
	}

	public List<CompanyQualificationCertificate> getByCtype(String ctype) {
		Map<String,Object> params=new HashMap<>();
		params.put("ctype", ctype);
		return dao.getBySqlKey("getByCtype", params);
	}
}
