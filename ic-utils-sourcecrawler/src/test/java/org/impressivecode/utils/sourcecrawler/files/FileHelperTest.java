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
import static com.googlecode.catchexception.CatchException.*;

import java.io.File;

import org.testng.annotations.Test;

public class FileHelperTest {

	@Test
	public void checkIsDirectoryShouldCheckFileIsNotDirectory()
			throws Exception {
		// given
		File file = mock(File.class);
		when(file.isDirectory()).thenReturn(true);
		// when
		FileHelper.checkIsDirectory(file);
		// then
		verify(file).isDirectory();
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void checkIsDirectoryShouldThrowExceptionWhenFileIsNotDirectory()
			throws Exception {
		// given
		File file = mock(File.class);
		when(file.isDirectory()).thenReturn(false);
		// when
		FileHelper.checkIsDirectory(file);
		// then

	}
}
