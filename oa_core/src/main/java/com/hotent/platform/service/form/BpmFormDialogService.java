package com.hotent.platform.service.form;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.db.datasource.DbContextHolder;
import com.hotent.core.db.datasource.JdbcTemplateUtil;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.page.PageList;
import com.hotent.core.service.BaseService;
import com.hotent.core.table.BaseTableMeta;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.IDbView;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.impl.TableMetaFactory;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.XmlBeanUtil;
import com.hotent.platform.dao.form.BpmFormDialogDao;
import com.hotent.platform.model.form.BpmFormDialog;
import com.hotent.platform.model.form.DialogField;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.util.ServiceUtil;
import com.hotent.platform.xml.form.BpmFormDialogXml;
import com.hotent.platform.xml.form.BpmFormDialogXmlList;
import com.hotent.platform.xml.util.MsgUtil;
import com.hotent.platform.xml.util.XmlUtil;

import net.sf.json.JSONObject;

/**
 * 对象功能:通用表单对话框 Service类 开发公司:广州宏天软件有限公司 开发人员:ray 创建时间:2012-06-25 11:05:09
 */
@Service
public class BpmFormDialogService extends BaseService<BpmFormDialog> {
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private BpmFormDialogDao dao;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysRoleService sysRoleService;
 	@Resource
	private GroovyScriptEngine groovyScriptEngine;
	@Resource
	private BpmFormDialogCombinateService bpmFormDialogCombinateService;

	public BpmFormDialogService() {
	}

	@Override
	protected IEntityDao<BpmFormDialog, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 检查模板别名是否唯一
	 * 
	 * @param alias
	 * @return
	 */
	public boolean isExistAlias(String alias) {
		return dao.isExistAlias(alias) > 0;
	}

	/**
	 * 检查模板别名是否唯一。
	 * 
	 * @param alias
	 * @return
	 */
	public boolean isExistAliasForUpd(Long id, String alias) {
		return dao.isExistAliasForUpd(id, alias) > 0;
	}

	/**
	 * 根据别名获取对话框对象。
	 * 
	 * @param alias
	 * @return
	 */
	public BpmFormDialog getByAlias(String alias) {
		return dao.getByAlias(alias);
	}

	/**
	 * 返回树型结构的数据。
	 * 
	 * @param alias
	 * @return
	 * @throws Exception
	 */
	public List getTreeData(String alias) throws Exception {
		BpmFormDialog bpmFormDialog = dao.getByAlias(alias);
		DbContextHolder.setDataSource(bpmFormDialog.getDsalias());
		String sql = getTreeSql(bpmFormDialog);
		List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(jdbcTemplate).queryForList(sql, new HashMap());

		return list;
	}

	/**
	 * 返回树型结构的数据。
	 * 
	 * @param alias
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getTreeData(String alias, Map<String, Object> params, boolean isRoot) throws Exception {
		BpmFormDialog bpmFormDialog = dao.getByAlias(alias);
		/*DbContextHolder.setDataSource(bpmFormDialog.getDsalias());*/
		JdbcTemplate template = JdbcTemplateUtil.getNewJdbcTemplate(bpmFormDialog.getDsalias());
		String sql = null;
		sql = getTreeSql(bpmFormDialog, params, isRoot);
		//System.out.println("sql语句-------------->：" + sql);
		List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(template).queryForList(sql, params);
		return list;
	}

	/**
	 * {"id":"id","pid":"fatherId","displayName":"name"}
	 * 
	 * @param displayField
	 * @param objName
	 * @return
	 * @throws Exception
	 */
	private String getTreeSql(BpmFormDialog bpmFormDialog, Map<String, Object> nodeMap, boolean isRoot) throws Exception {
		String objName = bpmFormDialog.getObjname();
		List<DialogField> conditionList = bpmFormDialog.getConditionList();
		Map<String, Object> params = new HashMap<String, Object>();
		if (BeanUtils.isNotEmpty(nodeMap)) {
			params = nodeMap;
		}
		String pname = (String) nodeMap.get("pname");
		String pvalue = (String) nodeMap.get("pvalue");
		String displayField = bpmFormDialog.getDisplayfield();
		JSONObject jsonObj = JSONObject.fromObject(displayField);
		String id = jsonObj.getString("id");
		String pid = jsonObj.getString("pid");
		if (StringUtil.isNotEmpty(pname) && !isRoot) {
			DialogField pfield = getDailogField(bpmFormDialog, pname);
			if (pfield != null) {
				pfield.setCondition("=");
				conditionList.add(pfield);
				params.put(pname, pvalue);
			}
		}
		// 获取条件的SQL语句
		String sqlWhere = ServiceUtil.getWhere(conditionList, params);
		if (!isRoot) {
			if (sqlWhere.indexOf(pid) < 0 && StringUtil.isNotEmpty(pvalue)) {
				// 若父节点ID的值不为空，则添加查找条件
				if (StringUtil.isEmpty(sqlWhere))
					sqlWhere += " where " + pid + "=" + pvalue;
				else
					sqlWhere += " and " + pid + "=" + pvalue;
			}
		} else {
			// 父节点ID的值，可为空字符串。在设置树形对话框时可对其进行赋值，以获取指定父节点的树
			pvalue = jsonObj.getString("pvalue");
			String isScript = jsonObj.getString("isScript");

			if (StringUtil.isNotEmpty(pvalue) && !"1".equals(pvalue)) {
				if ("true".equals(isScript)) {
					// 父节点ID的值为脚本表达式
					pvalue = groovyScriptEngine.executeObject(pvalue, null).toString();
				}
				// 若父节点ID的值不为空，则添加查找条件
				if (StringUtil.isNotEmpty(sqlWhere)) {
					if (sqlWhere.indexOf(id) < 0)
						sqlWhere += " AND " + id + "=" + pvalue;
				} else {
					sqlWhere += " WHERE " + id + "=" + pvalue;
				}
			}
			if (StringUtil.isEmpty(sqlWhere) && "1".equals(pvalue)) {
				sqlWhere += " WHERE " + pid + "=" + pvalue;
			}
		}
		String sqlSelect = getSelectSQl(bpmFormDialog);
		String isParent = ", ( case (select count(*)  from " + objName + " p where p." + pid + "=o." + id + " and p." + id + "!=p." + pid + ") when 0 then 'false' else 'true' end )isParent ";
		sqlSelect += isParent;
		List<DialogField> sortList = bpmFormDialog.getSortList();
		String orderBy = "";
		for (int i = 0; i < sortList.size(); i++) {
			DialogField df = sortList.get(i);
			if (i == 0) {
				orderBy = " order by ";
			}
			orderBy += df.getFieldName() + " " + df.getComment();
			if (i != sortList.size() - 1) {
				orderBy += ",";
			} else {
				sqlWhere += orderBy;
			}
		}
		String sql = "SELECT " + sqlSelect + " FROM " + objName + " o " + sqlWhere;
		return sql;
	}

	private DialogField getDailogField(BpmFormDialog bpmFormDialog, String fieldname) throws Exception {
		TableModel tableModel;
		int istable = bpmFormDialog.getIstable();
		String dsName = bpmFormDialog.getDsalias();
		String objectName = bpmFormDialog.getObjname();
		// 表
		if (istable == 1) {
			BaseTableMeta meta = TableMetaFactory.getMetaData(dsName);
			tableModel = meta.getTableByName(objectName);
		}
		// 视图处理
		else {
			IDbView dbView = TableMetaFactory.getDbView(dsName);
			tableModel = dbView.getModelByViewName(objectName);
		}
		List<ColumnModel> columns = tableModel.getColumnList();
		for (ColumnModel column : columns) {
			if (column.getName().equalsIgnoreCase(fieldname)) {
				DialogField field = new DialogField();
				field.setComment(column.getComment());
				field.setFieldName(column.getName());
				field.setFieldType(column.getColumnType());
				return field;
			}
		}
		return null;
	}

	/**
	 * 根据别名获取对应对话框的数据。
	 * 
	 * @param alias
	 *            对话框别名。
	 * @param params
	 *            参数集合。
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public BpmFormDialog getData(BpmFormDialog bpmFormDialog, Map<String, Object> params) throws Exception {
		
		List<DialogField> displayList = bpmFormDialog.getDisplayList();
		List<DialogField> conditionList = bpmFormDialog.getConditionList();
		String objectName = bpmFormDialog.getObjname();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectName", objectName);
		map.put("displayList", displayList);
		map.put("conditionList", conditionList);
		map.put("sortList", bpmFormDialog.getSortList());
		// 是否是列表
		if (bpmFormDialog.getStyle() == 0) {
			// 是否需要分页。
			if (bpmFormDialog.getNeedpage() == 1) {
				int currentPage = 1;
				Object pageObj = params.get(BpmFormDialog.Page);
				if (pageObj != null) {
					currentPage = Integer.parseInt(params.get(BpmFormDialog.Page).toString());
				}
				int pageSize = bpmFormDialog.getPagesize();
				Object pageSizeObj = params.get(BpmFormDialog.PageSize);
				if (pageSizeObj != null) {
					pageSize = Integer.parseInt(params.get(BpmFormDialog.PageSize).toString());
				}
				String sql = ServiceUtil.getSql(map, params);
				//合同编号相关处理
			/*	if("contractinfo".equals(bpmFormDialog.getAlias())){
					sql = changeCondition(params, sql);
				}
				if("contractPaymentApplication".equals(bpmFormDialog.getAlias())){
					sql = changeCondition(params, sql);
				}*/
				//我的合同自定义对话框特殊处理
				if("wdhtxx".equals(bpmFormDialog.getAlias())){
					//判断是否拥有查看所有合同权限
					if(!isEditor()){
						//sql = changeCondition(params, sql);
						Long currentUserId = ContextUtil.getCurrentUserId();
						String orgIdsByUserId = "-1";
						SysOrg primaryOrgByUserId = sysOrgService.getPrimaryOrgByUserId(currentUserId);
						if(primaryOrgByUserId!=null){
							String path = primaryOrgByUserId.getPath();
							if (StringUtils.isNotEmpty(path)) {
								String[] split = path.split("\\.");
								if (split.length >= 3) {
									orgIdsByUserId=split[2].trim();// 获取二级部门id
								}
							}
						}
						if(!ContextUtil.isSuperAdmin()){
							String paramSql="AND EXISTS (SELECT * FROM sys_org org WHERE org.orgid=F_contract_departmentID and org.path LIKE "
									+"'%"+orgIdsByUserId+"%'"
									+")";
							sql=sql.replace("order", paramSql+" order ");
						}
					}
					
				}
				/*if("wdxm".equals(bpmFormDialog.getAlias())){
					Long currentUserId = ContextUtil.getCurrentUserId();
					if(!ContextUtil.isSuperAdmin()){
						String paramSql="and a.id in ( "
								+" SELECT DISTINCT wp.id FROM w_project wp INNER JOIN w_classify_library wl ON wp.classifyLibraryId = wl.id AND wp.isDelete = 0 "
								+" INNER JOIN w_project_stage ws ON wp.id = ws.proId "
								+" INNER JOIN w_project_stage_task wt ON ws.id = wt.proStageId AND wt.isDelete = 0"
								+" WHERE 1 = 1 AND ( "
								+" FIND_IN_SET("+currentUserId+",wp.applicantID)"
								+" OR FIND_IN_SET("+currentUserId+",wp.memberID)"
								+" OR FIND_IN_SET("+currentUserId+",wt.chargeID)"
								+" ) )";
						if(sql.indexOf("where")<0){
							paramSql=" where 1=1 "+paramSql;
						}
						sql=sql.replace("order", paramSql+" order ");
					}
				}*/
				List list = JdbcTemplateUtil.getPage(bpmFormDialog.getDsalias(), currentPage, pageSize, sql, params);
				
/*				for(Object obj:list){
					Map<String,Object> m = (Map<String, Object>) obj;
					for(String key:m.keySet()){
						m.put(key, m.get(key).toString().replace("\"", "'"));
					}
				}
*/				
				bpmFormDialog.setList(list);
				bpmFormDialog.setPageBean(((PageList)list).getPageBean() );
			} else {
				String sql = ServiceUtil.getSql(map, params);
				List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
				bpmFormDialog.setList(list);
			}
		}

		return bpmFormDialog;

	}
	private boolean isEditor() {
		String alias = "cw_contract";
		Long userId = ContextUtil.getCurrentUserId();
		if(userId==1L){
			return true;
		}
		List<SysRole> roleList = sysRoleService.getByUserId(userId);
		for (SysRole role : roleList) {
			if (role.getAlias().equals(alias)) {
				return true;
			}
		}
		return false;
	}
/*	public String changeCondition(Map<String, Object> params, String sql) {
		String contractNum = (String) params.get("F_contract_num");
		if(StringUtil.isNotEmpty(contractNum)){
			//查找字段出现位置
			int numIndex = sql.indexOf("F_contract_num");
			//查找合同编号所在位置
			int index = sql.indexOf(contractNum);
			//查找最后的位置
			int firstIndex = sql.indexOf("%", index);
			//将sql拆为三段,替换中间一段
			String partOne = sql.substring(0, numIndex);
			String partTwo = sql.substring(firstIndex+2, sql.length());
			String F_contract_num = "(F_num_his like '%"+contractNum+"%' or F_contract_num like '%"+contractNum+"%')";
			sql = partOne + F_contract_num + partTwo;
		}
		return sql;
	}*/

	/**
	 * {"id":"id","pid":"fatherId","displayName":"name"}
	 * 
	 * @param displayField
	 * @param objName
	 * @return
	 */
	private String getTreeSql(BpmFormDialog bpmFormDialog) {

		String objName = bpmFormDialog.getObjname();
		List<DialogField> conditionList = bpmFormDialog.getConditionList();
		Map<String, Object> params = new HashMap<String, Object>();
		// 获取条件的SQL语句
		String sqlWhere = ServiceUtil.getWhere(conditionList, params);

		String sqlSelect = getSelectSQl(bpmFormDialog);
		String sql = "SELECT " + sqlSelect + " FROM " + objName + sqlWhere;

		return sql;
	}

	/**
	 * 从DisplayField和ReturnField中取得Select字段，用于拼接SQL语句
	 * 
	 * @param bpmFormDialog
	 * @return
	 */
	private String getSelectSQl(BpmFormDialog bpmFormDialog) {
		String displayField = bpmFormDialog.getDisplayfield();
		JSONObject jsonObj = JSONObject.fromObject(displayField);
		String id = jsonObj.getString("id");
		String pid = jsonObj.getString("pid");
		String displayName = jsonObj.getString("displayName");
		List<DialogField> returnFields = bpmFormDialog.getReturnList();
		String sqlSelect = id + "," + pid + "," + displayName;
		for (DialogField field : returnFields) {
			String name = field.getFieldName();
			if (name.equalsIgnoreCase(id) || name.equalsIgnoreCase(pid) || name.equalsIgnoreCase(displayName)) {
				continue;
			}
			sqlSelect += "," + name;
		}
		return sqlSelect;
	}
	/**
	 * 导出全部
	 * @param all
	 * @return
	 * @throws Exception
	 */
	public String exportXml(List<BpmFormDialog> bpmFormDialogs)throws Exception
	{
		BpmFormDialogXmlList bpmFormDialogXmlList = new BpmFormDialogXmlList();
		List<BpmFormDialogXml> list = new ArrayList<BpmFormDialogXml>();
		for(BpmFormDialog bpmFormDialog:bpmFormDialogs)
		{
			BpmFormDialogXml bpmFormDialogXml=this.exportBpmFormDialogXml(bpmFormDialog);
			list.add(bpmFormDialogXml);
		}
		bpmFormDialogXmlList.setBpmFormDialogXmlList(list);
		return XmlBeanUtil.marshall(bpmFormDialogXmlList, BpmFormDialogXmlList.class);
	}
	/**
	 * 导出自定义对话框XML
	 * @param tableIds
	 * @return
	 * @throws Exception
	 */
	public String exportXml(Long[] tableIds) throws Exception {
		BpmFormDialogXmlList bpmFormDialogXmlList = new BpmFormDialogXmlList();
		List<BpmFormDialogXml> list = new ArrayList<BpmFormDialogXml>();
		for (int i = 0; i < tableIds.length; i++) {
			BpmFormDialog bpmFormDialog = dao.getById(tableIds[i]);
			BpmFormDialogXml bpmFormDialogXml =this.exportBpmFormDialogXml(bpmFormDialog);
			list.add(bpmFormDialogXml);
		}
		bpmFormDialogXmlList.setBpmFormDialogXmlList(list);
		return XmlBeanUtil.marshall(bpmFormDialogXmlList, BpmFormDialogXmlList.class);
	}
	/**
	 * 导出表的信息
	 * @param bpmFormDialog
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BpmFormDialogXml exportBpmFormDialogXml(
			BpmFormDialog bpmFormDialog)throws Exception
	
	{
		//map = XmlUtil.getTableDefaultExportMap(map);
		BpmFormDialogXml bpmFormDialogXml=new BpmFormDialogXml();
		Long id=bpmFormDialog.getId();
		if(BeanUtils.isNotIncZeroEmpty(id)){
			//导出对话框
			bpmFormDialogXml.setBpmFormDialog(bpmFormDialog);
		}
		return bpmFormDialogXml;
	}
	
	/**
	 * 导入自定义对话框XML
	 * @param inputStream
	 * @throws Exception
	 */
	public void importXml(InputStream inputStream) throws Exception {
		Document doc = Dom4jUtil.loadXml(inputStream);
		Element root = doc.getRootElement();
		// 验证格式是否正确
		XmlUtil.checkXmlFormat(root, "bpm", "formDialogs");

		String xmlStr = root.asXML();
		BpmFormDialogXmlList bpmFormDialogXmlList = (BpmFormDialogXmlList) XmlBeanUtil
				.unmarshall(xmlStr, BpmFormDialogXmlList.class);

		List<BpmFormDialogXml> list = bpmFormDialogXmlList
				.getBpmFormDialogXmlList();

		for (BpmFormDialogXml bpmFormDialogXml : list) {
			// 导入表，并解析相关信息
			this.importBpmFormDialogXml(bpmFormDialogXml);

		}
		

	}
	/**
	 * 导入时生成自定义对话框
	 * @param bpmFormDialogXml
	 * @return 
	 * @throws Exception
	 */
	private void importBpmFormDialogXml(BpmFormDialogXml bpmFormDialogXml)throws Exception 
	{
		Long dialogId = UniqueIdUtil.genId();
		BpmFormDialog bpmFormDialog = bpmFormDialogXml.getBpmFormDialog();
		if (BeanUtils.isEmpty(bpmFormDialog)) {
			throw new Exception();
		}
		String alias = bpmFormDialog.getAlias();
		BpmFormDialog dialog = dao.getByAlias(alias);
		if (BeanUtils.isNotEmpty(dialog)) {
			MsgUtil.addMsg(MsgUtil.WARN, "别名为" + alias
					+ "的自定义对话框已存在，请检查你的xml文件！");
			return;
		}
		bpmFormDialog.setId(dialogId);
		dao.add(bpmFormDialog);
		MsgUtil.addMsg(MsgUtil.SUCCESS, "别名为" + alias + "的自定义对话框导入成功！");
	}
	
}
