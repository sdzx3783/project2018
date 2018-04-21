package com.hotent.platform.webservice.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/*@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.WRAPPED)
@WebService(endpointInterface = "com.hotent.platform.webservice.api.UserLoginService", targetNamespace = "http://impl.webservice.platform.hotent.com/")
*/
@Path("/UserLogin")
public interface UserLoginService {
	/**
	 * 用户登录
	 * @param json
	 * @return
	 */
	@Path("/login")
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String userLogin(@QueryParam("account") String account);
}
