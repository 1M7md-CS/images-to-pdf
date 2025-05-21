package cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import service.ImageSorter;
import service.ImageToPdfConverter;
import util.FolderUtils;


public abstract class CLIHandler {

	private static final int OPTION_CONVERT_WITH_SORTING = 1;
	private static final int OPTION_CONVERT_WITHOUT_SORTING = 2;
	private static final int OPTION_HELP = 3;
	private static final int OPTION_EXIT = 4;


	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static boolean isRunning = true;
	private static final int MAX_ATTEMPTS = 3;


	public static void run() {

		while (isRunning) {

			displayMenu();

			try {
				handleUserChoice(getUserChoice());
			} catch (IOException e) {
				System.out.println(ConsoleColors.ERROR_COLOR + e.getMessage() + ConsoleColors.RESET);
			}

			if (isRunning) {
				System.out.println();
				promptEnterKey();
			}

		}

		displayExitMessage();
		closeBufferedReader();

	}

	private static void displayMenu() {
		String title = "Welcome to Images to PDF Converter";

		String[] options = {
				"1. Convert images to pdf with sorting",
				"2. Convert images to pdf without sorting",
				"3. Help",
				"4. Exit"};

		int longestLineLength = getLongestLineLength(title,options);
		Frame.printMenuFrame(title,options,longestLineLength,2);
	}

	private static int getUserChoice() throws IOException {
		int attempts = 0;

		while (attempts < MAX_ATTEMPTS) {
			System.out.print(ConsoleColors.INPUT_COLOR + "Choose an option (1-4): " + ConsoleColors.RESET);
			attempts++;

			try {
				String input = reader.readLine().trim();
				if (input.isEmpty()) {
					System.out.println(ConsoleColors.ERROR_COLOR + "Input cannot be empty. Please enter a number from 1 to 4." + ConsoleColors.RESET);
					continue;
				}

				int choice = Integer.parseInt(input);

				if (choice >= 1 && choice <= 4) {
					return choice;
				} else {
					System.out.println(ConsoleColors.ERROR_COLOR + "Invalid option. Please enter a number from 1 to 4." + ConsoleColors.RESET);
				}

			} catch (NumberFormatException | IOException e) {
				System.out.println(ConsoleColors.ERROR_COLOR + "Invalid input. Please enter a valid number." + ConsoleColors.RESET);
			}
		}
		throw new IOException(ConsoleColors.ERROR_COLOR + "\nNo valid input provided after '" + MAX_ATTEMPTS + "' attempts." + ConsoleColors.RESET);
	}

	private static void handleUserChoice(int choice) {

		try {
			switch (choice) {

				case OPTION_CONVERT_WITH_SORTING -> handleConvert("withSorting");
				case OPTION_CONVERT_WITHOUT_SORTING -> handleConvert("withoutSorting");
				case OPTION_HELP -> displayHelp();
				case OPTION_EXIT -> isRunning = false;
				default -> System.out.println(ConsoleColors.ERROR_COLOR + "Invalid option. Please enter a number from 1 to 4." + ConsoleColors.RESET);
			}

		} catch (IllegalArgumentException e) {
			System.out.println(ConsoleColors.ERROR_COLOR + e.getMessage() + ConsoleColors.RESET);
		}
	}

	private static void handleConvert(String choice){

		try {

			String folderPath = FolderUtils.getFolderPath(reader);
			File folder = new File(folderPath);
			System.out.print(ConsoleColors.OUTPUT_COLOR + "Please enter the name of PDF file: " + ConsoleColors.RESET);
			String pdfName = reader.readLine();
			File[] images = FolderUtils.getImagesFromFolder(folder);

			if (choice.equalsIgnoreCase("withSorting")){
				File[] sortedImages = ImageSorter.sortImages(images);
				ImageToPdfConverter.convertToPDF(sortedImages, folderPath, pdfName);
			}else if(choice.equalsIgnoreCase("withoutSorting")){
				ImageToPdfConverter.convertToPDF(images, folderPath, pdfName);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	private static void displayHelp() {

		String title = "Help - Images to PDF Converter";

		String[] lines = {
				"This tool converts images to a PDF file.",
				"",
				"Options:",
				"1. Convert with sorting: Sorts images by numeric image name (e.g., 1.jpg, 2.png).",
				"2. Convert without sorting: Uses images in default order.",
				"3. Help: Displays this help message.",
				"4. Exit: Closes the application.",
				"",
				"Usage:",
				"- Enter a valid folder path containing images.",
				"- Supported formats: jpg, jpeg, png, gif, bmp, tiff, jfif, heic.",
				"- For sorting, use numeric image names (e.g., 1.jpg, 2.jpg).",
				"- Output PDF is saved as 'output.pdf' in the input folder."
		};

		int longestLineLength = getLongestLineLength(title,lines);
		Frame.printMenuFrame(title,lines,longestLineLength,1);


	}

	private static void displayExitMessage() {
		String exitMessage = "Thank you for using the Images to PDF Converter.";
		Frame.printExitFrame(exitMessage,6);

	}

	private static int getLongestLineLength(String title, String[] lines){

		int longestLineLength = title.length();

		for (String line : lines){
			longestLineLength = Math.max(longestLineLength, line.length());
		}

		return longestLineLength;

	}

	private static void promptEnterKey() {
		try {
			while (System.in.available() > 0) {
				System.in.read();
			}

			System.out.print(ConsoleColors.INPUT_COLOR + "Press ENTER to continue..." + ConsoleColors.RESET);
			reader.readLine();
		} catch (IOException e) {
			System.out.println(ConsoleColors.ERROR_COLOR + e.getMessage() + ConsoleColors.RESET);
		}
	}

	private static void closeBufferedReader() {
		try {
			reader.close();
		} catch (IOException e) {
			System.out.println(ConsoleColors.ERROR_COLOR + e.getMessage() + ConsoleColors.RESET);
		}
	}
}