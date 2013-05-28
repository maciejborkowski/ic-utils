package org.impressivecode.utils.sourcecrawler.parser;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;
import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;

import java.nio.file.Path;
import java.util.List;

import org.impressivecode.utils.sourcecrawler.model.JavaFile;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FileParserTest {
	@Mock
	private SingleFileParser singleFileParser;
	private FilesParser filesParser;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		filesParser = new FilesParserImpl(singleFileParser);
	}

	@Test
	public void parseFileReturnListOfJavaFiles() {
		// given
		List<Path> javaPaths = newArrayList();
		// when
		List<JavaFile> javaFiles = filesParser.parseFiles(javaPaths);
		// then
		assertThat(javaFiles).isNotNull().isEmpty();
	}

	@Test
	public void parseFileShouldIterateOverAllFiles() throws Exception {
		// given
		List<Path> javaPaths = prepareFilesPaths();
		// when
		filesParser.parseFiles(javaPaths);
		// then
		verify(singleFileParser, atLeast(javaPaths.size())).parseFile(
				any(Path.class));
	}

	@Test
	public void parseFileShouldReturnCollectionWithSizeEqualToPathList()
			throws Exception {
		// given
		List<Path> javaPaths = prepareFilesPaths();
		// when
		List<JavaFile> files = filesParser.parseFiles(javaPaths);
		// then
		assertThat(files).isNotNull().isNotEmpty().hasSize(javaPaths.size());
	}

	@Test
	public void parseFileShouldReturnExceptionWhenListIsNull() throws Exception {
		// given
		List<Path> javaPaths = null;
		// when
		catchException(filesParser).parseFiles(javaPaths);
		// then
		assertThat(caughtException()).isInstanceOf(NullPointerException.class)
				.hasMessage("List of paths should not be null.");
	}

	private List<Path> prepareFilesPaths() {
		List<Path> javaPaths = newArrayList();
		javaPaths.add(mock(Path.class));
		javaPaths.add(mock(Path.class));
		javaPaths.add(mock(Path.class));
		javaPaths.add(mock(Path.class));
		return javaPaths;
	}
}
