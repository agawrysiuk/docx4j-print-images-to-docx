package com.agawrysiuk.docx4jprintimagestodocx;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.File;

@SpringBootApplication
public class Docx4jPrintImagesToDocxApplication {

	public static void main(String[] args) {
		SpringApplication.run(Docx4jPrintImagesToDocxApplication.class, args);
	}

	@PostConstruct
	private void init() throws Docx4JException {
		WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
		MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
		mainDocumentPart.addParagraphOfText("It's a test page!");
		File exportFile = new File("test.docx");
		wordPackage.save(exportFile);
	}

}
