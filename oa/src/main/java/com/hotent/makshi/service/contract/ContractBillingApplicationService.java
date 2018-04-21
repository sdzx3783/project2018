package com.hotent.makshi.service.contract;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.log4j.Logger;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.contract.ContractBillingApplicationDao;
import com.hotent.makshi.dao.contract.ContractBillingRecordDao;
import com.hotent.makshi.model.contract.ContractBillingApplication;
import com.hotent.makshi.model.contract.ContractBillingRecord;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Service
public class ContractBillingApplicationService extends BaseService<ContractBillingApplication> {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private ContractBillingApplicationDao dao;
	@Resource
	private ContractinfoService infoService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmService bpmService;
	@Resource
	private ContractBillingRecordDao recordDao;

	public ContractBillingApplicationService() {
	}

	@Override
	protected IEntityDao<ContractBillingApplication, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 流程处理器方法 用于处理业务数据
	 * 
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd) throws Exception {
		Map data = cmd.getFormDataMap();
		if (BeanUtils.isNotEmpty(data)) {
			String json = data.get("json").toString();
			ContractBillingApplication contractBillingApplication = getContractBillingApplication(json);
			if (StringUtil.isEmpty(cmd.getBusinessKey())) {
				Long genId = UniqueIdUtil.genId();
				contractBillingApplication.setId(genId);
				this.add(contractBillingApplication);
			} else {
				contractBillingApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(contractBillingApplication);
			}
			cmd.setBusinessKey(contractBillingApplication.getId().toString());
		}
	}

	/**
	 * 根据json字符串获取ContractBillingApplication对象
	 * 
	 * @param json
	 * @return
	 */
	public ContractBillingApplication getContractBillingApplication(String json) {
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if (StringUtil.isEmpty(json))
			return null;
		JSONObject obj = JSONObject.fromObject(json);
		ContractBillingApplication contractBillingApplication = (ContractBillingApplication) JSONObject.toBean(obj, ContractBillingApplication.class);
		return contractBillingApplication;
	}

	/**
	 * 保存 合同开票申请 信息
	 * 
	 * @param contractBillingApplication
	 */

	public void save(ContractBillingApplication contractBillingApplication) throws Exception {
		Long id = contractBillingApplication.getId();
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			contractBillingApplication.setId(id);
			this.add(contractBillingApplication);
		} else {
			this.update(contractBillingApplication);
		}
	}

	public InputStream exportContract(String[] rowName, List<Object[]> dataList, String title, String[] subtitle) {
		InputStream is = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(); // 创建工作簿对象
			XSSFSheet sheet = workbook.createSheet(); // 创建工作表
			// 产生表格标题行
			XSSFRow rowm = sheet.createRow(0);
			XSSFCell cellTiltle = rowm.createCell(0);

			// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
			XSSFCellStyle columnTopStyle = this.getEntryColumnTopStyle(workbook);// 获取列头样式对象
			XSSFCellStyle style = this.getStyle(workbook); // 单元格样式对象
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (rowName.length - 1)));
			cellTiltle.setCellStyle(columnTopStyle);
			cellTiltle.setCellValue(title);

			int columnNum = rowName.length;
			XSSFRow subRow = sheet.createRow(2);
			subRow.createCell(1).setCellValue("申请日期");
			subRow.createCell(2).setCellValue(subtitle[0]);
			subRow.createCell(3).setCellValue("至");
			subRow.createCell(4).setCellValue(subtitle[1]);
			subRow.createCell(6).setCellValue("开票状态");
			subRow.createCell(7).setCellValue(subtitle[2]);
			XSSFRow rowRowName = sheet.createRow(4);
			// 将列头设置到sheet的单元格中
			for (int n = 0; n < columnNum; n++) {
				XSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
				cellRowName.setCellType(XSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
				XSSFRichTextString text = new XSSFRichTextString(rowName[n]);
				cellRowName.setCellValue(text); // 设置列头单元格的值
				cellRowName.setCellStyle(style); // 设置列头单元格样式
			}

			// 将查询出的数据设置到sheet对应的单元格中
			for (int i = 0; i < dataList.size(); i++) {

				Object[] obj = dataList.get(i);// 遍历每个对象
				XSSFRow row = sheet.createRow(i + 5);// 创建所需的行数

				for (int j = 0; j < obj.length; j++) {
					XSSFCell cell = null; // 设置单元格的数据类型
					// 此处注释部分为第一列创建序号
					if (j == 0) {
						cell = row.createCell(j, XSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(i + 1);
					} else {
						cell = row.createCell(j, XSSFCell.CELL_TYPE_STRING);
						if (!"".equals(obj[j]) && obj[j] != null) {
							cell.setCellValue(obj[j].toString()); // 设置单元格的值
						}
					}
					cell.setCellStyle(style); // 设置单元格样式
				}
			}
			// 让列宽随着导出的列长自动适应
			for (int colNum = 0; colNum < columnNum; colNum++) {
				int columnWidth = sheet.getColumnWidth(colNum) / 256;
				for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
					XSSFRow currentRow;
					// 当前行未被使用过
					if (sheet.getRow(rowNum) == null) {
						currentRow = sheet.createRow(rowNum);
					} else {
						currentRow = sheet.getRow(rowNum);
					}
					if (currentRow.getCell(colNum) != null) {
						XSSFCell currentCell = currentRow.getCell(colNum);
						if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
							int length = currentCell.getStringCellValue().getBytes().length;
							if (columnWidth < length) {
								columnWidth = length;
							}
						}
					}

				}

				if (colNum == 0) {
					sheet.setColumnWidth(colNum, 10 * 256);
				} else {
					sheet.setColumnWidth(colNum, (columnWidth > 50 ? 50 : columnWidth + 4) * 256);
				}
			}

			if (workbook != null) {
				try {
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					workbook.write(output);
					byte[] ba = output.toByteArray();
					is = new ByteArrayInputStream(ba);
				} catch (IOException e) {
					log.error("错误信息", e);
				}
			}

		} catch (Exception e) {
			log.error("错误信息", e);
		}
		return is;// 返回的是一个输入流
	}

	public XSSFCellStyle getEntryColumnTopStyle(XSSFWorkbook workbook) {

		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 20);
		// 字体加粗
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(new XSSFColor(Color.GRAY));
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(new XSSFColor(Color.BLACK));
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(new XSSFColor(Color.BLACK));
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(new XSSFColor(Color.BLACK));
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	/*
	 * 列数据信息单元格样式
	 */
	public XSSFCellStyle getStyle(XSSFWorkbook workbook) {
		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 8);
		// 字体加粗
		// font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(new XSSFColor(Color.BLACK));
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(new XSSFColor(Color.BLACK));
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(new XSSFColor(Color.BLACK));
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(new XSSFColor(Color.BLACK));
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	public void updInfo(DelegateTask delegateTask, ProcessCmd processCmd) {
		Short voteAgree = processCmd.getVoteAgree();
		String actDefId = processCmd.getActDefId();
		String taskName = delegateTask.getName();
		Integer nodeId = 0;
		// 判断是否为合同开票
		if (actDefId == null || !actDefId.equals("contract_billing:1:10000012900086")) {
			return;
		}
		// 审核及开票
		if (taskName != null && taskName.equals("审核及开票")) {
			nodeId = 1;
		}
		// 判断是否为领取发票
		if (taskName != null && taskName.equals("领取发票")) {
			nodeId = 2;
		}
		if (nodeId == 0) {
			return;
		}
		// 审核及开票，通过businessKey跟新数据
		if (voteAgree == 1) {
			contractBillingRecordAdd(processCmd, nodeId);
		}
		// 驳回
		if (voteAgree == 3) {
			contractBillingRecordDel(processCmd, nodeId);
		}

	}

	public void contractBillingRecordAdd(ProcessCmd processCmd, Integer nodeId) {
		String businessKey = processCmd.getBusinessKey();
		if (!StringUtil.isEmpty(businessKey)) {
			ContractBillingApplication contractBillingApplication = dao.getById(Long.parseLong(businessKey));
			if (contractBillingApplication != null) {
				// 获取合同信息
				Contractinfo contractinfo = infoService.getContractinfoByNum(contractBillingApplication.getContract_num());
				// 获取开票记录子表
				List<ContractBillingRecord> contractBillingRecordList = infoService.getContractBillingRecordList(contractinfo.getId());
				ProcessRun processRun = processRunService.getByBusinessKey(contractBillingApplication.getId().toString());
				List<ProcessTask> tasks = null;
				if (BeanUtils.isNotEmpty(processRun)) {
					if (processRun.getActInstId() != null) {
						tasks = bpmService.getTasks(processRun.getActInstId());
					}
				}
				String status = "已结束";
				if (tasks != null && tasks.size() > 0) {
					status = tasks.get(0).getName();
				}
				if (nodeId == 1) {
					// 跟新开票日期、开票金额、申请人
					// 创建子表数据
					String arrivalMoney = null;
					if (contractBillingRecordList == null) {
						contractBillingRecordList = new ArrayList<ContractBillingRecord>();
					}
					// 获取开票金额
					if (null != contractBillingApplication.getEnterNumber()) {
						arrivalMoney = contractBillingApplication.getEnterNumber().toString();
					}
					// 创建子表记录对象
					ContractBillingRecord billingRecord = new ContractBillingRecord(contractinfo.getId(), Long.parseLong(businessKey), new Date(), contractBillingApplication.getBilling_money(),
							contractBillingApplication.getApplicant(), contractBillingApplication.getApplicantID(), status, contractBillingApplication.getTicketTaker(),
							contractBillingApplication.getTicketTakerID(), contractBillingApplication.getEnterTime(), arrivalMoney);
					// 添加到子表
					contractBillingRecordList.add(billingRecord);
					// 添加到合同信息对象中
					contractinfo.setContractBillingRecordList(contractBillingRecordList);
					// 跟新合同信息的开票金额
					Double billingMoney = Double.valueOf(contractBillingApplication.getBilling_money());
					if (contractinfo.getInvoice_amount() != null) {
						// 发票金额合计：已开的加新开的
						billingMoney = Double.valueOf(contractinfo.getInvoice_amount()) + billingMoney * 0.0001;
					}
					contractinfo.setInvoice_amount(billingMoney);
					try {
						contractinfo.setContractBillingRecordList(contractBillingRecordList);
						infoService.save(contractinfo);
					} catch (Exception e) {
						log.error("错误信息", e);
					}

				}
				if (nodeId == 2) {
					// 跟新领票人
					// String userId = contractBillingApplication.getApplicantID();
					String bearer = null;
					String bearerId = null;
					List<ContractBillingRecord> existRecordList = recordDao.getByLinkId(Long.parseLong(businessKey));
					if (existRecordList.size() > 0) {
						ContractBillingRecord contractBillingRecord = existRecordList.get(0);
						if (null != contractBillingApplication.getTicketTakerID()) {
							bearerId = contractBillingApplication.getTicketTakerID();
						}
						if (null != contractBillingApplication.getTicketTaker()) {
							bearer = contractBillingApplication.getTicketTaker();
						}
						contractBillingRecord.setStatus(status);
						contractBillingRecord.setBearerID(bearerId);
						contractBillingRecord.setBearer(bearer);
						recordDao.update(contractBillingRecord);
					}
				}
			}
		}
	}

	public void contractBillingRecordDel(ProcessCmd processCmd, Integer nodeId) {
		if (nodeId == 2) {
			String businessKey = processCmd.getBusinessKey();
			updateInvoiceMoney(businessKey);
		}
	}

	public synchronized void updateInvoiceMoney(String businessKey) {
		// 删除开票记录需要将对应的开票金额做修改
		ContractBillingApplication contractBillingApplication = dao.getById(Long.parseLong(businessKey));
		Contractinfo contractinfo = infoService.getContractinfoByNum(contractBillingApplication.getContract_num());
		// 获取开票金额
		Double arrivalMoney = 0.0;
		if (null != contractBillingApplication.getBilling_money()) {
			arrivalMoney = Double.valueOf(contractBillingApplication.getBilling_money()) / 10000;
		}
		// 获取合同信息开票金额
		Double invoiceAmount = contractinfo.getInvoice_amount();
		// 跟新合同信息
		invoiceAmount = invoiceAmount - arrivalMoney;
		contractinfo.setInvoice_amount(invoiceAmount);
		infoService.update(contractinfo);
		recordDao.delByLinkId(Long.parseLong(businessKey));
	}

	public List<ContractBillingApplication> getByCancelId(String cancelId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cancelId", cancelId);
		List<ContractBillingApplication> list = dao.getBySqlKey("getByCancelId", params);
		return list;
	}
}
