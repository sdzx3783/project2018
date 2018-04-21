package com.hotent.makshi.service.finance;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

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

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.finance.UserInfoVoDao;
import com.hotent.makshi.model.finance.UserInfoVo;

@Service
public class UserInfoVoService extends BaseService<UserInfoVo> {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private UserInfoVoDao dao;

	public UserInfoVoService() {
	}

	@Override
	protected IEntityDao<UserInfoVo, Long> getEntityDao() {
		return dao;
	}

	public List<UserInfoVo> getTransferList(QueryFilter queryFilter) {
		return dao.getBySqlKey("getTransferList", queryFilter);
	}

	public InputStream export(List<String[]> rowName, List<Object[]> dataList, List<Object[]> formalList, List<Object[]> adjustList, String title, String[] subtitle) throws Exception {
		InputStream is = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(); // 创建工作簿对象
			XSSFSheet sheet = workbook.createSheet(); // 创建工作表
			// 产生表格标题行
			XSSFRow rowm = sheet.createRow(0);
			XSSFCell cellTiltle = rowm.createCell(0);

			// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
			XSSFCellStyle columnTopStyle = this.getEntryColumnTopStyle(workbook);// 获取列头样式对象
			XSSFCellStyle subTitleStyle = this.getSubTitleStyle(workbook);// 获取子标题标语样式对象
			XSSFCellStyle style = this.getStyle(workbook); // 单元格样式对象

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (rowName.get(0).length - 1)));
			cellTiltle.setCellStyle(columnTopStyle);
			cellTiltle.setCellValue(title);
			// 标语1：
			XSSFRow firstSubTitle = sheet.createRow(1);
			XSSFCell firstSubTitleCell = firstSubTitle.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, (rowName.get(0).length - 1)));
			firstSubTitleCell.setCellStyle(subTitleStyle);
			firstSubTitleCell.setCellValue(subtitle[0]);
			// 定义所需列数
			int columnNum = rowName.get(0).length;
			XSSFRow rowRow2 = sheet.createRow(2);
			for (int i = 0; i < columnNum; i++) {
				if (i == 9) {
					XSSFCell createCell = rowRow2.createCell(i);
					createCell.setCellType(XSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
					XSSFRichTextString text2 = new XSSFRichTextString("试用期工资");
					createCell.setCellValue(text2); // 设置列头单元格的值
					createCell.setCellStyle(style); // 设置列头单元格样式
				} else if (i > 9 && i <= 11) {
					XSSFCell createCell = rowRow2.createCell(i);
					createCell.setCellType(XSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
					createCell.setCellValue(""); // 设置列头单元格的值
					createCell.setCellStyle(style); // 设置列头单元格样式
				} else {
					XSSFCell createCell = rowRow2.createCell(i);
					createCell.setCellType(XSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
					XSSFRichTextString text2 = new XSSFRichTextString(rowName.get(0)[i]);
					createCell.setCellValue(text2); // 设置列头单元格的值
					createCell.setCellStyle(style); // 设置列头单元格样式
				}
			}
			XSSFRow rowRowName = sheet.createRow(3); // 在索引3的位置创建行(最顶端的行开始的第二行)

			// 将列头设置到sheet的单元格中
			for (int n = 0; n < columnNum; n++) {
				if (n >= 9 && n <= 11) {
					XSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
					cellRowName.setCellType(XSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
					XSSFRichTextString text = new XSSFRichTextString(rowName.get(0)[n]);
					cellRowName.setCellValue(text); // 设置列头单元格的值
					cellRowName.setCellStyle(style); // 设置列头单元格样式
				} else {
					XSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
					cellRowName.setCellType(XSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
					cellRowName.setCellValue(""); // 设置列头单元格的值
					cellRowName.setCellStyle(style); // 设置列头单元格样式
				}
			}
			// 合并row(2)和row(3)
			for (int i = 0; i < columnNum; i++) {
				if (i < 9 || i > 11) {
					sheet.addMergedRegion(new CellRangeAddress(2, 3, i, i));
				}
			}
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 9, 11));
			// 将查询出的数据设置到sheet对应的单元格中
			for (int i = 0; i < dataList.size(); i++) {

				Object[] obj = dataList.get(i);// 遍历每个对象
				XSSFRow row = sheet.createRow(i + 4);// 创建所需的行数

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
			// 获取标语二出现的行数
			// 标语2：
			XSSFRow secondSubTitle = sheet.createRow(5 + dataList.size());// 空一行
			XSSFCell secondSubTitleCell = secondSubTitle.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(5 + dataList.size(), 5 + dataList.size(), 0, (rowName.get(0).length - 1)));
			secondSubTitleCell.setCellStyle(subTitleStyle);
			secondSubTitleCell.setCellValue(subtitle[1]);
			// 列头2
			XSSFRow rowRowName2 = sheet.createRow(6 + dataList.size()); // 在索引3的位置创建行(最顶端的行开始的第二行)

			// 将列头设置到sheet的单元格中
			for (int n = 0; n < columnNum; n++) {
				XSSFCell cellRowName = rowRowName2.createCell(n); // 创建列头对应个数的单元格
				cellRowName.setCellType(XSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
				XSSFRichTextString text = new XSSFRichTextString(rowName.get(1)[n]);
				cellRowName.setCellValue(text); // 设置列头单元格的值
				cellRowName.setCellStyle(style); // 设置列头单元格样式
			}
			// 将查询出的数据设置到sheet对应的单元格中
			for (int i = 0; i < formalList.size(); i++) {

				Object[] obj = formalList.get(i);// 遍历每个对象
				XSSFRow row = sheet.createRow(i + 7 + dataList.size());// 创建所需的行数

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

			// 获取标语三出现的行数
			// 标语3：
			XSSFRow thirdSubTitle = sheet.createRow(9 + dataList.size() + adjustList.size());// 空一行
			XSSFCell thirdSubTitleCell = thirdSubTitle.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(9 + dataList.size() + adjustList.size(), 9 + dataList.size() + adjustList.size(), 0, (rowName.get(0).length - 1)));
			thirdSubTitleCell.setCellStyle(subTitleStyle);
			thirdSubTitleCell.setCellValue(subtitle[2]);
			// 列头3
			XSSFRow rowRowName3 = sheet.createRow(10 + dataList.size() + adjustList.size()); // 在索引3的位置创建行(最顶端的行开始的第二行)

			// 将列头设置到sheet的单元格中
			for (int n = 0; n < columnNum; n++) {
				XSSFCell cellRowName = rowRowName3.createCell(n); // 创建列头对应个数的单元格
				cellRowName.setCellType(XSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
				XSSFRichTextString text = new XSSFRichTextString(rowName.get(2)[n]);
				cellRowName.setCellValue(text); // 设置列头单元格的值
				cellRowName.setCellStyle(style); // 设置列头单元格样式
			}
			// 合并单元格
			sheet.addMergedRegion(new CellRangeAddress(10 + dataList.size() + adjustList.size(), 10 + dataList.size() + adjustList.size(), 5, 6));
			sheet.addMergedRegion(new CellRangeAddress(10 + dataList.size() + adjustList.size(), 10 + dataList.size() + adjustList.size(), 9, 10));
			sheet.addMergedRegion(new CellRangeAddress(10 + dataList.size() + adjustList.size(), 10 + dataList.size() + adjustList.size(), 11, 12));
			// 将查询出的数据设置到sheet对应的单元格中
			for (int i = 0; i < adjustList.size(); i++) {

				Object[] obj = formalList.get(i);// 遍历每个对象
				XSSFRow row = sheet.createRow(i + 11 + dataList.size() + formalList.size());// 创建所需的行数

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
				sheet.addMergedRegion(new CellRangeAddress(i + 11 + dataList.size() + formalList.size(), i + 11 + dataList.size() + formalList.size(), 5, 6));
				sheet.addMergedRegion(new CellRangeAddress(i + 11 + dataList.size() + formalList.size(), i + 11 + dataList.size() + formalList.size(), 9, 10));
				sheet.addMergedRegion(new CellRangeAddress(i + 11 + dataList.size() + formalList.size(), i + 11 + dataList.size() + formalList.size(), 11, 12));
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

	/*
	 * 招聘入职员工表单列头单元格样式
	 */
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
		style.setBorderBottom(XSSFCellStyle.BORDER_DASHED);
		// 设置底边框颜色;
		style.setBottomBorderColor(new XSSFColor(Color.BLACK));
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_DASHED);
		// 设置左边框颜色;
		style.setLeftBorderColor(new XSSFColor(Color.BLACK));
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_DASHED);
		// 设置右边框颜色;
		style.setRightBorderColor(new XSSFColor(Color.BLACK));
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_DASHED);
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
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_DASHED);
		// 设置底边框颜色;
		style.setBottomBorderColor(new XSSFColor(Color.BLACK));
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_DASHED);
		// 设置左边框颜色;
		style.setLeftBorderColor(new XSSFColor(Color.BLACK));
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_DASHED);
		// 设置右边框颜色;
		style.setRightBorderColor(new XSSFColor(Color.BLACK));
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_DASHED);
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

	/*
	 * 子标题信息单元格样式
	 */
	public XSSFCellStyle getSubTitleStyle(XSSFWorkbook workbook) {
		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_DASHED);
		// 设置底边框颜色;
		style.setBottomBorderColor(new XSSFColor(Color.BLACK));
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_DASHED);
		// 设置左边框颜色;
		style.setLeftBorderColor(new XSSFColor(Color.BLACK));
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_DASHED);
		// 设置右边框颜色;
		style.setRightBorderColor(new XSSFColor(Color.BLACK));
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_DASHED);
		// 设置顶边框颜色;
		style.setTopBorderColor(new XSSFColor(Color.BLACK));
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		return style;

	}
}
