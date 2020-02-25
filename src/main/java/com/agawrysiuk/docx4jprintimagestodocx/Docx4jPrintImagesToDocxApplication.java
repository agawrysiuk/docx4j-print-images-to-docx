package com.agawrysiuk.docx4jprintimagestodocx;

import com.agawrysiuk.docx4jprintimagestodocx.printer.Docx4JPrinter;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.model.structure.PageSizePaper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
public class Docx4jPrintImagesToDocxApplication {

	public static void main(String[] args) {
		SpringApplication.run(Docx4jPrintImagesToDocxApplication.class, args);
	}

	@PostConstruct
	private void init() {
		String[] arrayLink = new String[] {
				"https://img.scryfall.com/cards/large/front/1/9/192452f8-93c2-4a20-a52b-0093741a9e45.jpg?1562783094",
				"https://img.scryfall.com/cards/large/front/3/c/3c84cf70-4164-47ea-8da1-c0ca1ac132e1.jpg?1562611444"
		};
		Docx4JPrinter printer = Docx4JPrinter.builder()
				.internetLink(true)
				.pictureLinks(arrayLink)
				.fileName("test-print.docx")
				.landscape(true)
				.maxWidth(3600) //3580
				.pageSize(PageSizePaper.LETTER)
				.build();
		try {
			printer.print();
		} catch (Exception e) {
			e.printStackTrace();
			log.warn("Can't print the picture!");
		}
	}

}
