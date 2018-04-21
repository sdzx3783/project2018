package com.hotent.makshi.controller.portal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.DateUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.model.doc.Doc;
import com.hotent.makshi.model.doc.DocFile;
import com.hotent.makshi.service.doc.DocFileService;
import com.hotent.makshi.service.doc.DocService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.PositionService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserService;

@Controller
@RequestMapping("/makshi/portal/compportal/")
public class CompanyPortalController  extends BaseController {
	@Resource
	private DocService docService;
	@Resource
	private DocFileService docFileService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private PositionService positionService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysRoleService sysRoleService;
	@RequestMapping("index")
	@Action(description = "公司门户")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv=getAutoView();
		Map<String, Object> params=new HashMap<>();
		
		int pageSize=5; //默认取8条数据
		Doc temp=null;
		List<Doc> templist=null;
		List<DocFile> tzgg_list=null; //通知公告列表
		List<DocFile> gszd_list=null; //公司制度列表
		List<DocFile> pic_list=null; //公司门户图片列表
		
		temp=docService.getCommonDocByName("公司通知");
		if(temp!=null){
			tzgg_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("tzggId", temp.getDocid());
		}
		temp=null;
		templist=null;
		params.clear();
		
		temp=docService.getCommonDocByName("公司制度");
		if(temp!=null){
			gszd_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("gszdId", temp.getDocid());
		}
		//最新文档
		/*params.clear();
		params.put("isdelete", 0);
		params.put("status", 1);
		params.put("orderField", "wf.`CREATETIME`");
		params.put("orderSeq", "desc");
		List<DocFile> tempdocFileList = docFileService.getDocFileList(params, -1,-1);//不分页
		List<DocFile> docFileList=new ArrayList<>();
		//判断最新文档权限
		Long currentUserId = ContextUtil.getCurrentUserId();
		//Long currentOrgId = ContextUtil.getCurrentOrgId()==0l?-1l:ContextUtil.getCurrentOrgId();//主岗位组织id
		List<SysOrg> userorgs = getOrgIdByUserId();
		if(currentUserId==1){
			docFileList=tempdocFileList;
		}else{
			for (DocFile docFile : tempdocFileList) {
				Doc byId = docService.getById(docFile.getDocid());
				if(isOrgDocAdmin(byId.getOrgid())){
					docFileList.add(docFile);
				}else{
					boolean isRead = docFileService.isReadFile(docFile.getDfid(), currentUserId, userorgs);
					if(isRead){
						docFileList.add(docFile);
					}
				}
			}
		}*/
		//新人入职
		QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
		Map<String, Object> filters = queryFilter.getFilters();
		//PageBean pageBean = queryFilter.getPageBean();
		//pageBean.setCurrentPage(0);
		//pageBean.setPagesize(pageSize);
		queryFilter.setPageBean(null);
		//filters.put("orderField", "birthday");
		//filters.put("orderSeq", "asc");
		List<SysUser> userlist = sysUserService.getCurrentBirthUsers(queryFilter);
		for (SysUser user :userlist) {
			Date birthday = user.getBirthday();
			Calendar cal=Calendar.getInstance();
			int curyear = cal.get(Calendar.YEAR);
			cal.setTime(birthday);
			int birthdayYear = cal.get(Calendar.YEAR);
			cal.set(Calendar.YEAR, curyear);
			int diffDaysByDay = DateUtils.getDiffDaysByDay(new Date(),cal.getTime());
			user.setIntervalDay(diffDaysByDay);
			user.setBirthAge(curyear-birthdayYear);
			SysOrg org = sysOrgService.getPrimaryOrgByUserId(user.getUserId());
			Position position = positionService.getById(user.getPosId1());
			//Job job = jobService.getById(user.getJobId());
			if(org!=null && org.getOrgName()!=null){
				String orgpath= org.getOrgPathname();
				user.setOrgName(orgpath.replaceAll("/深水咨询/", ""));
			}
			if(position!=null && position.getPosName()!=null){
				user.setJobName(position.getPosName());
			}
		}
		//获取公司门户图片信息
		params.clear();
		temp=null;
		templist=null;
		List<String> imgSrc=new ArrayList<>();
		/*params.put("docname", "公司门户图片");
		params.put("docsupid",0);
		params.put("isdelete", 0);
		params.put("orgid",0);
		params.put("doctype", 1);
		templist = docService.getDocByParam(params);
		if(templist!=null && templist.size()>0){
			temp = templist.get(0);
		}*/
		temp = docService.getCommonDocByName("门户图片");
		if(temp!=null){
			pic_list = docFileService.getByDocId(temp.getDocid(),-1,-1);
			for (DocFile docFile : pic_list) {
				if(docFile.getStatus()==1){ //正常状态
					GetHtmlImageSrcList(imgSrc,docFile.getContent());
				}
			}
			
		}
		
		mv.addObject("tzgg_list", tzgg_list);
		mv.addObject("gszd_list", gszd_list);
		//mv.addObject("docFileList", docFileList);
		mv.addObject("userlist", userlist);
		mv.addObject("imgSrcList", imgSrc);
		
		return mv;
	}
	
	private  List<String> GetHtmlImageSrcList(List<String> imgSrc,String htmlText)   
    {
        if(StringUtils.isBlank(htmlText)){
        	return imgSrc;
        }
        Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(htmlText);
        while(m.find())
        {    
            imgSrc.add(m.group(1));
        }
        return imgSrc;
    }
	
	
	/**
	 * 判断当前用户是否为指定部门的文档管理员或者超级管理员
	 * @return
	 */
	public boolean isOrgDocAdmin(Long orgId){
		Long currentUserId = ContextUtil.getCurrentUserId();
		if(currentUserId==1l){//超级管理员
			return true;
		}
		List<SysRole> byUserId = sysRoleService.getByUserId(currentUserId);
		if(byUserId==null || byUserId.size()==0){
			return false;
		}
		if(orgId==0){//深水咨询
			for (SysRole sysRole : byUserId) {
				if(("深水咨询-文档管理员").equals(sysRole.getRoleName())){
					return true;
				}
			}
			return false;
		}
		SysOrg byId = sysOrgService.getById(orgId);
		if(byId==null){
			return false;
		}else{
			String orgName = byId.getOrgName();
			boolean flag=false;
			for (SysRole sysRole : byUserId) {
				if((orgName+"-文档管理员").equals(sysRole.getRoleName())){
					flag=true;
					break;
				}
			}
			return flag;
		}
	}

	public List<SysOrg> getOrgIdByUserId(){
		Long currentUserId = ContextUtil.getCurrentUserId();
		List<SysOrg> orgsByUserId = sysOrgService.getOrgsByUserId(currentUserId);
		return orgsByUserId;
	}
}
