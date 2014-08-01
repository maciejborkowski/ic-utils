package org.impressivecode.utils.sourcecrawler.document;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.MojoFailureException;
import org.impressivecode.utils.sourcecrawler.files.FileHelper;
import org.impressivecode.utils.sourcecrawler.files.FileHelperImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DocumentMergerTest {
	private static final String EXAMPLE_DIRECTORY = "src/test/java/org/impressivecode/utils/sourcecrawler/document/";
	private static final String EXAMPLE_EXTENSION = ".xml";
	private static final String EXAMPLE_OUTPUT = "output";
	
	XMLDocumentMergerImpl merger;
	List<File> files;
	
	@BeforeMethod
	public void setUp() {
		merger = new XMLDocumentMergerImpl();
		files = merger.findFiles(EXAMPLE_DIRECTORY, EXAMPLE_EXTENSION);
	}
	
	@Test
	public void mergerFindsFiles() {
		assertEquals(files.size(), 2);
	}
	
	@Test
	public void mergerMergesXMLFiles() throws MojoFailureException, IOException {
		String output = EXAMPLE_DIRECTORY + EXAMPLE_OUTPUT + EXAMPLE_EXTENSION;
		merger.mergeXMLFiles(files, output);
		File outputFile = new File(output);
		FileHelper fh = new FileHelperImpl();
		int lines = fh.countLines(outputFile);
		assertEquals(lines, 49);
	}
}
