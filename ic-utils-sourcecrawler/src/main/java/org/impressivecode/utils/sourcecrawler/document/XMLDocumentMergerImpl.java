package org.impressivecode.utils.sourcecrawler.document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import org.apache.maven.plugin.MojoFailureException;
import org.impressivecode.utils.sourcecrawler.files.FileHelper;
import org.impressivecode.utils.sourcecrawler.files.FileHelperImpl;
/*
ImpressiveCode Depress Framework Source Crawler
Copyright (C) 2013 ImpressiveCode contributors

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
/**
 * @author Maciej Borkowski, Capgemini Poland
 */
public class XMLDocumentMergerImpl implements DocumentMerger {
	final String directory;
	final String output;
	
	public XMLDocumentMergerImpl(final String directory, final String output) {
		this.directory = directory;
		this.output = output;
	}

	@Override
	public void createMergedFile() throws IOException, MojoFailureException {
		List<File> files = findXMLFiles();
		mergeXMLFiles(files);
		removeFiles(files);
	}

	private List<File> findXMLFiles() {
		File dir = new File(directory);
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.startsWith("sourcecrawler") && name.endsWith(".xml")
						&& !name.equals("sourcecrawler.xml")) {
					return true;
				} else {
					return false;
				}
			}
		};
		File[] result = dir.listFiles(filter);
		return Arrays.asList(result);
	}
	
	private void mergeXMLFiles(final List<File> files) throws IOException, MojoFailureException {
		File outputFile = new File(output);
		FileHelper fh = new FileHelperImpl();
		outputFile.delete();
		BufferedWriter writer = new BufferedWriter(new FileWriter(output)); 
		File aFile;
		try {
			aFile = files.iterator().next();
		} catch(NoSuchElementException e) {
			Logger.getLogger("MergeExceptions").severe("NO FILES FOUND");
			throw new MojoFailureException("No files found");
		}
		copyFileLines(aFile, writer, 0, 3);
		for(File inputFile : files) {
			copyFileLines(inputFile, writer, 3, fh.countLines(inputFile) - 1);
		}
		copyFileLines(aFile, writer, fh.countLines(aFile) - 1, fh.countLines(aFile));
		writer.close();
	}
	
	private void copyFileLines(final File input, final BufferedWriter writer, final int fromLine, final int toLine)
			throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(input))) {
			for(int i = 0; i < fromLine; i++) {
				reader.readLine();
			}
		    for(int i = fromLine; i < toLine; i++) {
		        writer.write(reader.readLine());
		        writer.newLine();
		    }
		    reader.close();
		} catch (IOException e) {
			Logger.getLogger("MergeExceptions").severe("COULD NOT MERGE " + input.getName());
			throw new IOException("Could not parse file " + input.getName());
		}
	}
	
	private void removeFiles(final List<File> files) {
		for(File file : files) {
			try {
				file.delete();
			} catch(SecurityException e) {
				Logger.getLogger("MergeExceptions").severe("CANNOT REMOVE FILE" + file.getName());
			}
		}
	}
}
