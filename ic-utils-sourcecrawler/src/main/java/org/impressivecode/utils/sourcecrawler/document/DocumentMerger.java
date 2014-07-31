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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.apache.maven.plugin.MojoFailureException;

/**
 * @author Maciej Borkowski, Capgemini Poland
 */
public abstract class DocumentMerger {
	public abstract void createMergedFile(final String directory, final String output) throws IOException, MojoFailureException;
	
	protected void removeFiles(final List<File> files) {
		for(File file : files) {
			try {
				file.delete();
			} catch(SecurityException e) {
				Logger.getLogger("FileExceptions").severe("CANNOT REMOVE FILE" + file.getName());
			}
		}
	}
	
	protected List<File> findFiles(final String directory, final String extension) {
		File dir = new File(directory);
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.startsWith("sourcecrawler-") && name.endsWith(extension)) {
					return true;
				} else {
					return false;
				}
			}
		};
		File[] result = dir.listFiles(filter);
		return Arrays.asList(result);
	}
	
	protected void copyFileLines(final File input, final BufferedWriter writer, final int fromLine, final int toLine)
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
			Logger.getLogger("MergeExceptions").severe("Could not parse file " + input.getName());
			throw new IOException("Could not parse file " + input.getName());
		}
	}
}
