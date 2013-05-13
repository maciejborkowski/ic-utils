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

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;
import static org.fest.assertions.Assertions.*;
import static com.googlecode.catchexception.CatchException.*;

import java.io.File;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FileDetectorTest {
	private FileDetector fileDetector;

	@BeforeMethod
	public void beforeMethod() {
		fileDetector = new FileDetector();
	}

	@Test
	public void getFilesShouldCheckThatGivenFileIsNotNull() throws Exception {
		// given
		File directory = null;
		// when
		catchException(fileDetector).getDirectoryFiles(directory);
		// then
		assertThat(caughtException()).isInstanceOf(NullPointerException.class)
				.hasMessage("Passed file should not be null");
	}

	@Test
	public void getFilesShoulCheckThatGivenFileIsDirectory() throws Exception {
		// given
		File directory = mock(File.class);
		when(directory.isDirectory()).thenReturn(true);
		// when
		fileDetector.getDirectoryFiles(directory);
		// then
		verify(directory).isDirectory();
	}

	@Test
	public void getFilesShouldThrowExceptionWhenGivenFileIsNotDirectory()
			throws Exception {
		// given
		File directory = mock(File.class);
		when(directory.isDirectory()).thenReturn(false);
		// when
		catchException(fileDetector).getDirectoryFiles(directory);
		// then
		assertThat(caughtException()).isInstanceOf(
				IllegalArgumentException.class).hasMessage(
				"given file should be a directory, but he wasn't");
	}
}
