/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.contract.ContractChangeApplication;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.service.contract.ContractChangeApplicationService;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.system.SysOrgService;

/**
 * @author cesc
 *合同信息变更
 */
@Component("contractChangeFetcher")
public class ContractChangeFetcher implements IFetcher {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private ContractChangeApplicationService changeApplicationService;
	@Resource
	private ContractinfoService infoService;
	@Resource
	private SysOrgService sysorgService;
	@SuppressWarnings("unused")
	@Override
	public void fetcheData(String tableName, String businessKey) {
		try {
		if(!StringUtil.isEmpty(businessKey)){
			ContractChangeApplication contractChangeApplication = changeApplicationService.getById(Long.parseLong(businessKey));
			if(contractChangeApplication!=null){
				Contractinfo contractinfo = infoService.getContractinfoByNum(contractChangeApplication.getContract_num());
				if(null!=contractinfo){
				String type = contractChangeApplication.getChange_content();
				if(null!=type&&!("").equals(type)){
					if(("1").equals(type)){
						contractinfo.setContract_money(Double.valueOf(contractChangeApplication.getChange_after()));
					}
					if(("2").equals(type)){
						contractinfo.setTotal_investment(Double.valueOf(contractChangeApplication.getChange_after()));
					}
					if(("3").equals(type)){
						contractinfo.setContract_handler(contractChangeApplication.getChange_after());
					}
					if(("4").equals(type)){
						contractinfo.setProject_director(contractChangeApplication.getChange_after());
					}
					if(("5").equals(type)){
						contractinfo.setProject_leader(contractChangeApplication.getChange_after());
					}
					if(("6").equals(type)){
						//获取变更后的部门名称
						String changeDepartment= contractChangeApplication.getChange_after();
						String departmentId = null;
						//通过部门名称获取部门id值
						List<SysOrg> sysOrgList = sysorgService.getByOrgName(changeDepartment);
						if(sysOrgList.size()>0){
							departmentId = sysOrgList.get(0).getOrgId().toString();
						}
						contractinfo.setContract_department(changeDepartment);
						contractinfo.setContract_departmentID(departmentId);
					}
					if(("7").equals(type)){
						contractinfo.setFirst_party(contractChangeApplication.getChange_after());
					}
					if(("8").equals(type)){
						contractinfo.setProject_place(contractChangeApplication.getChange_after());
					}
					
				}
					infoService.update(contractinfo);
				}
			}
			
		}
		} catch (Exception e) {
			log.error("错误信息",e);
		}
	}
}
