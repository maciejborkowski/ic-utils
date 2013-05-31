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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import static com.google.common.collect.Lists.*;
import static com.google.common.base.Preconditions.*;
import org.impressivecode.utils.sourcecrawler.model.JavaFile;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaSource;

/**
 * 
 * @author Paweł Nosal
 * 
 */

public class FilesParserImpl implements FilesParser {
	private JavaDocBuilder javaDocBuilder;

	public FilesParserImpl(JavaDocBuilder singleFileParser) {
		this.javaDocBuilder = singleFileParser;
	}

	@Override
	public List<JavaFile> parseFiles(List<Path> javaPaths) throws FileNotFoundException, IOException {
		checkNotNull(javaPaths, "List of paths should not be null.");
		List<JavaSource> javaSources = iterateByPaths(javaPaths);
		List<JavaFile> javaFiles = newArrayList(); 
		return javaFiles;
	}

	private List<JavaSource> iterateByPaths(List<Path> javaPaths)
			throws FileNotFoundException, IOException {
		for (Path path : javaPaths) {
			File fileToParse = path.toFile();
			javaDocBuilder.addSource(fileToParse);
		}
		List<JavaSource> javaFiles = newArrayList(javaDocBuilder.getSources());
		return javaFiles;
	}
}
