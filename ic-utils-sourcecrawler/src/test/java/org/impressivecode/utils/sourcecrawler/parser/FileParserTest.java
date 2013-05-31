package org.impressivecode.utils.sourcecrawler.parser;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;
import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.impressivecode.utils.sourcecrawler.model.JavaFile;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaSource;

public class FileParserTest {
	@Mock
	private JavaDocBuilder javaDocBuilder;
	@Mock
	private Path path;
	private FilesParser filesParser;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		filesParser = new FilesParserImpl(javaDocBuilder);
		JavaSource[] javaSources = {};
		when(javaDocBuilder.getSources()).thenReturn(javaSources);
	}

	@Test
	public void parseFileReturnListOfJavaFiles() throws FileNotFoundException, IOException {
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
		verify(javaDocBuilder, atLeast(javaPaths.size())).addSource(
				any(File.class));
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
	
	@Test
	public void parseFileShouldConvertPathToFile() throws Exception {
		List<Path> javaPaths = prepareFilesPaths();
		// when
		List<JavaFile> files = filesParser.parseFiles(javaPaths);
		//then
		verify(path, atLeast(javaPaths.size())).toFile();
	}
	private List<Path> prepareFilesPaths() {
		List<Path> javaPaths = newArrayList();
		javaPaths.add(path);
		javaPaths.add(path);
		javaPaths.add(path);

		return javaPaths;
	}
}
