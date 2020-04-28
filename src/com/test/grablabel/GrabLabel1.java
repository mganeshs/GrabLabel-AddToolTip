package com.test.grablabel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrabLabel1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String testHtml = "xx <tag a =\"b\" c=  \'d\' e=f ngtooltip=\"last modified by\"> contentssss </tag> zz <tag a=\"axt\">hello world </tag>";
		Pattern tagPattern = Pattern.compile("<(\\S+?)(.*?)>(.*?)</\\1>");
		Pattern attValueDoubleQuoteOnly = Pattern.compile("(\\w+)=\"(.*?)\"");
		Pattern attValueAll = Pattern.compile("([\\w:\\-]+)(\\s*=\\s*(\"(.*?)\"|'(.*?)'|([^ ]*))|(\\s+|\\z))");
		Matcher m = tagPattern.matcher(testHtml);

		boolean tagFound = m.find(); // true
		String tagOnly = m.group(0);// <tag a ="b" c= 'd' e=f> contentssss </tag>
		String tagname = m.group(1);// tag
		String attributes = m.group(2);// a ="b" c= 'd' e=f
		String content = m.group(3);// contentssss
		System.out.println("Tag Only   : " + tagOnly);
		System.out.println("Tag Name   : " + tagname);
		System.out.println("Attributes : " + attributes);
		System.out.println("Content    : " + content);
		// m = attValueDoubleQuoteOnly.matcher(attributes);
		 m = attValueAll.matcher(attributes);
		while (m.find()) {
			System.out.println(" >> " + m.group(1));
		}

	}
	public void readAndGrabLabelValue(String fileName) {
		String fileContent = readFileAsString( fileName);
		//Pattern tagPattern = Pattern.compile("<(l+?)(.*?)>(.*?)</\\1>");
		Pattern tagPattern = Pattern.compile("<(l+?.*>)(.*?)>(\\n.*?)<");
		Pattern attValueAll = Pattern.compile("([\\w:\\-]+)(\\s*=\\s*(\"(.*?)\"|'(.*?)'|([^ ]*))|(\\s+|\\z))");
		Matcher m = tagPattern.matcher(fileContent);
		if ( fileName.endsWith("product-detail.component.html"))
			System.out.println("************************** Product Detail component");
		while ( m.find()) {
			if ( m.group(1).startsWith("label")) {
				//check if label value is greater than 8 lenght
				String labelString = m.group(3);
				if (labelString.length() >= 8) {
					//check if no tooltip is added
					boolean ngToolTipAdded = false;
					String attributes = m.group(2);
					Matcher attributeMatcher = attValueAll.matcher(attributes);
					while ( attributeMatcher.find()) {
						if ( attributeMatcher.group(1).equals("ngbtooltip"))
								ngToolTipAdded = true;
					}
					if ( ngToolTipAdded != true) {
						System.out.println(fileName + " ---- "+labelString);	
					}
					
				}
			}
		}
	}
	
	public String readFileAsString(String fileName) {
		String data = "";
		try {
			data = new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

}
