package cli;

import service.ImageSorter;
import service.ImageToPdfConverter;
import util.FolderUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


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
			File[] images = FolderUtils.getImagesFromFolder(folder);

			if (choice.equalsIgnoreCase("withSorting")){
				File[] sortedImages = ImageSorter.sortImages(images);
				ImageToPdfConverter.convertToPDF(sortedImages, folderPath);
			}else if(choice.equalsIgnoreCase("withoutSorting")){
				ImageToPdfConverter.convertToPDF(images, folderPath);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

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

	private static void displayMenu() {
		String title = "Welcome to Images to PDF Converter";

		String[] options = {
				"1. Convert images to pdf with sorting",
				"2. Convert images to pdf without sorting",
				"3. Help",
				"4. Exit"};

		int width = title.length();
		for (String opt : options) {
			width = Math.max(width, opt.length());
		}

		printFrame(title, options, width);
	}

	private static void displayHelp() {

		String title = "Help - Images to PDF Converter";

		String[] lines = {
						   ConsoleColors.HELP_TEXT_COLOR + "This tool converts images to a PDF file.",
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
						   "- Output PDF is saved as 'output.pdf' in the input folder." + ConsoleColors.RESET
		};

		int maxLen = title.length();
		for (String line : lines) {
			maxLen = Math.max(maxLen, line.replaceAll("\u001B\\[[;\\d]*m", "").length());
		}
		maxLen = Math.max(maxLen, 40);

		System.out.println(ConsoleColors.FRAME_COLOR + "┌" + "─".repeat(maxLen + 4) + "┐" + ConsoleColors.RESET);
		System.out.println(ConsoleColors.FRAME_COLOR + "│ " + ConsoleColors.HELP_TITLE_COLOR
													 + centerText(title, maxLen + 2) + ConsoleColors.RESET
													 + " " + ConsoleColors.FRAME_COLOR + "│" + ConsoleColors.RESET);
		System.out.println(ConsoleColors.FRAME_COLOR + "├" + "─".repeat(maxLen + 4) + "┤" + ConsoleColors.RESET);

		for (String line : lines) {
			String plainLine = line.replaceAll("\u001B\\[[;\\d]*m", "");
			System.out.println(ConsoleColors.FRAME_COLOR + "│  "
							 + ConsoleColors.HELP_TEXT_COLOR + line
							 + ConsoleColors.RESET + " ".repeat(maxLen - plainLine.length())
							 + "  " + ConsoleColors.FRAME_COLOR + "│" + ConsoleColors.RESET);
		}

		System.out.println(ConsoleColors.FRAME_COLOR + "└" + "─".repeat(maxLen + 4) + "┘" + ConsoleColors.RESET);
	}

	private static void displayExitMessage() {
		String message = "Thank you for using the Images to PDF Converter.";
		int width = message.length();

		System.out.println(ConsoleColors.FRAME_COLOR + "╔" + "═".repeat(width + 4) + "╗" + ConsoleColors.RESET);
		System.out.println(ConsoleColors.FRAME_COLOR + "║  " + ConsoleColors.EXIT_COLOR + centerText(message, width)
						 + ConsoleColors.FRAME_COLOR + "  ║" + ConsoleColors.RESET);
		System.out.println(ConsoleColors.FRAME_COLOR + "╚" + "═".repeat(width + 4) + "╝" + ConsoleColors.RESET);
	}

	private static void printFrame(String title, String[] options, int width) {
		System.out.println(ConsoleColors.FRAME_COLOR + "╔" + "═".repeat(width + 6) + "╗" + ConsoleColors.RESET);
		System.out.println(ConsoleColors.FRAME_COLOR + "║" + ConsoleColors.RESET + "   "
						 + ConsoleColors.TITLE_COLOR + centerText(title, width) + ConsoleColors.RESET + "   "
						 + ConsoleColors.FRAME_COLOR + "║" + ConsoleColors.RESET);
		System.out.println(ConsoleColors.FRAME_COLOR + "╠" + "═".repeat(width + 6) + "╣" + ConsoleColors.RESET);

		for (String opt : options) {
			System.out.println(ConsoleColors.FRAME_COLOR + "║" + ConsoleColors.RESET + "   "
							 + ConsoleColors.OPTION_COLOR + opt + ConsoleColors.RESET
							 + " ".repeat(width - opt.length()) + "   "
							 + ConsoleColors.FRAME_COLOR + "║" + ConsoleColors.RESET);
		}

		System.out.println(ConsoleColors.FRAME_COLOR + "╚" + "═".repeat(width + 6) + "╝" + ConsoleColors.RESET);
	}

	private static String centerText(String text, int width) {
		if (text.length() >= width) {
			return text;
		}
		int padding = (width - text.length()) / 2;
		return " ".repeat(padding) + text + " ".repeat(width - padding - text.length());
	}

	private static void promptEnterKey() {
		try {
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