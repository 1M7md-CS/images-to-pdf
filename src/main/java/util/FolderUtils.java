package util;

import cli.ConsoleColors;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FolderUtils {

	private static final int MAX_ATTEMPTS = 3;
	private static final String[] IMAGE_EXTENSIONS = {"jpg", "jpeg", "png", "gif", "bmp", "tiff", "jfif", "heic"};


	public static File[] getImagesFromFolder(File folder) throws IOException {
		if (folder == null || !folder.exists() || !folder.isDirectory()) {
			throw new IllegalArgumentException(ConsoleColors.ERROR_COLOR + "Invalid folder: " + (folder == null ? "null" : folder.getAbsolutePath())  + ConsoleColors.RESET);
		}

		File[] images = folder.listFiles((dir, name) -> {
			String lowerName = name.toLowerCase();
			return Arrays.stream(IMAGE_EXTENSIONS).anyMatch(extension -> lowerName.endsWith("." + extension));
		});

		if (images == null || images.length == 0) {
			throw new IOException(ConsoleColors.ERROR_COLOR + "No images found in folder: '" + folder.getAbsolutePath() + "'" + ConsoleColors.RESET);
		}

		return images;
	}

	public static String getFolderPath(BufferedReader reader) throws IOException {
		int attempts = 0;

		while (attempts < MAX_ATTEMPTS) {
			System.out.print(ConsoleColors.OUTPUT_COLOR + "Please enter folder path that contains the images: " + ConsoleColors.RESET);
			String folderPath = reader.readLine().trim();
			attempts++;

			if (folderPath.isEmpty()) {
				System.out.println(ConsoleColors.ERROR_COLOR + "Path cannot be empty. Please enter a valid path." + ConsoleColors.RESET);
				continue;
			}

			File folder = new File(folderPath);
			if (!folder.exists()) {
				System.out.println(ConsoleColors.ERROR_COLOR + "Folder does not exist: '" + folder.getPath() + "'" + ConsoleColors.RESET);
				continue;
			}
			if (!folder.isDirectory()) {
				System.out.println(ConsoleColors.ERROR_COLOR + "Path is not a directory: '" + folder.getPath() + "'" + ConsoleColors.RESET);
				continue;
			}

			return folderPath;
		}

		throw new IOException(ConsoleColors.ERROR_COLOR + "\nNo valid folder path provided after '" + MAX_ATTEMPTS + "' attempts." + ConsoleColors.RESET);
	}
}