package com.hotent.makshi.service.summary;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.summary.SummaryPersonalVipDao;
import com.hotent.makshi.model.summary.SummaryPersonalVip;

@Service
public class SummaryPersonalVipService extends BaseService<SummaryPersonalVip> {
	private static final int MAX_ROW = 65500;
	public static final short ROW_HEIGHT = 700;
	public static final int EXCEL_SHEET_MAX_NUM = 60000;
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private SummaryPersonalVipDao summaryPersonalVipDao;

	@Override
	protected IEntityDao getEntityDao() {
		return summaryPersonalVipDao;
	}

	public void save(SummaryPersonalVip summaryPersonalVip) throws Exception {
		Long id = summaryPersonalVip.getId();
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			summaryPersonalVip.setId(id);
			this.add(summaryPersonalVip);
		} else {
			this.update(summaryPersonalVip);
		}
	}

	public void export(List<SummaryPersonalVip> resultList, String fileName, HttpServletRequest request, HttpServletResponse response) {
		try {
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 标题单元格风格
			HSSFCellStyle cellStyle = createCellStyle(workbook, new HSSFColor.WHITE().getIndex());
			// 偶数行单元格风格
			HSSFCellStyle evencellStyle = createCellStyle(workbook, new HSSFColor.LIGHT_GREEN().getIndex());
			// 奇数行单元格风格
			HSSFCellStyle oddcellStyle = createCellStyle(workbook, new HSSFColor.WHITE().getIndex());
			// 列头单元格风格
			HSSFCellStyle titlecellStyle = createCellStyle(workbook, new HSSFColor.WHITE().getIndex());
			// 行头单元格风格
			HSSFCellStyle rowtitlecellStyle = createCellStyle(workbook, new HSSFColor.YELLOW().getIndex());
			HSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight((short) 9);
			rowtitlecellStyle.setFont(titleFont);

			// 如果中奖记录超出了单个excel最大行数
			int totalRecord = resultList.size();
			int totalSheet = totalRecord / MAX_ROW;
			if ((totalRecord % MAX_ROW) > 0)
				totalSheet++;

			for (int i = 0; i < totalSheet; i++) {
				// 初始化表
				HSSFSheet sheet = workbook.createSheet(fileName + (i + 1));
				// workbook.setSheetName(0, fileName);
				initRecordSheetColumns(sheet);

				// 初始化标题
				int rowIndex = 0;
				HSSFRow row = sheet.createRow((short) rowIndex);
				row.setHeight((short) 900);
				CellRangeAddress reg = new CellRangeAddress(rowIndex, rowIndex, 0, 8);
				sheet.addMergedRegion(reg);
				createCell(row, 0, fileName, cellStyle);

				// 初始化表格的列头内容
				row = sheet.createRow(++rowIndex);
				row.setHeight((short) 900);
				initRecordSheetColumnsHeads(row, rowtitlecellStyle);

				// 将数据输出到表格中
				int startIndex = i * MAX_ROW;
				int endIndex = (i + 1) * MAX_ROW;
				if (endIndex > totalRecord)
					endIndex = totalRecord;
				rowIndex = addRecordContentToExcel(resultList.subList(startIndex, endIndex), row, rowIndex, sheet, titlecellStyle, evencellStyle, oddcellStyle);
			}
			// 写入字节输出流对象中
			workbook.write(byteArrayOut);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			// 执行导出
			exportExcel(fileName, out.toByteArray(), request, response);
		} catch (Exception e) {
			log.error("错误信息", e);
		}
	}

	/**
	 * 创建单元格表格样式
	 * 
	 * @param workbook
	 * @param isWrapText
	 * @param alignment
	 * @param valignment
	 * @param bBorder
	 * @param lBorder
	 * @param rBorder
	 * @param tBorder
	 * @param fillPattern
	 * @param color
	 * @return
	 */
	public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short color) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(color);
		return cellStyle;
	}

	/**
	 * 向表格中添加单元格
	 * 
	 * @param row ：需要添加单元格的行
	 * @param cellnum ：在第几行添加单元格
	 * @param value ：单元格的值
	 * @param cellStyle ：单元格的样式
	 */
	public static void createCell(HSSFRow row, int cellnum, String value, HSSFCellStyle cellStyle) {
		if (value == null || value.equals("null")) {
			value = " ";
		}
		HSSFCell cell = row.createCell(cellnum);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	/**
	 * 流方式导出数据到excel表格中，并区分对待火狐、IE、Chrome三种浏览器对中文文件名的处理
	 * 
	 * @param fileName
	 * @param datas
	 * @throws Exception
	 */
	public static void exportExcel(String fileName, byte[] datas, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String agent = request.getHeader("USER-AGENT");
		if (agent != null && agent.indexOf("Firefox") >= 0) {
			// FF
		} else {
			// IE or Chrome
			fileName = URLEncoder.encode(fileName, "UTF-8");// 兼容IE6，此处设置需IE选项中urf-8选项支持
			fileName = fileName.replaceAll("\\+", "%20"); // 解决编码后空格变+号的情况
		}

		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");// attachment
		response.setContentLength(datas.length);
		ServletOutputStream ouputStream = response.getOutputStream();

		ouputStream.write(datas, 0, datas.length);
		ouputStream.flush();
		ouputStream.close();
	}

	public static void initRecordSheetColumns(HSSFSheet sheet) {
		int index = 0;
		sheet.setColumnWidth(index++, (40 * 240));
		sheet.setColumnWidth(index++, (40 * 80));
		sheet.setColumnWidth(index++, (40 * 120));
		sheet.setColumnWidth(index++, (40 * 120));
		sheet.setColumnWidth(index++, (40 * 120));
		sheet.setColumnWidth(index++, (40 * 80));
		sheet.setColumnWidth(index++, (40 * 80));
		sheet.setColumnWidth(index++, (40 * 80));
		sheet.setColumnWidth(index++, (40 * 250));
		sheet.setPrintGridlines(true);// 打印网格线
		sheet.setAutobreaks(true);
	}

	public static void initRecordSheetColumnsHeads(HSSFRow row, HSSFCellStyle rowtitlecellStyle) {
		int index = 0;
		createCell(row, index++, "入会机构", rowtitlecellStyle);
		createCell(row, index++, "级别", rowtitlecellStyle);
		createCell(row, index++, "证书编号", rowtitlecellStyle);
		createCell(row, index++, "首次入会时间", rowtitlecellStyle);
		createCell(row, index++, "缴费标准（元）", rowtitlecellStyle);
		createCell(row, index++, "会费缴纳至", rowtitlecellStyle);
		createCell(row, index++, "发证日期", rowtitlecellStyle);
		createCell(row, index++, "有效日期", rowtitlecellStyle);
		createCell(row, index++, "备注", rowtitlecellStyle);
	}

	public static int addRecordContentToExcel(List<SummaryPersonalVip> resultList, HSSFRow row, int rowIndex, HSSFSheet sheet, HSSFCellStyle titlecellStyle, HSSFCellStyle evencellStyle,
			HSSFCellStyle oddcellStyle) {
		// 用来存放奇偶数行的临时对象
		HSSFCellStyle tempcellStyle = null;
		for (SummaryPersonalVip d : resultList) {
			row = sheet.createRow(++rowIndex);
			row.setHeight(ROW_HEIGHT);
			if (rowIndex % 2 == 0)
				tempcellStyle = oddcellStyle;
			else
				tempcellStyle = evencellStyle;
			int index = 0;
			createCell(row, index++, d.getOrganization() + "", tempcellStyle);
			createCell(row, index++, d.getLevel() + "", tempcellStyle);
			createCell(row, index++, d.getCertificateNo() + "", tempcellStyle);
			createCell(row, index++, d.getMembershipTime() + "年", tempcellStyle);
			createCell(row, index++, d.getPaymentStandard() + "", tempcellStyle);
			createCell(row, index++, d.getEffectiveTime() + "年", tempcellStyle);
			createCell(row, index++, d.getCertificationDate() + "", tempcellStyle);
			createCell(row, index++, d.getCerteffectiveTime() + "", tempcellStyle);
			createCell(row, index++, d.getRemark() + "", tempcellStyle);
		}
		return rowIndex;
	}
}
