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
package org.impressivecode.utils.sourcecrawler.document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import org.apache.maven.plugin.MojoFailureException;
import org.impressivecode.utils.sourcecrawler.files.FileHelper;
import org.impressivecode.utils.sourcecrawler.files.FileHelperImpl;

/**
 * @author Maciej Borkowski, Capgemini Poland
 */
public class XMLDocumentMergerImpl extends DocumentMerger {
	public XMLDocumentMergerImpl() {
	}

	@Override
	public void createMergedFile(final String directory, final String output)
			throws IOException, MojoFailureException {
		List<File> files = findFiles(directory, ".xml");
		mergeXMLFiles(files, output);
		removeFiles(files);
	}

	public void mergeXMLFiles(final List<File> files, final String output) throws IOException, MojoFailureException {
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
	
}
