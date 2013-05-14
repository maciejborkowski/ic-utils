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
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * 
 * @author Paweł Nosal
 * 
 */

public class FileProcessorImpl extends SimpleFileVisitor<Path> implements
		FileProcessor {
	private List<Path> filesPaths;
	private PathMatcher pathMatcher;

	public FileProcessorImpl(List<Path> filesPaths, PathMatcher matcher) {
		this.filesPaths = filesPaths;
		this.pathMatcher = matcher;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
		Path fileName = file.getFileName();
		if (fileName != null && pathMatcher.matches(fileName) && attrs.isRegularFile()) {
			filesPaths.add(file);
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public List<Path> getPaths() {
		return filesPaths;
	}

}
