package service;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class ImageSorter {

	public static File[] sortImages(File[] images) {

		Arrays.sort(images, Comparator.comparing(file -> {

			try {
				String nameWithoutExtension = file.getName().substring(0, file.getName().lastIndexOf("."));
				return Integer.parseInt(nameWithoutExtension);
			} catch (NumberFormatException e) {
				return Integer.MAX_VALUE;
			}

		}));

		return images;
	}

}
