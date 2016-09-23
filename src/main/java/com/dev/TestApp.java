package com.dev;

import java.text.DecimalFormat;

public class TestApp {
	
	public static void main(String args[]) {
		try {
			int fraction = 2;
			int denominator = 3;
			System.out.println(fraction+"/"+denominator+"=" + toPercent(fraction*100,denominator));
			System.out.println(fraction+"/"+denominator+"=" + (int)(toPercent(2,fraction,denominator)*100));
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
	
	private static int toPercent(int fraction, int denominator){
		return (int)(((double)fraction/denominator)*100);
	}
	
	private static double toPercent(int decimalCount, int fraction, int denominator){
		String pattern = "#.";
		for (int i=0; i<decimalCount; i++) pattern += "0";
		DecimalFormat df = new DecimalFormat(pattern);
		
		return Double.parseDouble(df.format(((double)fraction/denominator)*100));
	}

}