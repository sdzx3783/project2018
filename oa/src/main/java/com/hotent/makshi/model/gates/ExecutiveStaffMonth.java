/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-11-24，cp创建。 
 */
package com.hotent.makshi.model.gates;

import com.hotent.makshi.utils.ArithmeticUtil;

public class ExecutiveStaffMonth {
	private String orgId;
	private String orgName;
	private int perMonthTotal;// 月初人数
	private int cutMonthTotal; // 月底人数
	private int goMonthNum;// 整月离职人数
	private int inMonthNum;// 整月新进人数
	private int foMonthNum;// 整月转正人数
	private int transaction;// 部门调动人数
	private int salaryMonthNum;// 部门薪资调整人数

	private double monthAverageNum;// 月平均人数 =（月初人数＋月底人数）÷2

	public double getMonthAverageNum() {
		return ArithmeticUtil.div((perMonthTotal + cutMonthTotal), 2, 2);
	}

	private double turnoverRate;// 月员工离职率=部门整月员工离职总人数÷部门月平均人数×100％

	public double getTurnoverRate() {
		if (0 == getMonthAverageNum())
			return 0;
		return ArithmeticUtil.div(goMonthNum, getMonthAverageNum(), 4);
	}

	private double newArrivalRate;// 月员工新进率=部门整月员工新进总人数÷月平均人数×100％

	public double getNewArrivalRate() {
		if (0 == getMonthAverageNum())
			return 0;
		return ArithmeticUtil.div(inMonthNum, getMonthAverageNum(), 4);
	}

	private double retentionRate;// 月员工留存率=月底人数÷月初人数×100％

	public double getRetentionRate() {
		if (0 == perMonthTotal)
			return 0;
		return ArithmeticUtil.div(cutMonthTotal, perMonthTotal, 4);
	}

	private double lossRate;// 月员工损失率=部门整月员工离职总人数÷月初人数×100％

	public double getLossRate() {
		if (0 == perMonthTotal)
			return 0;
		return ArithmeticUtil.div(goMonthNum, perMonthTotal, 4);
	}

	private double exportRatio;// 月员工进出比率=部门整月新进员工总人数÷部门整月离职员工总人数×100％

	public double getExportRatio() {
		if (0 == goMonthNum)
			return inMonthNum;
		return ArithmeticUtil.div(inMonthNum, goMonthNum, 4);
	}

	private double newEmployeeRatio;// 新晋员工比率=部门当月已转正员工数/部门在职总人数*100%

	public double getNewEmployeeRatio() {
		if (0 == cutMonthTotal)
			return 0;
		return ArithmeticUtil.div(foMonthNum, cutMonthTotal, 4);
	}

	private double turnoverLostRate;// 异动率=部门异动人数/部门在职总人数*100%

	public double getTurnoverLostRate() {
		if (0 == cutMonthTotal)
			return 0;
		return ArithmeticUtil.div(transaction, cutMonthTotal, 4);
	}

	private double staffTurnoverLostRate;// 人员流动率=（月员工新进率+月员工离职率）/2

	public double getStaffTurnoverLostRate() {
		return ArithmeticUtil.div((getNewArrivalRate() + getTurnoverRate()), 2, 4);
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getPerMonthTotal() {
		return perMonthTotal;
	}

	public void setPerMonthTotal(int perMonthTotal) {
		this.perMonthTotal = perMonthTotal;
	}

	public int getCutMonthTotal() {
		return cutMonthTotal;
	}

	public void setCutMonthTotal(int cutMonthTotal) {
		this.cutMonthTotal = cutMonthTotal;
	}

	public int getGoMonthNum() {
		return goMonthNum;
	}

	public void setGoMonthNum(int goMonthNum) {
		this.goMonthNum = goMonthNum;
	}

	public int getInMonthNum() {
		return inMonthNum;
	}

	public void setInMonthNum(int inMonthNum) {
		this.inMonthNum = inMonthNum;
	}

	public int getFoMonthNum() {
		return foMonthNum;
	}

	public void setFoMonthNum(int foMonthNum) {
		this.foMonthNum = foMonthNum;
	}

	public int getTransaction() {
		return transaction;
	}

	public void setTransaction(int transaction) {
		this.transaction = transaction;
	}

	public int getSalaryMonthNum() {
		return salaryMonthNum;
	}

	public void setSalaryMonthNum(int salaryMonthNum) {
		this.salaryMonthNum = salaryMonthNum;
	}

}
