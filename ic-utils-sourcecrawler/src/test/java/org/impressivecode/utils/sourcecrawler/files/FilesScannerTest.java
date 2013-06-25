package org.impressivecode.utils.sourcecrawler.files;

import static com.google.common.collect.Lists.newArrayList;
import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FilesScannerTest {
	private FileScanner fileScanner;
	private FileHelper fileHelperMock;
	private FileProcessor fileProcessorMock;
	private Path pathMock;

	@BeforeMethod
	public void beforeMethod() {
		fileHelperMock = mock(FileHelper.class);
		fileProcessorMock = mock(FileProcessor.class);
		pathMock = mock(Path.class);
		when(fileHelperMock.isDirectory(pathMock)).thenReturn(true);
	}

	@Test
	public void whenPathIsNotNullScanDirectoryFilesThrowNullPointerException()
			throws Exception {
		// given
		pathMock = null;
		fileScanner = new FileScannerImpl(fileHelperMock);
		// when
		catchException(fileScanner).scanDirectoryFiles(pathMock,
				fileProcessorMock);
		// then
		assertThat(caughtException()).isInstanceOf(NullPointerException.class)
				.hasMessage("Path should not be null.");
	}

	@Test
	public void scanDirectoryShouldInvokeIsDirectoryMethodFromFileHelper()
			throws Exception {
		// given
		fileScanner = new FileScannerImpl(fileHelperMock);
		// when
		fileScanner.scanDirectoryFiles(pathMock, fileProcessorMock);
		// then
		verify(fileHelperMock).isDirectory(pathMock);
	}

	@Test
	public void whenPathIsNotDirectoryScanDirectoryFilesShouldThrowIllegalArgumentException()
			throws Exception {
		// given
		pathMock = mock(Path.class);
		when(fileHelperMock.isDirectory(pathMock)).thenReturn(false);
		fileScanner = new FileScannerImpl(fileHelperMock);
		// when
		catchException(fileScanner).scanDirectoryFiles(pathMock,
				fileProcessorMock);
		// then
		assertThat(caughtException()).isInstanceOf(
				IllegalArgumentException.class).hasMessage(
				"File from path should be directory.");
	}

	@Test
	public void scanDirectoryShouldThrowNullPointerExceptionWhenFileProcessorIsNull()
			throws Exception {
		// given
		fileProcessorMock = null;
		fileScanner = new FileScannerImpl(fileHelperMock);
		// when
		catchException(fileScanner).scanDirectoryFiles(pathMock,
				fileProcessorMock);
		// then
		assertThat(caughtException()).isInstanceOf(NullPointerException.class)
				.hasMessage("FileProcessor should not be null.");
	}

	@Test
	public void scanDirectoryShouldInvokeWalkFilesFromFileHelper()
			throws Exception {
		// given
		fileScanner = new FileScannerImpl(fileHelperMock);
		// when
		fileScanner.scanDirectoryFiles(pathMock, fileProcessorMock);
		// then
		verify(fileHelperMock).walkFiles(pathMock, fileProcessorMock);
	}

	@Test
	public void scanDirectoryShouldReturnListOfFiles()
			throws Exception {
		// given
		ArrayList<Path> arrayList = newArrayList();
		fileScanner = new FileScannerImpl(fileHelperMock);
		when(fileProcessorMock.getPaths()).thenReturn(arrayList);
		// when
		List<Path> paths = fileScanner.scanDirectoryFiles(pathMock, fileProcessorMock);
		// then
		assertThat(paths).isSameAs(arrayList);
	}
}
