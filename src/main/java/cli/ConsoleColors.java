package cli;

public class ConsoleColors {
	public static final String RESET = "\u001B[0m";
	public static final String BOLD = "\u001B[1m";

	public static final String SKY_BLUE = "\u001B[38;5;110m";
	public static final String MINT_GREEN = "\u001B[38;5;151m";
	public static final String SOFT_LILAC = "\u001B[38;5;182m";
	public static final String AQUA = "\u001B[38;5;79m";
	public static final String WARM_BEIGE = "\u001B[38;5;223m";
	public static final String CRIMSON = "\u001B[38;5;161m";
	public static final String COOL_GRAY = "\u001B[38;5;244m";
	public static final String DARK_SLATE = "\u001B[38;5;239m";

	public static final String FRAME_COLOR = DARK_SLATE;
	public static final String TITLE_COLOR = SKY_BLUE + BOLD;
	public static final String OPTION_COLOR = SOFT_LILAC;
	public static final String INPUT_COLOR = AQUA;
	public static final String OUTPUT_COLOR = MINT_GREEN;
	public static final String ERROR_COLOR = CRIMSON + BOLD;
	public static final String EXIT_COLOR = WARM_BEIGE + BOLD;
	public static final String HELP_TEXT_COLOR = COOL_GRAY + BOLD;
	public static final String HELP_TITLE_COLOR = SKY_BLUE + BOLD;
}