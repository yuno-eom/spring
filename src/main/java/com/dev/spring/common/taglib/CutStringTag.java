package com.dev.spring.common.taglib;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.tagext.TagSupport;

public final class CutStringTag extends TagSupport {

	private static final long serialVersionUID = 321966421109168178L;
	
	private String cutString; // 자를 문자열
	private String cutLength; // [설정] 자를 크기
	private String suffix; // [설정] 자른 문자열 뒤에 붙일 문자
	private String strongString; // [설정] 강조할 문장

	@Override
	public int doStartTag() {
		try {			
			pageContext.getOut().print(makeCutString());
		} catch (IOException e) {
			
		}		
		
		return SKIP_BODY;		
	}
	
	// 문자열 자르기 및 강조
	public String makeCutString(){
		int cLength = Integer.parseInt(cutLength);
		
		// 말줄임 할 문자열
		String cutTitle = cutString;
		
		// HTML 코드 제거
		Pattern SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>",Pattern.DOTALL);   
	    Pattern STYLE = Pattern.compile("<style[^>]*>.*</style>",Pattern.DOTALL);   
	    Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");   
	    //Pattern nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s*>");   
	    Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");   
	    Pattern WHITESPACE = Pattern.compile("\\s\\s+");   
		
	    Matcher m;
	    
	    m = SCRIPTS.matcher(cutTitle);   
	    cutTitle = m.replaceAll("");   
	    m = STYLE.matcher(cutTitle);   
	    cutTitle = m.replaceAll("");   
	    m = TAGS.matcher(cutTitle);   
	    cutTitle = m.replaceAll("");   
	    m = ENTITY_REFS.matcher(cutTitle);   
	    cutTitle = m.replaceAll("");   
	    m = WHITESPACE.matcher(cutTitle);   
	    cutTitle = m.replaceAll("");  
	    // .. 			 
		
		int oF = 0, oL = 0, rF = 0, rL = 0;
		int lengthPrev = 0;
		
		try {
			byte[] bytes = cutTitle.trim().getBytes("UTF-8"); // 바이트로 보관
			int j = 0;
			if (lengthPrev > 0){
				while (j < bytes.length){
					if((bytes[j] & 0x80) != 0){
						if(oF + 2 > lengthPrev){
							break;
						}
						oF += 2;
						rF += 3;
						j += 3;
					}else{
						if(oF + 1 > lengthPrev){
							break;
						}
						++oF;
						++rF;
						++j;
					}
				}
			}else{				
				j =rF;
				while (j < bytes.length){
					if ((bytes[j] & 0x80) != 0) {
						if (oL + 2 > cLength){
							break;
						}
						oL += 2;
						rL += 3;
						j += 3;
					}else{
						if (oL + 1 > cLength){
							break;
						}
						++oL;
						++rL;
						++j;
					}
			
				}				
			}
			
		    cutTitle = new String(bytes, rF, rL, "UTF-8"); // charset Option
		    
		    // 강조 표시
		    if(strongString.isEmpty() == false) {
		    	String replacement = "<strong>" + strongString + "</strong>";
		    	cutTitle = cutTitle.replaceAll(strongString, replacement);
		    }
		    
		    // 말줄임 표시
		    byte[] bytes2 = cutTitle.getBytes("UTF-8");			
			if (bytes.length > bytes2.length) {
				cutTitle = cutTitle + suffix;
			}
			//System.out.println(">>> rF : " + rF + " => rL " + rL +  "=> lengthPrev : " + lengthPrev + " => cLength : " + cLength + " => cutTitle : " + cutTitle + " => bytes.length : " + bytes.length + " => bytes2.length : " + bytes2.length);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cutTitle;
	}

	public String getCutString() {
		return cutString;
	}

	public void setCutString(String cutString) {
		this.cutString = cutString;
	}

	public String getCutLength() {
		return cutLength;
	}

	public void setCutLength(String cutLength) {
		this.cutLength = cutLength;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getStrongString() {
		return strongString;
	}

	public void setStrongString(String strongString) {
		this.strongString = strongString;
	}	
}
