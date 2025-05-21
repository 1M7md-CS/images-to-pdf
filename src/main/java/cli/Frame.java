package cli;

public class Frame {

	public static void printMenuFrame(String title, String[] lines, int longestLineLength, int frameStyle) {

		switch (frameStyle) {
			case 1  -> printMenuFrameStyle(title, lines, longestLineLength, '╒', '╕', '│', '╞', '╡', '╘', '╛', '─');
			case 2  -> printMenuFrameStyle(title, lines, longestLineLength, '┌', '┐', '│', '├', '┤', '└', '┘', '─');
			case 3  -> printMenuFrameStyle(title, lines, longestLineLength, '╔', '╗', '│', '╠', '╣', '╚', '╝', '═');
			case 4  -> printMenuFrameStyle(title, lines, longestLineLength, '╒', '╕', '║', '╞', '╡', '╘', '╛', '═');
			case 5  -> printMenuFrameStyle(title, lines, longestLineLength, '╔', '╗', '║', '╟', '╢', '╚', '╝', '─');
			case 6  -> printMenuFrameStyle(title, lines, longestLineLength, '╔', '╗', '║', '╠', '╣', '╚', '╝', '═');
			default -> printMenuFrameStyle(title, lines, longestLineLength, '╔', '╗', '║', '╠', '╣', '╚', '╝', '═');

		}

	}

	public static void printExitFrame(String exitMessage, int frameStyle) {
		switch (frameStyle) {
			case 1  -> printExitFrameStyle(exitMessage, exitMessage.length(), '╒', '╕', '│', '╘', '╛', '─');
			case 2  -> printExitFrameStyle(exitMessage, exitMessage.length(), '┌', '┐', '│', '└', '┘', '─');
			case 3  -> printExitFrameStyle(exitMessage, exitMessage.length(), '╔', '╗', '│', '╚', '╝', '═');
			case 4  -> printExitFrameStyle(exitMessage, exitMessage.length(), '╒', '╕', '║', '╘', '╛', '═');
			case 5  -> printExitFrameStyle(exitMessage, exitMessage.length(), '╔', '╗', '║', '╚', '╝', '─');
			case 6  -> printExitFrameStyle(exitMessage, exitMessage.length(), '╔', '╗', '║', '╚', '╝', '═');
			default -> printExitFrameStyle(exitMessage, exitMessage.length(), '╔', '╗', '║', '╚', '╝', '═');
		}
	}


	private static void printMenuFrameStyle(String title, String[] lines, int longestLineLength,
	                                        char topLeft, char topRight, char vertical,
	                                        char middleLeft, char middleRight,
	                                        char bottomLeft, char bottomRight, char horizontal) {

		String edge = "  ";

		String repeatedHorizontal = String.valueOf(horizontal).repeat(longestLineLength + 4);
		System.out.println(ConsoleColors.FRAME_COLOR + topLeft + repeatedHorizontal + topRight + ConsoleColors.RESET);


		String centeredTitle = centerText(title, longestLineLength);
		System.out.println(
				ConsoleColors.FRAME_COLOR + vertical + edge
						+ ConsoleColors.TITLE_COLOR + centeredTitle
						+ ConsoleColors.FRAME_COLOR + edge + vertical
						+ ConsoleColors.RESET
		);

		System.out.println(ConsoleColors.FRAME_COLOR + middleLeft + repeatedHorizontal + middleRight + ConsoleColors.RESET);

		for (String line : lines) {
			String repeatedBlank = " ".repeat(longestLineLength - line.length());
			System.out.println(
					ConsoleColors.FRAME_COLOR + vertical + edge
							+ ConsoleColors.OPTION_COLOR + line + repeatedBlank
							+ ConsoleColors.FRAME_COLOR + edge + vertical
							+ ConsoleColors.RESET
			);
		}

		System.out.println(ConsoleColors.FRAME_COLOR + bottomLeft + repeatedHorizontal + bottomRight + ConsoleColors.RESET);

	}

	private static void printExitFrameStyle(String exitMessage, int exitMessageLength,
	                                        char topLeft, char topRight, char vertical,
	                                        char bottomLeft, char bottomRight, char horizontal) {

		String edge = "  ";

		String repeatedHorizontal = String.valueOf(horizontal).repeat(exitMessageLength + 4);
		System.out.println(ConsoleColors.FRAME_COLOR + topLeft + repeatedHorizontal + topRight + ConsoleColors.RESET);

		String centeredExitMessage = centerText(exitMessage, exitMessageLength);
		System.out.println(ConsoleColors.FRAME_COLOR + vertical + edge
				+ ConsoleColors.EXIT_COLOR + centeredExitMessage
				+ ConsoleColors.FRAME_COLOR + edge + vertical
				+ ConsoleColors.RESET
		);

		System.out.println((ConsoleColors.FRAME_COLOR + bottomLeft + repeatedHorizontal + bottomRight + ConsoleColors.RESET));
	}



	private static String centerText(String text, int longestLineLength) {
		if (text.length() >= longestLineLength) {
			return text;
		}


		int midPoint = (longestLineLength - text.length()) / 2;
		return " ".repeat(midPoint) + text + " ".repeat(longestLineLength - midPoint - text.length());
	}

}
