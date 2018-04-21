package com.hotent.makshi.service.contract;

import java.util.ArrayList;
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
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.contract.ContractBillingRecordDao;
import com.hotent.makshi.dao.contract.ContractNumAdminDao;
import com.hotent.makshi.dao.contract.ContractPaymentRecordDao;
import com.hotent.makshi.dao.contract.ContractinfoDao;
import com.hotent.makshi.model.contract.ContractBillingRecord;
import com.hotent.makshi.model.contract.ContractNumAdmin;
import com.hotent.makshi.model.contract.ContractNumYear;
import com.hotent.makshi.model.contract.ContractPaymentRecord;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.utils.ArithmeticUtil;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Service
public class ContractinfoService extends BaseService<Contractinfo> {
	@Resource
	private ContractinfoDao dao;

	@Resource
	private ContractBillingRecordDao contractBillingRecordDao;
	@Resource
	private ContractPaymentRecordDao contractPaymentRecordDao;
	@Resource
	private ContractnumService contractnumService;
	@Resource
	private ContractNumAdminDao contractNumAdminDao;
	@Resource
	private ContractNumAdminService contractNumAdminService;
	@Resource
	private ContractNumYearService contractNumYearService;
	
	public ContractinfoService() {
	}

	@Override
	protected IEntityDao<Contractinfo, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 根据外键删除子表记录
	 * 
	 * @param id
	 */
	private void delByPk(Long id) {
		contractBillingRecordDao.delByMainId(id);
		contractPaymentRecordDao.delByMainId(id);
	}

	/**
	 * 删除数据 包含相应子表记录
	 * 
	 * @param lAryId
	 */
	public void delAll(Long[] lAryId) {
		for (Long id : lAryId) {
			delByPk(id);
			dao.delById(id);
		}
	}

	/**
	 * 添加数据
	 * 
	 * @param contractinfo
	 * @throws Exception
	 */
	public void addAll(Contractinfo contractinfo) throws Exception {
		super.add(contractinfo);
		addSubList(contractinfo);
	}

	/**
	 * 更新数据
	 * 
	 * @param contractinfo
	 * @throws Exception
	 */
	public void updateAll(Contractinfo contractinfo) throws Exception {
		super.update(contractinfo);
		delByPk(contractinfo.getId());
		addSubList(contractinfo);
	}

	public void addBillingRecord(ContractBillingRecord contractBillingRecord) {
		contractBillingRecordDao.add(contractBillingRecord);
	}

	/**
	 * 添加子表记录
	 * 
	 * @param contractinfo
	 * @throws Exception
	 */
	public void addSubList(Contractinfo contractinfo) throws Exception {

		List<ContractBillingRecord> contractBillingRecordList = contractinfo.getContractBillingRecordList();
		if (BeanUtils.isNotEmpty(contractBillingRecordList)) {

			for (ContractBillingRecord contractBillingRecord : contractBillingRecordList) {
				contractBillingRecord.setRefId(contractinfo.getId());
				Long id = UniqueIdUtil.genId();
				contractBillingRecord.setId(id);
				contractBillingRecordDao.add(contractBillingRecord);
			}
		}

		List<ContractPaymentRecord> contractPaymentRecordList = contractinfo.getContractPaymentRecordList();
		if (BeanUtils.isNotEmpty(contractPaymentRecordList)) {

			for (ContractPaymentRecord contractPaymentRecord : contractPaymentRecordList) {
				contractPaymentRecord.setRefId(contractinfo.getId());
				Long id = UniqueIdUtil.genId();
				contractPaymentRecord.setId(id);
				contractPaymentRecordDao.add(contractPaymentRecord);
			}
		}
	}

	/**
	 * 根据外键获得合同开票记录列表
	 * 
	 * @param id
	 * @return
	 */
	public List<ContractBillingRecord> getContractBillingRecordList(Long id) {
		return contractBillingRecordDao.getByMainId(id);
	}
	
	public List<ContractBillingRecord> getAllContractBillingRecordList() {
		return contractBillingRecordDao.getAll();
	}
	
	/**
	 * 根据外键获得合同付款记录列表
	 * 
	 * @param id
	 * @return
	 */
	public List<ContractPaymentRecord> getContractPaymentRecordList(Long id) {
		return contractPaymentRecordDao.getByMainId(id);
	}

	/**
	 * 流程处理器方法 用于处理业务数据
	 * 
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd) throws Exception {
		Map data = cmd.getFormDataMap();
		if (BeanUtils.isNotEmpty(data)) {
			String json = data.get("json").toString();
			Contractinfo contractinfo = getContractinfo(json);
			if (StringUtil.isEmpty(cmd.getBusinessKey())) {
				Long genId = UniqueIdUtil.genId();
				contractinfo.setId(genId);
				this.addAll(contractinfo);
			} else {
				contractinfo.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(contractinfo);
			}
			cmd.setBusinessKey(contractinfo.getId().toString());
		}
	}

	/**
	 * 根据json字符串获取Contractinfo对象
	 * 
	 * @param json
	 * @return
	 */
	public Contractinfo getContractinfo(String json) {
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if (StringUtil.isEmpty(json))
			return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("contractBillingRecordList", ContractBillingRecord.class);
		map.put("contractPaymentRecordList", ContractPaymentRecord.class);
		Contractinfo contractinfo = (Contractinfo) JSONObject.toBean(obj, Contractinfo.class, map);
		return contractinfo;
	}

	/**
	 * 保存 合同基本信息 信息
	 * 
	 * @param contractinfo
	 */

	public void save(Contractinfo contractinfo) throws Exception {
		
		Long id = contractinfo.getId();
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			contractinfo.setId(id);
			this.addAll(getSort(contractinfo));
		} else {
			this.updateAll(getSort(contractinfo));
		}
		//判断是否含有该年合同类型编号（合同类型，年份）
		ContractNumAdmin contractAdmin = contractNumAdminService.getContractNumAdminByType(contractinfo.getContracttype());
		if(null==contractAdmin){
			return;
		}
		ContractNumYear contractNumYear = contractNumYearService.getByRefIdAndYear( contractAdmin.getId(),contractinfo.getYearSort().toString());
		//添加或更新该年合同编号流水号
		if(null==contractNumYear){
			contractNumYear = new ContractNumYear();
			contractNumYear.setFlowNo(contractinfo.getNumSort().toString());
			contractNumYear.setContractId(contractAdmin.getId());
			contractNumYear.setYear(contractinfo.getYearSort().toString());
			contractNumYearService.save(contractNumYear);
		}
		contractNumYearService.updateFlowNo(contractNumYear.getId());
	/*	Map<String,Object> params = new HashMap<String,Object>();
		params.put("contracttype", contractinfo.getContracttype());
		params.put("flow", contractinfo.getNumSort());
		contractNumAdminDao.getBySqlKey("updateFlowNo", params);*/
		contractnumService.updateContractnum(contractinfo.getContract_num());
	}

	
	public Contractinfo getSort(Contractinfo contractinfo) {
		//通过合同编号设置排序
		String contractNum = contractinfo.getContract_num();
		String[] sortArr = getSortDetail(contractNum);
		contractinfo.setYearSort(Integer.valueOf(sortArr[0]));
		contractinfo.setNumSort(Integer.valueOf(sortArr[1]));
		contractinfo.setSecondSort(Integer.valueOf(sortArr[2]));
		return contractinfo;
	}
	
	
	public String[] getSortDetail(String contractNum){
		String [] str = new String[3];
		//获取年份、编号、子编号
		//合同编号格式为CG2017-1、CG2017-1（补充协议）、CG2017-1-1、CG2017-1-1（xxxxx）
		//获取第一个横杠
		int beginIndex = contractNum.indexOf("-");
		//获取第一个括号
		int addIndex = contractNum.indexOf("（");
		//获取第二个横杠
		int secondIndex = contractNum.indexOf("-",beginIndex+1);
		String yearSort = contractNum.substring(beginIndex-4, beginIndex);
		String numSort = "0";
		String secondSort = "0";
		if(addIndex>0 && secondIndex<0){
			numSort = contractNum.substring(beginIndex+1,addIndex);
		}else if(secondIndex>0){
			//获取第二横杠后的括号
			if(addIndex>0){
				secondSort = contractNum.substring(secondIndex+1,addIndex);
			}else{
				secondSort = contractNum.substring(secondIndex+1,contractNum.length());
			}
			numSort = contractNum.substring(beginIndex+1,secondIndex);
		}else{
			numSort = contractNum.substring(beginIndex+1,contractNum.length());
		}
		str[0] = yearSort;
		str[1] = numSort;
		str[2] = secondSort;
		return str;
	}
	
	public Contractinfo getContractinfoByNum(String contractNum) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractNum", contractNum);
		Contractinfo contractinfo = dao.getUnique("getContractinfoByNum", params);

		return contractinfo;
	}

	public Contractinfo getSimpleinfoByNum(String contractNum) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractNum", contractNum);
		return dao.getUnique("getSimpleinfoByNum", params);
	}

	public List<String> getContractType() {
		List<String> contracttypeList = new ArrayList<String>();
		List<Contractinfo> list = dao.getBySqlKey("getContractType");
		if (list.size() > 0) {
			for (Contractinfo contractinfo : list) {
				if (null != contractinfo && !StringUtil.isEmpty(contractinfo.getContracttype())) {
					contracttypeList.add(contractinfo.getContracttype());
				}
			}
		}
		return contracttypeList;
	}

	public Contractinfo getByTypeAndNum(String type, String contractId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractNum", contractId);
		params.put("type", type);
		List<Contractinfo> list = dao.getBySqlKey("getByTypeAndNum", params);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<Contractinfo> getNumUpLoad(Map<String, Object> param) {
		List<Contractinfo> list = dao.getBySqlKey("getNumUpLoad", param);
		if (list.size() > 0) {
			return list;
		}
		return null;
	}

	public List<Contractinfo> getListByHandleId(String userName) {
		List<Contractinfo> list = dao.getBySqlKey("getListByHandleId", userName);
		if (list.size() > 0) {
			return list;
		}
		return null;
	}

	public void incrAmount(Long id, double invoice_money, double arrival_money) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("invoice_money", ArithmeticUtil.div(invoice_money, 10000, 4));
		params.put("arrival_money", ArithmeticUtil.div(arrival_money, 10000, 4));
		dao.update("incrAmount", params);
	}

	public void updateContractStatus(String contractNo, String project_status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractNo", contractNo);
		params.put("project_status", project_status);
		dao.update("updateContractStatus", params);
	}

	public List<Contractinfo> getListForMobileDialog(QueryFilter queryFilter){
		return dao.getBySqlKey("getListForMobileDialog", queryFilter);
	}

	public String updateNum (String num){
		int beginIndex = num.indexOf("-");
		//获取第一个括号
		int addIndex = num.indexOf("（");
		//获取第二个横杠
		int secondIndex = num.indexOf("-",beginIndex+1);
		//第一部分
		String firstPart = num.substring(0, beginIndex+1);
		//第二部分
		String secondPart = "0";
		//第三部分
		String thirdPart = "";
		if(addIndex>0 && secondIndex<0){
			//CG2017-1（补充协议）
			secondPart = num.substring(beginIndex+1,addIndex);
			thirdPart = num.substring(addIndex,num.length());
		}else if(secondIndex>0){
			//CG2017-1-1、CG2017-1-1（xxxxx）
			secondPart = num.substring(beginIndex+1,secondIndex);
			thirdPart = num.substring(secondIndex,num.length());
		}else{
			//CG2017-1
			secondPart = num.substring(beginIndex+1,num.length());
		}
		secondPart = addZero(secondPart,3);
		num = firstPart + secondPart + thirdPart;
		return num;
	}
	
	//反取合同编号
	public  String oppositeNum(String num){
		int beginIndex = num.indexOf("-");
		//获取第一个括号
		int addIndex = num.indexOf("（");
		//获取第二个横杠
		int secondIndex = num.indexOf("-",beginIndex+1);
		//第一部分
		String firstPart = num.substring(0, beginIndex+1);
		//第二部分
		String secondPart = "0";
		//第三部分
		String thirdPart = "";
		if(addIndex>0 && secondIndex<0){
			//CG2017-001（补充协议）
			secondPart = num.substring(beginIndex+1,addIndex);
			thirdPart = num.substring(addIndex,num.length());
		}else if(secondIndex>0){
			//CG2017-001-1、CG2017-001-1（xxxxx）
			secondPart = num.substring(beginIndex+1,secondIndex);
			thirdPart = num.substring(secondIndex,num.length());
		}else{
			//CG2017-001
			secondPart = num.substring(beginIndex+1,num.length());
		}
		secondPart = Integer.valueOf(secondPart).toString();
		num = firstPart + secondPart + thirdPart;
		return num;
	}
	
	public String addZero(String str,int length){
		int len = str.length();
		for(int i=len;i<length;i++){
			str = "0"+str;
		}
		return str;
	}
}
