package com.agawrysiuk.docx4jprintimagestodocx;

import com.agawrysiuk.docx4jprintimagestodocx.downloader.Downloader;
import com.agawrysiuk.docx4jprintimagestodocx.printer.Docx4JPrinter;
import com.agawrysiuk.docx4jprintimagestodocx.service.DeckLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.model.structure.PageSizePaper;

import java.io.IOException;
import java.util.List;

@Slf4j
public class Docx4jPrintImagesToDocxApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
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
		System.exit(0);
	}

}
