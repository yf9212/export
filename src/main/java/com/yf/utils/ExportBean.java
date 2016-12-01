package com.yf.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.yf.annotation.ExportCellName;


public class ExportBean {
	public static final Integer DEFAULTROWACCESSWINDOWSIZE = 500;

	
	private final String TITLE="title";
	
	private final String FIELD="field";
	
	private String fileName;

	@SuppressWarnings("rawtypes")
	private List<Map> dataList;
	
	@SuppressWarnings("rawtypes")
	private List<Map> columnsList;

	private Class<? extends Object> clazz;
	
	private CellStyle headStyle;
	
	private CellStyle titleStyle;
	
	private CellStyle rowStyle;
	
	private Integer sheetRowNumber;
	
	private Integer crruntSheetNumber;
	
	private Integer sheetCount=0;
	
	private boolean  sheetFlag=false;
	
	/**
	 * 
	 * @param fileName 文件名
	 * @param dataList 数据列表
	 */
	public <T> ExportBean(String fileName, List<T> dataList) {
		this.clazz=dataList.get(0).getClass();
		this.fileName=fileName;
		this.dataList=getDataList(dataList);
		this.columnsList=getColumnsMapList();
	}
	
	/**
	 * 
	 * @param fileName 文件名
	 * @param dataList 数据列表
	 * @param sheetRowNumber  每个sheet页数量
	 */
	public <T> ExportBean(String fileName, List<T> dataList,Integer sheetRowNumber) {
		this.clazz=dataList.get(0).getClass();
		this.fileName=fileName;
		this.dataList=getDataList(dataList);
		this.columnsList=getColumnsMapList();
		this.sheetRowNumber=sheetRowNumber;
		this.sheetFlag=true;
	}

	@SuppressWarnings("rawtypes")
	private <T> List<Map> getDataList(List<T> dataList) {
		List<Map> list=new ArrayList<Map>();
		try {
			for (T t : dataList) {
				Map map=new HashMap();
				BeanUtilsCommon.object2MapWithoutNull(t, map);
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> getColumnsMapList() {
		List<Map> list=new ArrayList<Map>();
		Field[] fields=	 clazz.getDeclaredFields();
		List<Field>  fieldList=new ArrayList<Field>();
		for (Field field : fields) {
			ExportCellName e= field.getAnnotation(ExportCellName.class);
			if(e!=null){
				fieldList.add(field);
			}
		}
		Collections.sort(fieldList, new SortComparator());
		for (Field field : fieldList) {
			ExportCellName e=field.getAnnotation(ExportCellName.class);
			Map map=new HashMap();
			map.put(TITLE, e.value());
			map.put(FIELD, field.getName());
			list.add(map);
		}
		return list;
	}
	
	
	


	/**
	 * 行样式
	 * 
	 * @param workbook
	 * @return
	 */
	protected  CellStyle getOneCellStyle(SXSSFWorkbook workbook) {
		if(this.rowStyle ==null){
			this.rowStyle = workbook.createCellStyle();
			this.rowStyle .setAlignment(HSSFCellStyle.ALIGN_LEFT);
			this.rowStyle .setWrapText(true);
		}
		return this.rowStyle ;
	}

	/**
	 * 列头样式
	 * 
	 * @param workbook
	 * @return
	 */
	protected CellStyle getHeadStyle(SXSSFWorkbook workbook) {
		if(this.headStyle==null){
			this.headStyle = workbook.createCellStyle();
			this.headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			Font font = workbook.createFont();
			font.setFontHeightInPoints((short) 13);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			this.headStyle.setFont(font);
		} 
		return this.headStyle;
	}

	/**
	 * 第一行标题样式
	 * 
	 * @param workbook
	 * @return
	 */
	protected CellStyle getTitleStyle(SXSSFWorkbook workbook) {
		if(this.titleStyle==null){
		this.titleStyle= workbook.createCellStyle();
		this.titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 18);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		this.titleStyle.setFont(font);
		this.titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		this.titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		return this.titleStyle;
	}

	protected SXSSFWorkbook getWorkBook() throws NoSuchFieldException, SecurityException {
		SXSSFWorkbook wb = new SXSSFWorkbook(DEFAULTROWACCESSWINDOWSIZE);
		if(sheetFlag){
			this.sheetCount=this.dataList.size()%this.sheetRowNumber==0?this.dataList.size()/this.sheetRowNumber:(this.dataList.size()/this.sheetRowNumber)+1;
			for (int i = 0; i < sheetCount; i++) {
				this.crruntSheetNumber=i+1;
				int start=i*this.sheetRowNumber;
				int end=(i+1)*this.sheetRowNumber;
				getSheet(wb, this.dataList.subList(start, end>this.dataList.size()?this.dataList.size():end));
			}
		}else{
			getSheet(wb, this.dataList);
		}
		return wb;
	}

	@SuppressWarnings("rawtypes")
	protected Sheet getSheet(SXSSFWorkbook   workbook,
			List<Map> data) throws NoSuchFieldException, SecurityException {
		Sheet sheet;
		if(sheetFlag && this.sheetCount>1){
			sheet = workbook.createSheet(this.fileName+"_"+this.crruntSheetNumber);
		}else{
			sheet = workbook.createSheet(this.fileName);
		}
		Footer  footer=sheet.getFooter();
		//&P 代表当前页
		//&N 代表总页数
		//打印的时候显示在页脚
		footer.setRight("第 &P 页   共 &N 页");
		sheet.setDefaultRowHeightInPoints(20);
		sheet.setDefaultColumnWidth((short) 18);
		// 设置表头
		Row row1 = sheet.createRow(0);
		row1.setHeightInPoints(20);
		for (int column = 0; column < columnsList.size(); column++) {
			Cell cell1 = row1.createCell(column);
			cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell1.setCellValue(columnsList.get(column).get("title")
					.toString());
			cell1.setCellStyle(getHeadStyle(workbook));
		}

		// 填充数据
		for (int j = 0; j < data.size(); j++) {
			Row row2 = sheet.createRow((j + 1)); // 第三行开始填充数据
			row2.setRowStyle(getOneCellStyle(workbook));
			Map cellDataMap = data.get(j);
			for (int column = 0; column < columnsList.size(); column++) {
				Cell cell = row2.createCell(column);
				String cellValue = StringUtils.EMPTY;
				if (columnsList.get(column).get("field") != null) {
					String fieldString = String.valueOf(columnsList.get(column).get("field"));
					cellValue = String.valueOf(cellDataMap.get(fieldString));
				}
				 
				Field  f= clazz.getDeclaredField(columnsList.get(column).get("field").toString());
				if(StringUtils.isNotEmpty(cellValue) &&!"-".equals(cellValue)&& isNum(f)){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Double.valueOf(cellValue));
				}else{
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(cellValue);
				}
				cell.setCellStyle(getOneCellStyle(workbook));
			}
		}
		return sheet;
	}

 
	
	private Boolean isNum(Field  f) {
		if(f!=null){
			String type= f.getType().toString();
			if("class java.lang.Integer".equals(type)){
				return true;
			}else if("class java.math.BigDecimal".equals(type)){
				return true;
			}else if("class java.lang.Double".equals(type)){
				return true;
			}else if("class java.lang.Float".equals(type)){
				return true;
			}else if("class java.lang.Long".equals(type)){
				return true;
			}else if("class java.lang.Short".equals(type)){
				return true;
			}else if("int".equals(type)){
				return true;
			}else if("double".equals(type)){
				return true;
			}else if("float".equals(type)){
				return true;
			}else if("long".equals(type)){
				return true;
			}else if("short".equals(type)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}
	
	
}
