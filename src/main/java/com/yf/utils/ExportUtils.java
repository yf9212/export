package com.yf.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;


public class ExportUtils {
	/**
	 * 
	 * @param list 结果集
	 * @param path  导出路径
	 * @param fileName 导出文件名
	 */
	public static <T>  void doExportExcel(List<T> list,String path,
			String  fileName) {
		try {
			ExportBean excelExport = new ExportBean(fileName, list);
			SXSSFWorkbook wb = excelExport.getWorkBook();
			if(!path.endsWith("/")){
				path=path+"/";
			}
			File path1= new File(path);
			if(!path1 .exists()  && !path1 .isDirectory()){
				path1.mkdirs();
			}
			File file=new File(path+fileName+".xlsx");
			FileOutputStream os=new FileOutputStream(file);
			wb.write(os);
			os.flush();
			os.close();
			wb.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param list 结果集
	 * @param path 路径
	 * @param fileName  文件名
	 * @param sheetRowCount 每个sheet页数量大小
	 */
	public static <T>  void doExportExcel(List<T> list,String path,
			String  fileName,int sheetRowCount) {
		try {
			ExportBean excelExport = new ExportBean(fileName, list,sheetRowCount);
			SXSSFWorkbook wb = excelExport.getWorkBook();
			if(!path.endsWith("/")){
				path=path+"/";
			}
			File path1= new File(path);
			if(!path1 .exists()  && !path1 .isDirectory()){
				path1.mkdirs();
			}
			File file=new File(path+fileName+".xlsx");
			FileOutputStream os=new FileOutputStream(file);
			wb.write(os);
			os.flush();
			os.close();
			wb.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
