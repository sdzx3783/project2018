package com.hotent.makshi.service.qualification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.qualification.CertificateAndBorrowDao;
import com.hotent.makshi.model.qualification.CertificateAndBorrow;

@Service
public class CertificateAndBorrowService extends BaseService<CertificateAndBorrow>
{
	@Resource
	private CertificateAndBorrowDao dao;
	
	public CertificateAndBorrowService()
	{
	}
	
	@Override
	protected IEntityDao<CertificateAndBorrow,Long> getEntityDao() 
	{
		return dao;
	}
	
	
	public void save(CertificateAndBorrow certificateAndBorrow) throws Exception{
		Long id=certificateAndBorrow.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			certificateAndBorrow.setId(id);
			this.add(certificateAndBorrow);
		}else{
			this.update(certificateAndBorrow);
		}
	}	
	
	public List<CertificateAndBorrow> getByCertifateId(Long linkId){
		return dao.getBySqlKey("getByCertifateId",linkId); 
	}
	
	public List<CertificateAndBorrow> getInfoByCertifateId(Long linkId){
		return dao.getBySqlKey("getInfoByCertifateId",linkId); 
	}
	
	public int updateDelete(Long linkId){
		 List<CertificateAndBorrow>  list = getByCertifateId(linkId);
		 if(list.size()>0){
			 CertificateAndBorrow certi = list.get(0);
			 certi.setDeleted(1);
			 return dao.update(certi); 
		 }
	return 0;
	}
	
	public int updateInfoDelete(Long linkId){
		 List<CertificateAndBorrow>  list = getInfoByCertifateId(linkId);
		 //资格证、注册证
		 if(list.size()>0){
			 for(CertificateAndBorrow certificateAndBorrow:list){
				 certificateAndBorrow.setDeleted(1);
				 dao.update(certificateAndBorrow); 
			 }
		 }
	return 0;
	}

	public List<CertificateAndBorrow> getInfoByUserId(String userId, String type) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("type", type);
/*		if(Integer.valueOf(type)==3){
			return dao.getBySqlKey("getSealInfoByUserId", params);
		}*/
		return dao.getBySqlKey("getInfoByUserId", params); 
	}

	public List<CertificateAndBorrow> getInfoByUserName(String userName, String type) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userName", userName);
		params.put("type", type);
/*		if(Integer.valueOf(type)==3){
			return dao.getBySqlKey("getSealInfoByUserName", params);
		}*/
		return dao.getBySqlKey("getInfoByUserName", params); 
		
	}
	
	public List<CertificateAndBorrow> getDuplicateName(String userName, String type,String userId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userName", userName);
		params.put("type", type);
		params.put("userId", userId);
	/*	if(Integer.valueOf(type)==3){
			return dao.getBySqlKey("getSealDuplicateName", params);
		}*/
		return dao.getBySqlKey("getDuplicateName", params); 
		
	}

	public List<CertificateAndBorrow> getAccount(String userName, String type, String userId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userName", userName);
		params.put("type", type);
		params.put("userId", userId);
		return dao.getBySqlKey("getAccount", params); 
	}

	public List<CertificateAndBorrow> getByTypeAndCertificateId(Integer type,String name, String certificateId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", type);
		params.put("name", name);
		params.put("certificateId", certificateId);
		List<CertificateAndBorrow> list = dao.getBySqlKey("getByTypeAndCertificateId", params); 
		return list;
	}

	public int updateByCertificateId(CertificateAndBorrow certificateAndBorrow) {
		return dao.update("updateByCertificateId", certificateAndBorrow);
		
	}

	public CertificateAndBorrow getByLinkId(Long linkId,Integer type) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("type", type);
			params.put("linkId", linkId);
			List<CertificateAndBorrow> list = dao.getBySqlKey("getByLinkId", params);
			if(list.size()>0){
				return list.get(0);
			}
			return null;
		}

	public List<CertificateAndBorrow> getByIdAndType(String certificateId,Integer type, Long linkId) {
		Map<String,Object> param = new HashMap<String,Object>();
		if(certificateId==null||certificateId.length()==0){
			return null;
		}
		param.put("certificateId", certificateId);
		param.put("type", type);
		param.put("linkId", linkId);
		return dao.getBySqlKey("getByIdAndType", param);
		 
	}

	public int delByLinkId(Long id) {
		return dao.delBySqlKey("delByLinkId", id);
	}

	public List<CertificateAndBorrow> getByBorrowerId(long userId) {
		return dao.getBySqlKey("getByBorrowerId", userId); 
	}
}
