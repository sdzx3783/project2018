package com.hotent.makshi.controller.expense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.controller.BaseController;
import com.hotent.makshi.model.expense.ExpenseReimbursement;
import com.hotent.makshi.model.expense.ExpenseReimbursementDepart;
import com.hotent.makshi.model.expense.ExpenseReimbursementDepartUser;
import com.hotent.makshi.model.expense.ExpenseReimbursementOrg;
import com.hotent.makshi.model.expense.ExpenseReimbursementTypes;
import com.hotent.makshi.model.gates.ExecutiveOrg;
import com.hotent.makshi.service.expense.ExpenseReimbursementService;
import com.hotent.makshi.service.gates.ExecutiveService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.service.system.SysOrgService;

@Controller
@RequestMapping("/makshi/expense/reimbursement/")
@SuppressWarnings("unchecked")
public class ExpenseReimbursementController extends BaseController {

	@Autowired
	private ExpenseReimbursementService expenseReimbursementService;
	@Resource
	private ExecutiveService executiveService;
	@Resource
	private SysOrgService sysOrgService;

	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required = false, value = "start") String start, @RequestParam(required = false, value = "end") String end, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(start) && StringUtils.isEmpty(end)) {
			start = DateUtils.getDefinedMonth(-5, "yyyy-MM");
			end = DateUtils.getDefinedYear(0, "yyyy-MM");
		}
		ModelAndView model = new ModelAndView("makshi/expense/reimbursement/reimbursementList.jsp").addObject("start", start).addObject("end", end);

		// 获取深水咨询和他底下的全部部门信息 start
		ExecutiveOrg shenshuiOrg = (ExecutiveOrg) executiveService.getOrgsBySplitLevel(2).get(0);
		ExpenseReimbursementOrg shenshuiErOrg = new ExpenseReimbursementOrg();
		shenshuiErOrg.setOrgId(shenshuiOrg.getOrgId());
		shenshuiErOrg.setOrgName(shenshuiOrg.getOrgName());
		List<ExpenseReimbursementOrg> allOrgs = new ArrayList<>();
		allOrgs.add(shenshuiErOrg);
		List<ExpenseReimbursementOrg> orgts = (List<ExpenseReimbursementOrg>) expenseReimbursementService.getOrgIds(start, end);
		if (CollectionUtils.isNotEmpty(orgts))
			allOrgs.addAll(orgts);
		// 获取深水咨询和他底下的全部部门信息 end
		// 将日期组装成一个集合，这样不管该日期有无数据都显示 start
		Map<String, Double> dateTotalValuesmaps = new HashMap<String, Double>();
		Set<String> dates = new TreeSet<String>();
		dates.add(start);
		dateTotalValuesmaps.put(start, 0D);
		String tempStart = start;
		while (true) {
			String temp = DateUtils.format(DateUtils.addMonth(DateUtils.toDateFromStr(tempStart + "-01 00:00:00"), 1), "yyyy-MM");
			if (temp.equals(end) || temp.compareToIgnoreCase(end) > 0)
				break;
			tempStart = temp;
			dates.add(temp);
			dateTotalValuesmaps.put(temp, 0D);
		}
		dates.add(end);
		dateTotalValuesmaps.put(end, 0D);
		// 将日期组装成一个集合，这样不管该日期有无数据都显示 end

		// 根据部门划分该部门的费用类型、只显示已有报销费用的类型，其他无的不显示
		Map<String, List<ExpenseReimbursementTypes>> typesmaps = new HashMap<String, List<ExpenseReimbursementTypes>>();
		double total = 0;// 计算总的报销费用
		Map<String, String> valuesmaps = new HashMap<String, String>();// 将以map的形式填充对应的坑，方便取值
		// 上面统计所有的部门，因为有一些历史数据，查询出来的部门，对应的报销类型可能没有值，但是用sql排除，浪费性能，所以直接在types的后面判断，有加入，无就不加入
		List<ExpenseReimbursementOrg> orgsOut = new ArrayList<>();
		for (ExpenseReimbursementOrg o : allOrgs) {
			// 1、表示该部门下面的所有的子部门信息的集合，2表示只统计该部门，因为深水咨询这个部门特殊
			int type = 1;
			if (o.getOrgId().equalsIgnoreCase(shenshuiErOrg.getOrgId())) {
				type = 2;
			}
			// 获取该部门下面的费用报销类型
			List<ExpenseReimbursementTypes> types = expenseReimbursementService.getTypes(o.getOrgId(), start, end, type);
			if (CollectionUtils.isEmpty(types)) {
				continue;
			}
			// 上面统计所有的部门，因为有一些历史数据，查询出来的部门，对应的报销类型可能没有值，但是用sql排除，浪费性能，所以直接在types的后面判断，有加入，无就不加入
			orgsOut.add(o);
			typesmaps.put(o.getOrgId(), types);
			List<ExpenseReimbursement> expenseReimbursements = expenseReimbursementService.getStatics(o.getOrgId(), start, end, type);
			for (ExpenseReimbursement e : expenseReimbursements) {
				if (StringUtils.isEmpty(e.getMoney()))
					continue;
				valuesmaps.put(o.getOrgId() + e.getTypes() + e.getCtime(), e.getMoney());
				if (dateTotalValuesmaps.containsKey(e.getCtime())) {
					dateTotalValuesmaps.put(e.getCtime(), dateTotalValuesmaps.get(e.getCtime()) + Double.valueOf(e.getMoney()));
				}
				total += Double.valueOf(e.getMoney());
			}
		}
		return model.addObject("orgs", orgsOut).addObject("total", total).addObject("dates", dates).addObject("typesmaps", typesmaps).addObject("valuesmaps", valuesmaps)
				.addObject("dateTotalValuesmaps", dateTotalValuesmaps);
	}

	@RequestMapping("depart")
	public ModelAndView depart(@RequestParam(required = false, value = "orgId") String orgId, @RequestParam(required = false, value = "start") String start,
			@RequestParam(required = false, value = "end") String end, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(start) && StringUtils.isEmpty(end)) {
			start = DateUtils.getDefinedMonth(-5, "yyyy-MM");
			end = DateUtils.getDefinedYear(0, "yyyy-MM");
		}
		List<ExecutiveOrg> orgs = new ArrayList<>();
		List<ExecutiveOrg> orglead = executiveService.getOrgsBySplitLevel(2);
		if (CollectionUtils.isNotEmpty(orglead))
			orgs.addAll(orglead);
		List<ExecutiveOrg> orgdepart = executiveService.getOrgsBySplitLevel(3);
		if (CollectionUtils.isNotEmpty(orgdepart))
			orgs.addAll(orgdepart);
		if (StringUtils.isEmpty(orgId))
			orgId = orgs.get(0).getOrgId();
		ModelAndView model = new ModelAndView("makshi/expense/reimbursement/reimbursementDepart.jsp").addObject("orgId", orgId).addObject("orgs", orgs).addObject("start", start).addObject("end", end);
		// 1、表示该部门下面的所有的子部门信息的集合，2表示只统计该部门，因为深水咨询这个部门特殊
		int type = 1;
		if (CollectionUtils.isNotEmpty(orgdepart) && orgId.equalsIgnoreCase(orglead.get(0).getOrgId())) {
			type = 2;
		}
		List<ExpenseReimbursementDepartUser> users = expenseReimbursementService.getUsers(orgId, start, end, type);
		if (CollectionUtils.isEmpty(users))
			return model;
		Map<String, Double> dateTotalValuesmaps = new HashMap<String, Double>();
		List<ExpenseReimbursementTypes> types = expenseReimbursementService.getTypes(orgId, start, end, type);
		if (CollectionUtils.isEmpty(types))
			return model;

		Map<String, String> valuesmaps = new HashMap<String, String>();
		List<ExpenseReimbursementDepart> values = expenseReimbursementService.getDepartStatics(orgId, start, end, type);
		if (CollectionUtils.isEmpty(values))
			return model;
		for (ExpenseReimbursementDepart e : values) {
			if (StringUtils.isEmpty(e.getMoney()))
				continue;
			valuesmaps.put(e.getUserName() + e.getTypes(), e.getMoney());
			if (dateTotalValuesmaps.containsKey(e.getUserName())) {
				dateTotalValuesmaps.put(e.getUserName(), dateTotalValuesmaps.get(e.getUserName()) + Double.valueOf(e.getMoney()));
			} else {
				dateTotalValuesmaps.put(e.getUserName(), Double.valueOf(e.getMoney()));
			}
		}
		return model.addObject("types", types).addObject("users", users).addObject("valuesmaps", valuesmaps).addObject("dateTotalValuesmaps", dateTotalValuesmaps);
	}

}
