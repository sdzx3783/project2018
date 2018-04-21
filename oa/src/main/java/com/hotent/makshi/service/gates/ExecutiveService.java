package com.hotent.makshi.service.gates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.gates.ExecutiveDao;
import com.hotent.makshi.model.gates.ContractWordNo;
import com.hotent.makshi.model.gates.Executive;
import com.hotent.makshi.model.gates.ExecutiveHouseStastics;
import com.hotent.makshi.utils.DateUtils;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ExecutiveService extends BaseService<Executive> {

	@Resource
	private ExecutiveDao executiveDao;

	@Override
	protected IEntityDao getEntityDao() {
		return executiveDao;
	}

	/**
	 * 根据分割符.获取对应的等级的部门信息
	 * 
	 * @param level
	 * @return
	 */
	public List getOrgsBySplitLevel(int level) {
		Map<String, Object> param = new HashMap<>();
		param.put("level", level);
		return executiveDao.getBySqlKey("getOrgsBySplitLevel", param);
	}

	/**
	 * 获取某个部门以及下属部门的人数数据
	 * 
	 * @param orgId
	 * @return
	 */
	public Executive getDepartmentPeople(Long orgId) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		return (Executive) executiveDao.getOne("getDepartmentPeople", param);
	}

	/**
	 * 获取某个部门的人数数据，不包含下属部门
	 * 
	 * @param orgId
	 * @return
	 */
	public Executive getDepartmentMaxPeople(Long orgId) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		return (Executive) executiveDao.getOne("getDepartmentMaxPeople", param);
	}

	/**
	 * 获取合同的统计数据，orgid有值表示只查询某一个部门的，没值查询所有部门的数据
	 * 
	 * @return
	 */
	public List getContractTjByYears(String orgid) {
		Map<String, Object> param = new HashMap<>();
		param.put("time1", DateUtils.getDefinedYear(0, "yyyy"));
		param.put("time2", DateUtils.getDefinedYear(-1, "yyyy"));
		param.put("time3", DateUtils.getDefinedYear(-2, "yyyy"));
		param.put("time4", DateUtils.getDefinedYear(-3, "yyyy"));
		param.put("time5", DateUtils.getDefinedYear(-4, "yyyy"));
		if (StringUtils.isNotEmpty(orgid))
			param.put("orgid", orgid);
		return executiveDao.getBySqlKey("getContractTjByYears", param);
	}

	/**
	 * 合同收款统计
	 * 
	 * @param orgid
	 * @return
	 */
	public List getContractBillingByYears(String orgid) {
		Map<String, Object> param = new HashMap<>();
		param.put("time1", DateUtils.getDefinedYear(0, "yyyy"));
		param.put("time2", DateUtils.getDefinedYear(-1, "yyyy"));
		param.put("time3", DateUtils.getDefinedYear(-2, "yyyy"));
		param.put("time4", DateUtils.getDefinedYear(-3, "yyyy"));
		param.put("time5", DateUtils.getDefinedYear(-4, "yyyy"));
		if (StringUtils.isNotEmpty(orgid))
			param.put("orgid", orgid);
		return executiveDao.getBySqlKey("getContractBillingByYears", param);
	}

	/**
	 * 获取所有资产类型
	 * 
	 * @return
	 */
	public List getAssetCodes() {
		return executiveDao.getBySqlKey("getAssetCodes");
	}

	/**
	 * 获取某个部门以及下属部门的资产数据
	 * 
	 * @param orgId
	 * @return
	 */
	public List getAssetByOrgId(String orgId) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		return executiveDao.getBySqlKey("getAssetByOrgId", param);
	}

	/**
	 * 获取某个部门的资产数据,不包含下属部门
	 * 
	 * @param orgId
	 * @return
	 */
	public List getAssetMaxByOrgId(String orgId) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		return executiveDao.getBySqlKey("getAssetMaxByOrgId", param);
	}

	/**
	 * 获取某个部门以及下属部门的租房数据
	 * 
	 * @param orgId
	 * @return
	 */
	public Object getRentHouseByOrgId(String orgId) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		return executiveDao.getOne("getRentHouseByOrgId", param);
	}

	/**
	 * 获取某个部门的租房数据,不包含下属部门
	 * 
	 * @param orgId
	 * @return
	 */
	public Object getRentHouseMaxByOrgId(String orgId) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		return executiveDao.getOne("getRentHouseMaxByOrgId", param);
	}

	/**
	 * 获取某个部门以及下属部门的年龄分布数据
	 * 
	 * @param orgId
	 * @return
	 */
	public Object getPeopleAgesByOrgId(String orgId) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		param.put("cutYear", DateUtils.getDefinedYear(0, "yyyy"));
		return executiveDao.getOne("getPeopleAgesByOrgId", param);
	}

	/**
	 * 获取某个部门的年龄分布数据,不包含下属部门
	 * 
	 * @param orgId
	 * @return
	 */
	public Object getPeopleAgesMaxByOrgId(String orgId) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		param.put("cutYear", DateUtils.getDefinedYear(0, "yyyy"));
		return executiveDao.getOne("getPeopleAgesMaxByOrgId", param);
	}

	/**
	 * 获取某个部门以及下属部门的学历分布数据
	 * 
	 * @param orgId
	 * @return
	 */
	public Object getPeopleEducationByOrgId(String orgId) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		return executiveDao.getOne("getPeopleEducationByOrgId", param);
	}

	/**
	 * 获取某个部门的年龄学历数据,不包含下属部门
	 * 
	 * @param orgId
	 * @return
	 */
	public Object getPeopleEducationMaxByOrgId(String orgId) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		return executiveDao.getOne("getPeopleEducationMaxByOrgId", param);
	}

	/**
	 * 获取某个部门以及下属部门的职称分布数据
	 * 
	 * @param orgId
	 * @return
	 */
	public Object getPeopleProfessionalByOrgId(String orgId) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		return executiveDao.getOne("getPeopleProfessionalByOrgId", param);
	}

	/**
	 * 获取某个部门的职称数据,不包含下属部门
	 * 
	 * @param orgId
	 * @return
	 */
	public Object getPeopleProfessionalMaxByOrgId(String orgId) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		return executiveDao.getOne("getPeopleProfessionalMaxByOrgId", param);
	}

	/**
	 * 获取资质数据
	 * 
	 * @return
	 */
	public List getCertificateStatistics() {
		return executiveDao.getBySqlKey("getCertificateStatistics");
	}

	private Map<String, Object> getDepartmentDetailPeopleQueryParams(String orgId, Integer sex, String jobno, String name, String education, String posname, String positional,
			String posid, String agesMin, String agesMax, String entrydateStart, String entrydateEnd, String school, String positionalMajor, String querygo) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		param.put("cutYear", DateUtils.getDefinedYear(0, "yyyy"));
		param.put("sex", sex == null ? null : String.valueOf(sex));
		param.put("jobno", jobno);
		param.put("education", education);
		param.put("positional", positional);
		param.put("posid", posid);
		param.put("agesMin", agesMin);
		param.put("agesMax", agesMax);
		param.put("entrydateStart", entrydateStart);
		param.put("entrydateEnd", entrydateEnd);
		param.put("school", school);
		param.put("positionalMajor", positionalMajor);
		param.put("querygo", querygo);
		if (StringUtils.isNotEmpty(name))
			param.put("name", "%" + name + "%");
		if (StringUtils.isNotEmpty(posname))
			param.put("posname", "%" + posname + "%");

		if (StringUtils.isNotEmpty(school))
			param.put("school", "%" + school + "%");

		if (StringUtils.isNotEmpty(positionalMajor))
			param.put("positionalMajor", "%" + positionalMajor + "%");
		return param;
	}

	/**
	 * 人力资源管理:人员统计明细表
	 * 
	 * @return
	 */
	public int getDepartmentDetailPeopleCount(String orgId, Integer sex, String jobno, String name, String education, String posname, String positional, String posid,
			String agesMin, String agesMax, String entrydateStart, String entrydateEnd, String school, String positionalMajor, String querygo) {
		if (StringUtils.isEmpty(orgId))
			return 0;
		Map<String, Object> param = getDepartmentDetailPeopleQueryParams(orgId, sex, jobno, name, education, posname, positional, posid, agesMin, agesMax, entrydateStart,
				entrydateEnd, school, positionalMajor, querygo);
		return (int) executiveDao.getOne("getDepartmentDetailPeopleCount", param);
	}

	public List getDepartmentDetailPeoplePage(String orgId, Integer sex, String jobno, String name, String education, String posname, String positional, String posid,
			String agesMin, String agesMax, String entrydateStart, String entrydateEnd, String school, String positionalMajor, String querygo, int page, int pageSize) {
		if (StringUtils.isEmpty(orgId))
			return null;
		Map<String, Object> param = getDepartmentDetailPeopleQueryParams(orgId, sex, jobno, name, education, posname, positional, posid, agesMin, agesMax, entrydateStart,
				entrydateEnd, school, positionalMajor, querygo);
		if (page <= 0)
			page = 1;
		param.put("begNum", (page - 1) * pageSize);
		param.put("pageSize", pageSize);
		return executiveDao.getBySqlKey("getDepartmentDetailPeoplePage", param);
	}

	public int getDetailContractCount(String orgId, String contracttype, String contracttype1, String contract_num, String contract_name, String contract_status,
			String first_party, String project_department, String contract_handler, String singingStart, String singingEnd, String moneyMin, String moneyMax) {
		Map<String, Object> param = getDetailContractBillingQueryParams(orgId, contracttype, contracttype1, contract_num, contract_name, contract_status, first_party,
				project_department, contract_handler, singingStart, singingEnd, moneyMin, moneyMax);
		return (int) executiveDao.getOne("getDetailContractCount", param);
	}

	public List getDetailContractPage(String orgId, String contracttype, String contracttype1, String contract_num, String contract_name, String contract_status,
			String first_party, String project_department, String contract_handler, String singingStart, String singingEnd, String moneyMin, String moneyMax, int page, int pageSize) {
		if (page <= 0)
			page = 1;
		Map<String, Object> param = getDetailContractBillingQueryParams(orgId, contracttype, contracttype1, contract_num, contract_name, contract_status, first_party,
				project_department, contract_handler, singingStart, singingEnd, moneyMin, moneyMax);
		param.put("begNum", (page - 1) * pageSize);
		param.put("pageSize", pageSize);
		return executiveDao.getBySqlKey("getDetailContractPage", param);

	}

	/**
	 * 员工月度报表
	 * 
	 * @param orgId
	 * @param yearMonth
	 * @return
	 */
	public Object getStaffByMonth(String orgId, String yearMonth) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		param.put("queryMonth", yearMonth);
		return executiveDao.getOne("getStaffByMonth", param);
	}

	/**
	 * 员工年度报表
	 * 
	 * @param orgId
	 * @param queryYear
	 * @return
	 */
	public Object getStaffByYear(String orgId, String queryYear) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		param.put("queryYear", queryYear);
		return executiveDao.getOne("getStaffByYear", param);
	}

	public Object getPeopleBetAgesMaxByOrgId(String orgId, String agesMin, String agesMax) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		param.put("agesMin", agesMin);
		param.put("agesMax", agesMax);
		param.put("cutYear", DateUtils.getDefinedYear(0, "yyyy"));
		return executiveDao.getOne("getPeopleBetAgesMaxByOrgId", param);
	}

	public Object getPeopleBetAgesByOrgId(String orgId, String agesMin, String agesMax) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		param.put("agesMin", agesMin);
		param.put("agesMax", agesMax);
		param.put("cutYear", DateUtils.getDefinedYear(0, "yyyy"));
		return executiveDao.getOne("getPeopleBetAgesByOrgId", param);
	}

	/**
	 * 劳动合同模板 获取字号
	 * 
	 * @param taskId
	 * @return
	 */
	public String getContractNo(String taskId) {
		if (StringUtils.isEmpty(taskId))
			return null;
		ContractWordNo w = (ContractWordNo) getContractWordNoByTaskId(taskId);
		if (null != w) {
			return w.getUse_at().replace("-", "") + w.getUseNo();
		}
		int use_no = (int) getContractWordNewNo();
		String useNo = "";
		if (use_no < 10)
			useNo = "0" + use_no;
		saveContractWordNo(taskId, use_no);
		return DateUtils.getDefinedYear(0, "yyyy-MM").replace("-", "") + useNo;
	}

	private Object getContractWordNoByTaskId(String taskId) {
		Map<String, Object> param = new HashMap<>();
		param.put("taskId", taskId);
		List list = executiveDao.getBySqlKey("getContractWordNoByTaskId", param);
		if (CollectionUtils.isEmpty(list))
			return null;
		return list.get(0);
	}

	private Object getContractWordNewNo() {
		Map<String, Object> param = new HashMap<>();
		param.put("cutMonth", DateUtils.getDefinedYear(0, "yyyy-MM"));
		return executiveDao.getOne("getContractWordNo", param);
	}

	private void saveContractWordNo(String taskId, int use_no) {
		Map<String, Object> param = new HashMap<>();
		param.put("use_at", DateUtils.getDefinedYear(0, "yyyy-MM"));
		param.put("taskId", taskId);
		param.put("use_no", use_no);
		param.put("create_at", DateUtils.getDefinedYear(0, "yyyy-MM-dd HH:mm:ss"));
		executiveDao.getOne("saveContractWordNo", param);
	}

	/**
	 * 租房统计信息
	 * 
	 * @param orgId
	 * @param start
	 * @param end
	 * @return
	 */
	public ExecutiveHouseStastics getRentHouseStastics(String orgId, String start, String end) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		param.put("start", start);
		param.put("end", end);
		return (ExecutiveHouseStastics) executiveDao.getOne("getRentHouseStastics", param);
	}

	private Map<String, Object> getRentHouseStasticsDetailQueryParams(String orgId, String start, String end, String houseId, String rentPerson) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgid", orgId);
		param.put("orgid1", "%" + orgId + "%");
		param.put("start", start);
		param.put("end", end);
		param.put("houseId", houseId);
		if (StringUtils.isNotEmpty(rentPerson))
			param.put("rentPerson", "%" + rentPerson + "%");
		return param;
	}

	/**
	 * 租房详情信息统计信息
	 * 
	 * @param orgId
	 * @param start
	 * @param end
	 * @param houseId
	 * @param rentPerson
	 * @return
	 */
	public int getRentHouseStasticsDetailCount(String orgId, String start, String end, String houseId, String rentPerson) {
		Map<String, Object> param = getRentHouseStasticsDetailQueryParams(orgId, start, end, houseId, rentPerson);
		return (int) executiveDao.getOne("getRentHouseStasticsDetailCount", param);
	}

	public List getRentHouseStasticsDetailPage(String orgId, String start, String end, String houseId, String rentPerson, int page, int pageSize) {
		Map<String, Object> param = getRentHouseStasticsDetailQueryParams(orgId, start, end, houseId, rentPerson);
		if (page <= 0)
			page = 1;
		param.put("begNum", (page - 1) * pageSize);
		param.put("pageSize", pageSize);
		return executiveDao.getBySqlKey("getRentHouseStasticsDetailPage", param);
	}

	private Map<String, Object> initQualificationParams(String userNo, String userName, String certificateNo, String qualification, String year, int page, int pageSize) {
		Map<String, Object> param = new HashMap<>();
		param.put("type", qualification);
		param.put("year", year);
		if (StringUtils.isNotEmpty(userNo))
			param.put("userNo", userNo);
		if (StringUtils.isNotEmpty(userName))
			param.put("userName", "%" + userName + "%");
		if (StringUtils.isNotEmpty(certificateNo))
			param.put("certificateNo", "%" + certificateNo + "%");
		if (page <= 0)
			page = 1;
		param.put("begNum", (page - 1) * pageSize);
		param.put("pageSize", pageSize);
		return param;
	}

	// 在册人次
	public int getQualificationPeopleDetailCount(String userNo, String userName, String certificateNo, String qualification, String year) {
		return (int) executiveDao.getOne("getQualificationPeopleDetailCount", initQualificationParams(userNo, userName, certificateNo, qualification, year, 0, 0));
	}

	// 初始注册人次
	public int getQualificationInitPeopleDetailCount(String userNo, String userName, String certificateNo, String qualification, String year) {
		return (int) executiveDao.getOne("getQualificationInitPeopleDetailCount", initQualificationParams(userNo, userName, certificateNo, qualification, year, 0, 0));
	}

	// 转入注册人次
	public int getQualificationIntoPeopleDetailCount(String userNo, String userName, String certificateNo, String qualification, String year) {
		return (int) executiveDao.getOne("getQualificationIntoPeopleDetailCount", initQualificationParams(userNo, userName, certificateNo, qualification, year, 0, 0));
	}

	// 转出注册人次
	public int getQualificationOuttoPeopleDetailCount(String userNo, String userName, String certificateNo, String qualification, String year) {
		return (int) executiveDao.getOne("getQualificationOuttoPeopleDetailCount", initQualificationParams(userNo, userName, certificateNo, qualification, year, 0, 0));
	}

	public List getQualificationPeopleDetailPage(String userNo, String userName, String certificateNo, String qualification, String year, int page, int pageSize) {
		return executiveDao.getBySqlKey("getQualificationPeopleDetailPage", initQualificationParams(userNo, userName, certificateNo, qualification, year, page, pageSize));
	}

	public List getQualificationInitPeopleDetailPage(String userNo, String userName, String certificateNo, String qualification, String year, int page, int pageSize) {
		return executiveDao.getBySqlKey("getQualificationInitPeopleDetailPage", initQualificationParams(userNo, userName, certificateNo, qualification, year, page, pageSize));
	}

	public List getQualificationIntoPeopleDetailPage(String userNo, String userName, String certificateNo, String qualification, String year, int page, int pageSize) {
		return executiveDao.getBySqlKey("getQualificationIntoPeopleDetailPage", initQualificationParams(userNo, userName, certificateNo, qualification, year, page, pageSize));
	}

	public List getQualificationOuttoPeopleDetailPage(String userNo, String userName, String certificateNo, String qualification, String year, int page, int pageSize) {
		return executiveDao.getBySqlKey("getQualificationOuttoPeopleDetailPage", initQualificationParams(userNo, userName, certificateNo, qualification, year, page, pageSize));
	}

	// 在册人次
	public int getQualificationPractitionersPeopleDetailCount(String userNo, String userName, String certificateNo, String qualification, String year) {
		return (int) executiveDao.getOne("getQualificationPractitionersPeopleDetailCount", initQualificationParams(userNo, userName, certificateNo, qualification, year, 0, 0));
	}

	// 转入注册人次
	public int getQualificationPractitionersIntoPeopleDetailCount(String userNo, String userName, String certificateNo, String qualification, String year) {
		return (int) executiveDao.getOne("getQualificationPractitionersIntoPeopleDetailCount", initQualificationParams(userNo, userName, certificateNo, qualification, year, 0, 0));
	}

	// 转出注册人次
	public int getQualificationPractitionersOuttoPeopleDetailCount(String userNo, String userName, String certificateNo, String qualification, String year) {
		return (int) executiveDao
				.getOne("getQualificationPractitionersOuttoPeopleDetailCount", initQualificationParams(userNo, userName, certificateNo, qualification, year, 0, 0));
	}

	public List getQualificationPractitionersPeopleDetailPage(String userNo, String userName, String certificateNo, String qualification, String year, Integer page,
			Integer pageSize) {
		return executiveDao.getBySqlKey("getQualificationPractitionersPeopleDetailPage",
				initQualificationParams(userNo, userName, certificateNo, qualification, year, page, pageSize));
	}

	public List getQualificationPractitionersIntoPeopleDetailPage(String userNo, String userName, String certificateNo, String qualification, String year, Integer page,
			Integer pageSize) {
		return executiveDao.getBySqlKey("getQualificationPractitionersIntoPeopleDetailPage",
				initQualificationParams(userNo, userName, certificateNo, qualification, year, page, pageSize));
	}

	public List getQualificationPractitionersOuttoPeopleDetailPage(String userNo, String userName, String certificateNo, String qualification, String year, Integer page,
			Integer pageSize) {
		return executiveDao.getBySqlKey("getQualificationPractitionersOuttoPeopleDetailPage",
				initQualificationParams(userNo, userName, certificateNo, qualification, year, page, pageSize));
	}

	// 上岗培训人次
	public int getQualificationPractitionersTrainingPeopleDetailCount(String userNo, String userName, String certificateNo, String qualification, String year) {
		return (int) executiveDao.getOne("getQualificationPractitionersTrainingPeopleDetailCount",
				initQualificationParams(userNo, userName, certificateNo, qualification, year, 0, 0));
	}

	public List getQualificationPractitionersTrainingPeopleDetailPage(String userNo, String userName, String certificateNo, String qualification, String year, Integer page,
			Integer pageSize) {
		return executiveDao.getBySqlKey("getQualificationPractitionersTrainingPeopleDetailPage",
				initQualificationParams(userNo, userName, certificateNo, qualification, year, page, pageSize));
	}

	/**
	 * 合同收款统计信息详情
	 * 
	 * @return
	 */
	public int getDetailContractBillingCount(String orgId, String contracttype, String contracttype1, String contract_num, String contract_name, String contract_status,
			String first_party, String project_department, String contract_handler, String singingStart, String singingEnd, String moneyMin, String moneyMax) {
		Map<String, Object> param = getDetailContractBillingQueryParams(orgId, contracttype, contracttype1, contract_num, contract_name, contract_status, first_party,
				project_department, contract_handler, singingStart, singingEnd, moneyMin, moneyMax);
		return (int) executiveDao.getOne("getDetailContractBillingCount", param);
	}

	public List getDetailContractBillingPage(String orgId, String contracttype, String contracttype1, String contract_num, String contract_name, String contract_status,
			String first_party, String project_department, String contract_handler, String singingStart, String singingEnd, String moneyMin, String moneyMax, int page, int pageSize) {
		Map<String, Object> param = getDetailContractBillingQueryParams(orgId, contracttype, contracttype1, contract_num, contract_name, contract_status, first_party,
				project_department, contract_handler, singingStart, singingEnd, moneyMin, moneyMax);
		if (page <= 0)
			page = 1;
		param.put("begNum", (page - 1) * pageSize);
		param.put("pageSize", pageSize);
		return executiveDao.getBySqlKey("getDetailContractBillingPage", param);
	}

	private Map<String, Object> getDetailContractBillingQueryParams(String orgId, String contracttype, String contracttype1, String contract_num, String contract_name,
			String contract_status, String first_party, String project_department, String contract_handler, String singingStart, String singingEnd, String moneyMin, String moneyMax) {
		Map<String, Object> param = new HashMap<>();
		if (StringUtils.isNotEmpty(contract_name))
			param.put("contract_name", "%" + contract_name + "%");
		if (StringUtils.isNotEmpty(contract_num))
			param.put("contract_num", "%" + contract_num + "%");
		param.put("contract_status", contract_status);
		if (StringUtils.isNotEmpty(first_party))
			param.put("first_party", "%" + first_party + "%");
		param.put("contracttype", contracttype);
		param.put("contracttype1", contracttype1);
		if (StringUtils.isNotEmpty(project_department))
			param.put("project_department", "%" + project_department + "%");
		if (StringUtils.isNotEmpty(contract_handler))
			param.put("contract_handler", "%" + contract_handler + "%");
		param.put("orgId", orgId);
		if (StringUtils.isNotEmpty(singingStart))
			param.put("singingStart", singingStart + " 00:00:00");
		if (StringUtils.isNotEmpty(singingEnd))
			param.put("singingEnd", singingEnd + " 00:00:00");
		param.put("moneyMin", moneyMin);
		param.put("moneyMax", moneyMax);
		return param;
	}

	private Map<String, Object> getAssetDetailQueryParams(String orgId, String assetCode, String assetId, String assetName, String carePerson) {
		Map<String, Object> param = new HashMap<>();
		if (StringUtils.isNotEmpty(orgId))
			param.put("orgid1", "%" + orgId + "%");
		param.put("assetCode", assetCode);
		param.put("assetId", assetId);
		if (StringUtils.isNotEmpty(assetName))
			param.put("assetName", "%" + assetName + "%");
		if (StringUtils.isNotEmpty(carePerson))
			param.put("carePerson", "%" + carePerson + "%");
		return param;
	}

	/**
	 * 资产信息统计详情
	 * 
	 * @param orgId
	 * @param assetCode
	 * @param assetId
	 * @param assetName
	 * @param carePerson
	 * @return
	 */
	public int getAssetDetailCount(String orgId, String assetCode, String assetId, String assetName, String carePerson) {
		Map<String, Object> param = getAssetDetailQueryParams(orgId, assetCode, assetId, assetName, carePerson);
		return (int) executiveDao.getOne("getAssetDetailCount", param);
	}

	public List getAssetDetailPage(String orgId, String assetCode, String assetId, String assetName, String carePerson, int page, int pageSize) {
		Map<String, Object> param = getAssetDetailQueryParams(orgId, assetCode, assetId, assetName, carePerson);
		if (page <= 0)
			page = 1;
		param.put("begNum", (page - 1) * pageSize);
		param.put("pageSize", pageSize);
		return executiveDao.getBySqlKey("getAssetDetailPage", param);
	}

	private Map<String, Object> getAssetDetailQueryParams(String type) {
		Map<String, Object> param = new HashMap<>();
		param.put("type", type);
		return param;
	}

	public int getQualificationsDetailCount(String type) {
		Map<String, Object> param = getAssetDetailQueryParams(type);
		return (int) executiveDao.getOne("getQualificationsDetailCount", param);
	}

	public List getQualificationsDetailPage(String type, Integer page, Integer pageSize) {
		Map<String, Object> param = getAssetDetailQueryParams(type);
		if (page <= 0)
			page = 1;
		param.put("begNum", (page - 1) * pageSize);
		param.put("pageSize", pageSize);
		return executiveDao.getBySqlKey("getQualificationsDetailPage", param);
	}
}
