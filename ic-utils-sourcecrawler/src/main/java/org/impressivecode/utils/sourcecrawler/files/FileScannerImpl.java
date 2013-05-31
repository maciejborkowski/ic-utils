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
package org.impressivecode.utils.sourcecrawler.files;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.*;

/**
 * 
 * @author Paweł Nosal
 * 
 */

public class FileScannerImpl implements FileScanner {
	private final FileHelper fileHelper;
	public FileScannerImpl(final FileHelper fileHelper) {
		this.fileHelper = fileHelper;
	}

	@Override
	public List<Path> scanDirectoryFiles(final Path path, final FileProcessor fileProcessor) throws IOException {
		checkNotNull(path,"Path should not be null.");
		checkNotNull(fileProcessor, "FileProcessor should not be null.");
		checkIsDirectory(path);
		fileHelper.walkFiles(path, fileProcessor);
		return fileProcessor.getPaths();
	}

	private void checkIsDirectory(final Path path) {
		if(!fileHelper.isDirectory(path)){
			throw new IllegalArgumentException("File from path should be directory.");
		}
	}
	
}
