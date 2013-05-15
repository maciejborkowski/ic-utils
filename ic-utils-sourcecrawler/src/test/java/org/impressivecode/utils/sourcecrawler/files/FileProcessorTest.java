package org.impressivecode.utils.sourcecrawler.files;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class FileProcessorTest {
	private FileProcessor fileProcessor;
	@Mock
	private Path pathMock;
	@Mock
	private BasicFileAttributes basicFileAttributesMock;
	@Mock
	private List<Path> filesPathMock;
	@Mock
	private PathMatcher matcherMock;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		fileProcessor = new FileProcessorImpl(filesPathMock, matcherMock);
	}

	@Test
	public void visitFilesShouldReturnContinue() throws IOException {
		// given

		// when
		FileVisitResult result = fileProcessor.visitFile(pathMock,
				basicFileAttributesMock);
		// then
		assertEquals(result, FileVisitResult.CONTINUE,
				"Result should be continue");
	}

	@Test
	public void visitFilesShouldAddPathToList() throws Exception {
		// given
		when(pathMock.getFileName()).thenReturn(pathMock);
		when(basicFileAttributesMock.isRegularFile()).thenReturn(true);
		when(matcherMock.matches(pathMock)).thenReturn(true);
		// when
		fileProcessor.visitFile(pathMock, basicFileAttributesMock);
		// then
		verify(filesPathMock).add(pathMock);
	}

	@Test
	public void visitFilesShouldCheckFileNameIsNotNull() throws Exception {
		// given
		when(pathMock.getFileName()).thenReturn(null);
		// when
		fileProcessor.visitFile(pathMock, basicFileAttributesMock);
		// then
		verifyZeroInteractions(filesPathMock);
	}

	@Test
	public void visitFilesShouldCheckFileIsRegular() throws Exception {
		// given
		when(pathMock.getFileName()).thenReturn(pathMock);
		when(basicFileAttributesMock.isRegularFile()).thenReturn(false);
		// when
		fileProcessor.visitFile(pathMock, basicFileAttributesMock);
		// then
		verifyZeroInteractions(filesPathMock);
	}

	@Test
	public void visitFilesShouldCheckFileNameMatchesToJava() throws Exception {
		// given
		when(pathMock.getFileName()).thenReturn(pathMock);
		when(basicFileAttributesMock.isRegularFile()).thenReturn(true);
		when(matcherMock.matches(pathMock)).thenReturn(false);
		// when
		fileProcessor.visitFile(pathMock, basicFileAttributesMock);
		// then
		verifyZeroInteractions(filesPathMock);
	}
}
