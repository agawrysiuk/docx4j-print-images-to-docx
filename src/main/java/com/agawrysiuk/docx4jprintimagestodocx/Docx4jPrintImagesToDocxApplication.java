package com.agawrysiuk.docx4jprintimagestodocx;

import com.agawrysiuk.docx4jprintimagestodocx.downloader.Downloader;
import com.agawrysiuk.docx4jprintimagestodocx.printer.Docx4JPrinter;
import com.agawrysiuk.docx4jprintimagestodocx.service.DeckLoader;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.model.structure.PageSizePaper;

import java.io.IOException;
import java.util.List;

@Slf4j
public class Docx4jPrintImagesToDocxApplication {

	public static void main(String[] args) throws IOException {
		init();
	}

	private static void init() throws IOException {
		DeckLoader deckLoader = new DeckLoader(new Downloader());
		List<String> arrayLink = deckLoader.loadDeck();
		Docx4JPrinter printer = Docx4JPrinter.builder()
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
