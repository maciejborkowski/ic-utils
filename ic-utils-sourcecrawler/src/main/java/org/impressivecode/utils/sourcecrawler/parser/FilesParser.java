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

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.impressivecode.utils.sourcecrawler.model.JavaFile;

/**
 * 
 * @author Paweł Nosal
 * 
 */

public interface FilesParser {

	List<JavaFile> parseFiles(List<Path> javaPaths) throws IOException;

}
