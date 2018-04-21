/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.hr.PoliticalIn;
import com.hotent.makshi.service.hr.PoliticalInService;

/**
 * @author cesc
 *党员转入关联
 */
@Component("politicalInFetcher")
public class PoliticalInFetcher implements IFetcher {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private PoliticalInService politicalInService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		if(!StringUtil.isEmpty(businessKey)){
			PoliticalIn politicalIn = politicalInService.getById(Long.parseLong(businessKey));
			if(politicalIn!=null){
				String picPath = politicalIn.getPic();
				if(null!=picPath&&!("").equals(picPath)){
					String path = "/platform/system/sysFile/getFileById.ht?fileId="+picPath.substring(picPath.indexOf("id")+5,picPath.indexOf("name")-3);
					politicalIn.setPic(path);
				}
				politicalIn.setIn_date(new Date());
				politicalIn.setIs_in("1");
				}
				try {
					politicalInService.update(politicalIn);
				} catch (Exception e) {
					log.error("错误信息",e);
				}
			}
		}
	}

