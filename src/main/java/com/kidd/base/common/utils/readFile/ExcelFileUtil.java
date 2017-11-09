package com.kidd.base.common.utils.readFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kidd.base.common.utils.KiddStringUtils;

public class ExcelFileUtil {

	private static final Logger log = LoggerFactory
			.getLogger(ExcelFileUtil.class);

	private final static String encoding = "UTF-8";

	// 写文件
	public static void writeStr(String path, String content, String name) {
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(path + name), encoding));
			pw.println(content);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			log.error("写文件内容出错");
			e.printStackTrace();
		}
	}

	public static Map<Integer, List<String>> readExcelFile(String path,
			String name) {
		try {
			File file = new File(path + name);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				return readExcelFile(file);
			} else {
				log.error("找不到指定的文件");
			}
		} catch (Exception e) {
			log.error("获取文件出错");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析Excel数据
	 * 
	 * @param configFile
	 * @return
	 * @throws com.hpay.core.common.exception.ActionException
	 */
	private static Map<Integer, List<String>> readExcelFile(File configFile)
			throws IOException {
		// 通过工厂方法创建workbook取消office2007前后差异
		Workbook workbook = null;
		InputStream in = null;
		try {
			in = new FileInputStream(configFile);
			workbook = WorkbookFactory.create(in);
		} catch (Exception e) {
			// throw new ActionException(ERROR, "解析Excel文件出错！");
			return null;
		} finally {
			in.close();
		}
		// 获得第一个sheet
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getLastRowNum();
		if (rowCount == 0) {
			// throw new ActionException(ERROR, "文件无数据，请检查");
		} else if (rowCount > 1000) {
			// throw new ActionException(ERROR, "文件内容不得超过1000条，请检查");
		}
		Map<Integer, List<String>> values = new HashMap<Integer, List<String>>();
		String errorMsg;
		List<String> rowValues = null;
		int rowNum;
		for (int i = 0; i <= rowCount; i++) {
			rowNum = i + 1;
			Row row = sheet.getRow(i);
			if (row == null) {
				errorMsg = "第" + rowNum + "行为空，请检查";
				log.error(errorMsg);
				continue;
			}

			int columnCount = row.getLastCellNum();
			String val = null;
			int colNum;
			rowValues = new ArrayList<String>();

			for (int j = 0; j < columnCount; j++) {
				colNum = j + 1;
				Cell cell = row.getCell(j);
				try {
					// val = cell.getStringCellValue();
					val = getStringValueFromObject(cell).trim();
				} catch (Exception e) {
					// logger.error(e.getMessage(),e);
					val = "第" + rowNum + "行，第" + colNum + "列，异常";
					log.error(val);
				}
				if (KiddStringUtils.isBlank(val)) {
					val = "第" + rowNum + "行，第" + colNum + "列内容为空";
					log.error(val);
				}
				rowValues.add(val);
			}
			values.put(rowNum, rowValues);
		}
		return values;
	}

	/**
	 * 获取excel中对应行中列的值
	 * 
	 * @param oneCell
	 * @return
	 */
	private static String getStringValueFromObject(Cell oneCell) {

		Object oVal = null;
		switch (oneCell.getCellType()) {

		case HSSFCell.CELL_TYPE_STRING:
			oVal = oneCell.getStringCellValue();
			break;

		case HSSFCell.CELL_TYPE_FORMULA:
			oVal = oneCell.getCellFormula();
			break;

		case HSSFCell.CELL_TYPE_NUMERIC:
			oVal = oneCell.getNumericCellValue();
			DecimalFormat df = new DecimalFormat();
			oVal = df.format(oVal).replaceAll(",", "");
			break;

		case HSSFCell.CELL_TYPE_ERROR:
			oVal = "";
			break;
		default:
			oVal = "";
			break;
		}
		return oVal.toString();
	}

}