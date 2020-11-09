package com.test.grablabel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Document.OutputSettings.Syntax;
import org.jsoup.nodes.Element;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

//version 0.9.1

public class GrabLabelUsingHTML {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stubgg
		String fileName = "D:\\src_repo\\svn\\Newport\\trunk\\moonshot\\1.0\\frontend\\src\\app\\fleet\\product\\product-detail\\product-detail.component.html";
		
		GrabLabelUsingHTML gl = new GrabLabelUsingHTML();
		gl.writeToFile("Hellow World",fileName,"src_repo");
		//gl.grabAndWrite(fileName);
	}
	public void grabAndWrite(String fileName, String outputFolderName) throws IOException {
		File input = new File(fileName);
		ParseSettings ps = new ParseSettings(true, true);
		
		Document doc = Jsoup.parse(input, "UTF-8");
		
		Elements labels = doc.select("label");
		for (Element label : labels) {
			String labelText = label.text();
			if (( labelText.length()>=12) && ( labelText.indexOf("{{")<0)){
				//check if attribute ngbtooltip available
				String tooltip = label.attr("ngbTooltip");
				if ( tooltip.length() < 1) {
					System.out.println(fileName + "-----" + labelText);
					label.attr(ps.normalizeAttribute("HW"), labelText);
					
				}
			}
			writeToFile(doc.html(),fileName,outputFolderName);
        }
		
	}
	public void writeToFile( String content, String filename,String outputFolderName) {
		//String newFileName = filename.replace(outputFolderName,outputFolderName+"1");
		String newFileName = filename;
		Path p = Paths.get(newFileName);
		
		try {
			Files.createDirectories(p.getParent());
			Files.write(p, content.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	public void grabLabelWithoutTooltip(String fileName,String outputFolderName) throws IOException {
		File input = new File(fileName);
		ParseSettings ps = ParseSettings.preserveCase;
		Parser parser = Parser.htmlParser();
		parser.settings(ps);
		InputStream targetStream = new FileInputStream(input);
		Document doc = Jsoup.parse(targetStream, "UTF-8","",parser);
		Elements labels = doc.select("label");
		for (Element label : labels) {
			String labelText = label.text();
			if (( labelText.length()>=12) && ( labelText.indexOf("{{")<0)){
				//check if attribute ngbtooltip available
				String tooltip = label.attr("ngbTooltip");
				if ( tooltip.length() < 1) {
					System.out.println(fileName + "-----" + labelText);
					label.attr("ngbTooltip", labelText);
					//placement="top" container="body" tooltipClass="normal"
					label.attr("placement","top");label.attr("container","body");
					label.attr("tooltipClass","normal");
					OutputSettings os = new OutputSettings();
					os.prettyPrint(true);
					os.outline(true);
					os.syntax(Syntax.html);
					os.indentAmount(4);
					doc.outputSettings(os);
					
					writeToFile(doc.body().html(), fileName, outputFolderName);
				}
			}
            
        }
	}

}
