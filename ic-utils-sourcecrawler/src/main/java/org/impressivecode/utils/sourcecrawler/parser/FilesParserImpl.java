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
package org.impressivecode.utils.sourcecrawler.parser;

import java.nio.file.Path;
import java.util.List;
import static com.google.common.collect.Lists.*;
import static com.google.common.base.Preconditions.*;
import org.impressivecode.utils.sourcecrawler.model.JavaFile;

/**
 * 
 * @author Paweł Nosal
 * 
 */

public class FilesParserImpl implements FilesParser {
	private SingleFileParser singleFileParser;

	public FilesParserImpl(SingleFileParser singleFileParser) {
		this.singleFileParser = singleFileParser;
	}

	@Override
	public List<JavaFile> parseFiles(List<Path> javaPaths) {
		checkNotNull(javaPaths, "List of paths should not be null.");
		List<JavaFile> javaFiles = iterateByPaths(javaPaths);
		return javaFiles;
	}

	private List<JavaFile> iterateByPaths(List<Path> javaPaths) {
		List<JavaFile> javaFiles = newArrayList();
		for (Path path : javaPaths) {
			JavaFile file = singleFileParser.parseFile(path);
			javaFiles.add(file);
		}
		return javaFiles;
	}
}
