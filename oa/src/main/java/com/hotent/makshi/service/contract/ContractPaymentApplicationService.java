package com.hotent.makshi.service.contract;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.contract.ContractPaymentApplication;
import com.hotent.makshi.dao.contract.ContractPaymentApplicationDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;

import com.hotent.makshi.model.contract.InvoiceDetail;
import com.hotent.makshi.dao.contract.InvoiceDetailDao;

import com.hotent.core.service.BaseService;


@Service
public class ContractPaymentApplicationService extends BaseService<ContractPaymentApplication>
{
	@Resource
	private ContractPaymentApplicationDao dao;
	
	@Resource
	private InvoiceDetailDao invoiceDetailDao;
	public ContractPaymentApplicationService()
	{
	}
	
	@Override
	protected IEntityDao<ContractPaymentApplication,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param id
	 */
	private void delByPk(Long id){
		invoiceDetailDao.delByMainId(id);
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
	 * @param contractPaymentApplication
	 * @throws Exception
	 */
	public void addAll(ContractPaymentApplication contractPaymentApplication) throws Exception{
		super.add(contractPaymentApplication);
		addSubList(contractPaymentApplication);
	}
	
	/**
	 * 更新数据
	 * @param contractPaymentApplication
	 * @throws Exception
	 */
	public void updateAll(ContractPaymentApplication contractPaymentApplication) throws Exception{
		super.update(contractPaymentApplication);
		delByPk(contractPaymentApplication.getId());
		addSubList(contractPaymentApplication);
	}
	
	/**
	 * 添加子表记录
	 * @param contractPaymentApplication
	 * @throws Exception
	 */
	public void addSubList(ContractPaymentApplication contractPaymentApplication) throws Exception{
		
		List<InvoiceDetail> invoiceDetailList=contractPaymentApplication.getInvoiceDetailList();
		if(BeanUtils.isNotEmpty(invoiceDetailList)){
			
			for(InvoiceDetail invoiceDetail:invoiceDetailList){
				invoiceDetail.setRefId(contractPaymentApplication.getId());
				Long id=UniqueIdUtil.genId();
				invoiceDetail.setId(id);				
				invoiceDetailDao.add(invoiceDetail);
			}
		}
	}
	
	/**
	 * 根据外键获得发票明细列表
	 * @param id
	 * @return
	 */
	public List<InvoiceDetail> getInvoiceDetailList(Long id) {
		return invoiceDetailDao.getByMainId(id);
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
			ContractPaymentApplication contractPaymentApplication=getContractPaymentApplication(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				contractPaymentApplication.setId(genId);
				this.addAll(contractPaymentApplication);
			}else{
				contractPaymentApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(contractPaymentApplication);
			}
			cmd.setBusinessKey(contractPaymentApplication.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取ContractPaymentApplication对象
	 * @param json
	 * @return
	 */
	public ContractPaymentApplication getContractPaymentApplication(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("invoiceDetailList", InvoiceDetail.class);
		ContractPaymentApplication contractPaymentApplication = (ContractPaymentApplication)JSONObject.toBean(obj, ContractPaymentApplication.class,map);
		return contractPaymentApplication;
	}
	/**
	 * 保存 合同付款申请 信息
	 * @param contractPaymentApplication
	 */

	public void save(ContractPaymentApplication contractPaymentApplication) throws Exception{
		Long id=contractPaymentApplication.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			contractPaymentApplication.setId(id);
			this.addAll(contractPaymentApplication);
		}
		else{
		    this.updateAll(contractPaymentApplication);
		}
	}
}
