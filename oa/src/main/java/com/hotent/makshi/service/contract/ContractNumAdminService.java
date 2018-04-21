package com.hotent.makshi.service.contract;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.contract.ContractNumAdminDao;
import com.hotent.makshi.dao.contract.ContractNumSecondDao;
import com.hotent.makshi.model.contract.ContractNumAdmin;
import com.hotent.makshi.model.contract.ContractNumSecond;
import com.hotent.makshi.model.contract.ContractNumYear;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;


@Service
public class ContractNumAdminService extends BaseService<ContractNumAdmin>
{
	@Resource
	private ContractNumAdminDao dao;
	@Resource
	private ContractNumYearService contractNumYearService;
	@Resource
	private ContractNumSecondDao contractNumSecondDao;
	public ContractNumAdminService()
	{
	}
	
	@Override
	protected IEntityDao<ContractNumAdmin,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param id
	 */
	private void delByPk(Long id){
		contractNumSecondDao.delByMainId(id);
	}
	
	/**
	 * 删除数据 包含相应子表记录
	 * @param lAryId
	 */
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	/**
	 * 添加数据 
	 * @param contractNumAdmin
	 * @throws Exception
	 */
	public void addAll(ContractNumAdmin contractNumAdmin) throws Exception{
		super.add(contractNumAdmin);
		addSubList(contractNumAdmin);
	}
	
	/**
	 * 更新数据
	 * @param contractNumAdmin
	 * @throws Exception
	 */
	public void updateAll(ContractNumAdmin contractNumAdmin) throws Exception{
		super.update(contractNumAdmin);
		delByPk(contractNumAdmin.getId());
		addSubList(contractNumAdmin);
	}
	
	/**
	 * 添加子表记录
	 * @param contractNumAdmin
	 * @throws Exception
	 */
	public void addSubList(ContractNumAdmin contractNumAdmin) throws Exception{
		
		List<ContractNumSecond> contractNumSecondList=contractNumAdmin.getContractNumSecondList();
		if(BeanUtils.isNotEmpty(contractNumSecondList)){
			
			for(ContractNumSecond contractNumSecond:contractNumSecondList){
				contractNumSecond.setRefId(contractNumAdmin.getId());
				Long id=UniqueIdUtil.genId();
				contractNumSecond.setId(id);				
				contractNumSecondDao.add(contractNumSecond);
			}
		}
	}
	
	/**
	 * 根据外键获得二级监理合同列表
	 * @param id
	 * @return
	 */
	public List<ContractNumSecond> getContractNumSecondList(Long id) {
		return contractNumSecondDao.getByMainId(id);
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
			ContractNumAdmin contractNumAdmin=getContractNumAdmin(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				contractNumAdmin.setId(genId);
				this.addAll(contractNumAdmin);
			}else{
				contractNumAdmin.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(contractNumAdmin);
			}
			cmd.setBusinessKey(contractNumAdmin.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取ContractNumAdmin对象
	 * @param json
	 * @return
	 */
	public ContractNumAdmin getContractNumAdmin(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("contractNumSecondList", ContractNumSecond.class);
		ContractNumAdmin contractNumAdmin = (ContractNumAdmin)JSONObject.toBean(obj, ContractNumAdmin.class,map);
		return contractNumAdmin;
	}
	/**
	 * 保存 合同编号管理 信息
	 * @param contractNumAdmin
	 */

	public void save(ContractNumAdmin contractNumAdmin) throws Exception{
		Long id=contractNumAdmin.getId();
		ContractNumYear contractNumYear = new ContractNumYear();
		contractNumYear.setFlowNo(contractNumAdmin.getFlowNo());
		contractNumYear.setYear(contractNumAdmin.getYear().toString());
		contractNumYear.setContractId(id);
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			contractNumAdmin.setId(id);
			this.addAll(contractNumAdmin);
			contractNumYear.setContractId(id);
			contractNumYearService.save(contractNumYear);
		}
		else{
		    this.updateAll(contractNumAdmin);
		    contractNumYearService.save(contractNumYear);
		}
	}
	
	
	public ContractNumAdmin getContractNumAdminByType(String type){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", type);
		ContractNumAdmin contractNumAdmin = dao.getUnique("getContractNumAdminByType", params);
		return contractNumAdmin;
	}
	
	public void updateFlowNo(String contracttype,String flow){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("contracttype", contracttype);
		params.put("flow", flow);
		dao.getBySqlKey("updateFlowNo", params);
	}

	public List<ContractNumAdmin> getLoadList() {
		return dao.getBySqlKey("getLoadList");
	}

	public List<ContractNumAdmin> getByIdAndYear(Long id, String year) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("year", year);
		return dao.getBySqlKey("getByIdAndYear", params);
	}
}
