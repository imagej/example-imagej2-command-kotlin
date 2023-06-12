package com.mycompany.imagej;

import net.imagej.Dataset;
import net.imagej.ImageJ;

import java.io.File;
import java.io.IOException;

public class DatasetOpener {

	public static Dataset open(ImageJ imageJ) throws IOException {
		// ask the user for a file to open
		File file = imageJ.ui().chooseFile(null, "open");

		if (file != null)
			// load the dataset
			return imageJ.scifio().datasetIO().open(file.getPath());
		return null;
	}
}
