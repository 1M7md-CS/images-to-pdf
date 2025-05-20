package service;

import cli.ConsoleColors;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

public class ImageToPdfConverter {

	public static void convertToPDF(File[] images, String folderPath) {
		System.out.println(ConsoleColors.OUTPUT_COLOR + "Please Wait..." + ConsoleColors.RESET);

		String outputPath = folderPath + File.separator + "output.pdf";
		Document document = new Document(PageSize.A4, 10, 10, 10, 10);

		try {

			PdfWriter.getInstance(document, new FileOutputStream(outputPath));
			document.open();

			for (File imageFromFile : images) {

				Image image = Image.getInstance(imageFromFile.getAbsolutePath());
				float scale = Math.min(PageSize.A4.getWidth() / image.getWidth(),
						PageSize.A4.getHeight() / image.getHeight());
				image.scaleAbsolute(image.getWidth() * scale, image.getHeight() * scale);
				image.setAbsolutePosition(
						(PageSize.A4.getWidth() - image.getScaledWidth()) / 2,
						(PageSize.A4.getHeight() - image.getScaledHeight()) / 2
				);
				document.add(image);
				document.newPage();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			document.close();
		}

		System.out.println(ConsoleColors.OUTPUT_COLOR + "Done. (PDF saved in folder: '" + outputPath + "')" + ConsoleColors.RESET);
	}
}
