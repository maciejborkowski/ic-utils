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

/**
 *
 * @author Paweł Nosal
 *
 */

import java.io.File;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FileDetector {

	private static final String NULL_POINTER_EXCEPTION = "Passed file should not be null";

	public List<File> getDirectoryFiles(File directory) {
		checkNotNull(directory, NULL_POINTER_EXCEPTION);
		FileHelper.checkIsDirectory(directory);
		return null;
	}

}
