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

import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;
import static org.fest.assertions.Assertions.*;
import static com.googlecode.catchexception.CatchException.*;

import java.io.File;
import java.util.List;

import org.testng.annotations.BeforeMethod;

public class DirectoryFilesScannerTest {
	private DirectoryFileScanner fileScanner;
	private File directory;

	@BeforeMethod
	public void beforeTest() {
		fileScanner = new DirectoryFileScannerImpl();
		directory = mock(File.class);
	}

	@Test
	public void getFilesByDirectoryShouldNotReturnNull() {
		// given
		prepareDirectoryToReturnEmptyArray();
		// when
		final List<File> filesList = fileScanner.getFilesByDirectory(directory);
		// then
		assertThat(filesList).isNotNull();
	}

	@Test
	public void getFilesByDirectoryShouldCheckThatGivenFileIsDirectory() {
		// given
		prepareDirectoryToReturnEmptyArray();
		// when
		fileScanner.getFilesByDirectory(directory);
		// then
		verify(directory).isDirectory();
	}

	@Test
	public void getFilesByDirectoryShouldThrowExceptionWhenFileIsNotDirectory() {
		// given
		assertNotNull(directory, "Directory should not be null");
		when(directory.isDirectory()).thenReturn(false);
		// when
		catchException(fileScanner).getFilesByDirectory(directory);
		// then
		assertThat(caughtException()).isInstanceOf(
				IllegalArgumentException.class).hasMessage(
				"given file should be a directory, but he wasn't");
	}

	@Test
	public void getFileByDirectoryShouldThrowExceptionWhenDirectoryIsNull() {
		// given
		directory = null;
		// when
		catchException(fileScanner).getFilesByDirectory(directory);
		// then
		assertThat(caughtException()).isInstanceOf(NullPointerException.class)
				.hasMessage("given file should not be null");
	}

	@Test
	public void getFileByDirectoryShouldListFilesFromDirectories() {
		// given
		prepareDirectoryToReturnEmptyArray();
		// when
		fileScanner.getFilesByDirectory(directory);
		// then
		verify(directory).listFiles();
	}

	@Test
	public void getFilesByDirectoryShouldConvertArrayToList() {
		// given
		final File mockFile = prepareDirectoryToReturnFile();
		// when
		final List<File> filesByDirectory = fileScanner
				.getFilesByDirectory(directory);
		// then
		assertThat(filesByDirectory).isNotNull().isNotEmpty()
				.contains(mockFile);
	}

	@Test
	public void getFilesByDirectoryShouldReturnEmptyCollectionWhenDirectoryReturnNull()
			throws Exception {
		// given
		when(directory.isDirectory()).thenReturn(true);
		when(directory.listFiles()).thenReturn(null);
		// when
		final List<File> list = fileScanner.getFilesByDirectory(directory);
		// then
		assertThat(list).isNotNull().isEmpty();
	}

	private File prepareDirectoryToReturnFile() {
		when(directory.isDirectory()).thenReturn(true);
		final File mockFile = mock(File.class);
		when(mockFile.isDirectory()).thenReturn(true);
		final File[] files = { mockFile };
		when(directory.listFiles()).thenReturn(files);
		return mockFile;
	}

	private void prepareDirectoryToReturnEmptyArray() {
		when(directory.isDirectory()).thenReturn(true);
		final File[] files = {};
		when(directory.listFiles()).thenReturn(files);
	}
}
