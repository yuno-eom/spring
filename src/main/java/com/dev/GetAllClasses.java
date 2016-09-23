package com.dev;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.common.reflect.ClassPath;

public class GetAllClasses {
	
	final static ClassLoader loader = Thread.currentThread().getContextClassLoader();
	
	public static void main(String args[]) {
		try {
			int r = 0;
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("classes");
			HSSFRow row = null;
			
			for(final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()){
				if(info.getName().startsWith("com.dev")){
					final Class<?> clazz = info.load();
					String className = clazz.getName();
					className = className.substring(className.lastIndexOf(".")+1);
					System.out.println();
					System.out.println("class: "+clazz);
					System.out.println("------------------");
					
					row = sheet.createRow(r);
					row.createCell(0).setCellValue(className);
					r++;
					
					Field f[] = clazz.getDeclaredFields();
					for(int i = 0; i < f.length; i++){
						String fieldName = f[i].getName();
						System.out.println("field: "+fieldName);
						
						String fieldType = f[i].getType().toString();
						fieldType = fieldType.substring(fieldType.lastIndexOf(".")+1);
						System.out.println("type: "+fieldType);
						System.out.println("---------");
						
						row = sheet.createRow(r);
						row.createCell(0).setCellValue(fieldName);
						row.createCell(1).setCellValue(fieldType);
						row.createCell(2).setCellValue("");
						row.createCell(3).setCellValue("");
						r++;
					}
					System.out.println("------------------");
					r++;
					
					Method m[] = clazz.getDeclaredMethods();
					for(int i = 0; i < m.length; i++){
						String methodName = m[i].getName();
						System.out.println("method: "+methodName);
						
						String parameters = "";
						Class<?> param[] = m[i].getParameterTypes();
						for(int j = 0; j < param.length; j++){
							if(j > 0) parameters += ", ";
							String strParam = param[j].toString();
							strParam = strParam.substring(strParam.lastIndexOf(".")+1);
							parameters += strParam;
						}
						System.out.println("parameters: "+parameters);
						
						String returnType = m[i].getReturnType().toString();
						returnType = returnType.substring(returnType.lastIndexOf(".")+1);
						System.out.println("return: "+returnType);
						System.out.println("---------");
						
						row = sheet.createRow(r);
						row.createCell(0).setCellValue(methodName);
						row.createCell(1).setCellValue(parameters);
						row.createCell(2).setCellValue(returnType);
						row.createCell(3).setCellValue("");
						r++;
					}
					System.out.println("------------------");
					r++;
				}
			}
			
			FileOutputStream fileOut = new FileOutputStream("/Users/yuno/Downloads/classes.xls");
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Throwable e) {
			System.err.println(e);
		}
	}

}