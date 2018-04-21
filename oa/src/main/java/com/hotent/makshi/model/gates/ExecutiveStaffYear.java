/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-11-24，cp创建。 
 */
package com.hotent.makshi.model.gates;

import com.hotent.makshi.utils.ArithmeticUtil;

public class ExecutiveStaffYear {
	private String orgId;
	private String orgName;
	private int perYearTotal;// 年初人数
	private int cutYearTotal; // 年底人数
	private int goYearNum;// 整年离职人数
	private int inYearNum;// 整年新进人数
	private int foYearNum;// 整年转正人数
	private int transaction;// 部门调动人数
	private int salaryYearNum;// 部门薪资调整人数

	private double yearAverageNum;// 年平均人数 =（年初人数＋年底人数）÷2

	public double getYearAverageNum() {
		return ArithmeticUtil.div((perYearTotal + cutYearTotal), 2, 2);
	}

	private double turnoverRate;// 年员工离职率=部门整年员工离职总人数÷部门年平均人数×100％

	public double getTurnoverRate() {
		if (0 == getYearAverageNum())
			return 0;
		return ArithmeticUtil.div(goYearNum, getYearAverageNum(), 4);
	}

	private double newArrivalRate;// 年员工新进率=部门整年员工新进总人数÷年平均人数×100％

	public double getNewArrivalRate() {
		if (0 == getYearAverageNum())
			return 0;
		return ArithmeticUtil.div(inYearNum, getYearAverageNum(), 4);
	}

	private double retentionRate;// 年员工留存率=年底人数÷年初人数×100％

	public double getRetentionRate() {
		if (0 == perYearTotal)
			return 0;
		return ArithmeticUtil.div(cutYearTotal, perYearTotal, 4);
	}

	private double lossRate;// 年员工损失率=部门整年员工离职总人数÷年初人数×100％

	public double getLossRate() {
		if (0 == perYearTotal)
			return 0;
		return ArithmeticUtil.div(goYearNum, perYearTotal, 4);
	}

	private double exportRatio;// 年员工进出比率=部门整年新进员工总人数÷部门整年离职员工总人数×100％

	public double getExportRatio() {
		if (0 == goYearNum)
			return inYearNum;
		return ArithmeticUtil.div(inYearNum, goYearNum, 4);
	}

	private double newEmployeeRatio;// 新晋员工比率=部门当年已转正员工数/部门在职总人数*100%

	public double getNewEmployeeRatio() {
		if (0 == cutYearTotal)
			return 0;
		return ArithmeticUtil.div(foYearNum, cutYearTotal, 4);
	}

	private double turnoverLostRate;// 异动率=部门异动人数/部门在职总人数*100%

	public double getTurnoverLostRate() {
		if (0 == cutYearTotal)
			return 0;
		return ArithmeticUtil.div(transaction, cutYearTotal, 4);
	}

	private double staffTurnoverLostRate;// 人员流动率=（年员工新进率+年员工离职率）/2

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

	public int getPerYearTotal() {
		return perYearTotal;
	}

	public void setPerYearTotal(int perYearTotal) {
		this.perYearTotal = perYearTotal;
	}

	public int getCutYearTotal() {
		return cutYearTotal;
	}

	public void setCutYearTotal(int cutYearTotal) {
		this.cutYearTotal = cutYearTotal;
	}

	public int getGoYearNum() {
		return goYearNum;
	}

	public void setGoYearNum(int goYearNum) {
		this.goYearNum = goYearNum;
	}

	public int getInYearNum() {
		return inYearNum;
	}

	public void setInYearNum(int inYearNum) {
		this.inYearNum = inYearNum;
	}

	public int getFoYearNum() {
		return foYearNum;
	}

	public void setFoYearNum(int foYearNum) {
		this.foYearNum = foYearNum;
	}

	public int getTransaction() {
		return transaction;
	}

	public void setTransaction(int transaction) {
		this.transaction = transaction;
	}

	public int getSalaryYearNum() {
		return salaryYearNum;
	}

	public void setSalaryYearNum(int salaryYearNum) {
		this.salaryYearNum = salaryYearNum;
	}

}
