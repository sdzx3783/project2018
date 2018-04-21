package com.hotent.makshi.service.fetcher;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.renhouse.RentHouse;
import com.hotent.makshi.model.renthouseregistration.RentHouseRegistration;
import com.hotent.makshi.service.renhouse.RentHouseService;
import com.hotent.makshi.service.renthouseregistration.RentHouseRegistrationService;

@Component("renHouseFetcher")
public class RenHouseFetcher implements IFetcher {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private RentHouseService rentHouseService;
	@Resource
	private RentHouseRegistrationService rentHouseRegistrationService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		try {
			if(!StringUtil.isEmpty(businessKey)){
				RentHouse rentHouse= rentHouseService.getById(Long.parseLong(businessKey));
				RentHouseRegistration rentHouseRegistration = new RentHouseRegistration();
				rentHouseRegistration.setAddress(rentHouse.getAddress());
				rentHouseRegistration.setDepartmentID(rentHouse.getDepartmentID());
				rentHouseRegistration.setDepartment(rentHouse.getDepartment());
				rentHouseRegistration.setLandlord(rentHouse.getLandlord());
				rentHouseRegistration.setMoney(rentHouse.getMoney());
				rentHouseRegistration.setNumber_people(rentHouse.getNumber_people());
				rentHouseRegistration.setRent_person(rentHouse.getRent_person());
				rentHouseRegistration.setRent_type(rentHouse.getUse_type());
				rentHouseRegistration.setResponsible_person(rentHouse.getResponsible_person());
				rentHouseRegistration.setSize(rentHouse.getSize());
				rentHouseRegistration.setStart_date(rentHouse.getStart_date());
				rentHouseRegistration.setHouse_type(rentHouse.getStructure());
				rentHouseRegistration.setAttachment(rentHouse.getRent_house_attachment());
				rentHouseRegistration.setEnd_date(rentHouse.getRent_end_date());
				rentHouseRegistration.setDeposit(rentHouse.getDeposit());
				rentHouseRegistration.setHandle_person(rentHouse.getHandle_person());
				rentHouseRegistrationService.save(rentHouseRegistration);
			}
			
			} catch (Exception e) {
				log.error("错误信息",e);
			}
		}
		
	}

