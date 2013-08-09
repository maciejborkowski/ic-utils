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

import com.thoughtworks.qdox.model.JavaSource;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

/**
 * 
 * @author Paweł Nosal
 * 
 */

public class FileHelperImpl implements FileHelper {

	@Override
	public boolean isDirectory(final Path path) {
		return Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS);
	}

	@Override
	public Path walkFiles(final Path path, final FileProcessor fileProcessor)
			throws IOException {
		return Files.walkFileTree(path, fileProcessor);
	}

	@Override
	public PathMatcher getPathMatcher(final String regExp) {
		return FileSystems.getDefault().getPathMatcher(regExp);
	}

    @Override
    public boolean isTest(JavaSource javaSource) {
        return javaSource.getURL().getPath().indexOf("/test/")>-1;
    }

}
