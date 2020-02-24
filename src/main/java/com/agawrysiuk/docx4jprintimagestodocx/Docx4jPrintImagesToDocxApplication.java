package com.agawrysiuk.docx4jprintimagestodocx;

import com.agawrysiuk.docx4jprintimagestodocx.printer.Docx4JPrinter;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@SpringBootApplication
public class Docx4jPrintImagesToDocxApplication {

	public static void main(String[] args) {
		SpringApplication.run(Docx4jPrintImagesToDocxApplication.class, args);
	}

	@PostConstruct
	private void init() {
		Docx4JPrinter printer = Docx4JPrinter.builder()
				.pictureLink("images/c17-48-the-ur-dragon.jpg")
				.fileName("test-print.docx")
				.landscape(true)
				.maxWidth(3580)
				.build();
		try {
			printer.print();
		} catch (Exception e) {
			e.printStackTrace();
			log.warn("Can't print the picture!");
		}
	}

}
