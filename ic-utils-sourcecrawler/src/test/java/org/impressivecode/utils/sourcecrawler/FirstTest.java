package org.impressivecode.utils.sourcecrawler;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.impressivecode.utils.sourcecrawler.files.FileHelper;
import org.impressivecode.utils.sourcecrawler.files.FileHelperImpl;
import org.impressivecode.utils.sourcecrawler.files.FileProcessor;
import org.impressivecode.utils.sourcecrawler.files.FileProcessorImpl;
import org.impressivecode.utils.sourcecrawler.files.FileScanner;
import org.impressivecode.utils.sourcecrawler.files.FileScannerImpl;
import org.impressivecode.utils.sourcecrawler.model.JavaClazz;
import org.impressivecode.utils.sourcecrawler.model.JavaFile;
import org.impressivecode.utils.sourcecrawler.parser.FilesParser;
import org.impressivecode.utils.sourcecrawler.parser.FilesParserImpl;
import org.impressivecode.utils.sourcecrawler.parser.SourceParser;
import org.impressivecode.utils.sourcecrawler.parser.SourceParserImpl;
import org.testng.annotations.Test;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaClassParent;
import com.thoughtworks.qdox.model.JavaSource;
import com.thoughtworks.qdox.model.Type;

public class FirstTest {
	@Test
	public void f() throws IOException {
		String ROOT = ".";
		Path path = Paths.get(ROOT);
		FileHelper fileHelper = new FileHelperImpl();
		PathMatcher matcher = fileHelper.getPathMatcher("glob:*.java");
		FileScanner fileScanner = new FileScannerImpl(fileHelper);
		List<Path> list = new ArrayList<Path>();
		FileProcessor fileProcessor = new FileProcessorImpl(list, matcher);
		List<Path> scanDirectoryFiles = fileScanner.scanDirectoryFiles(path, fileProcessor);
		JavaDocBuilder builder = new JavaDocBuilder();
		SourceParser sourceParser = new SourceParserImpl();
		FilesParser fileParser = new FilesParserImpl(builder, sourceParser);
		List<JavaFile> parseFiles = fileParser.parseFiles(scanDirectoryFiles);
		for (JavaFile javaFile : parseFiles) {
			System.out.println(javaFile.getPackageName());
			System.out.println(javaFile.getFilePath());
			List<JavaClazz> classes = javaFile.getClasses();
			for (JavaClazz javaClazz : classes) {
				System.out.println("  "+javaClazz.getClassName());
				System.out.println("  "+javaClazz.isException());
				System.out.println("  "+javaClazz.getClassType());
				System.out.println("  "+javaClazz.isInner());
				System.out.println();
			}
			System.out.println();
		}
		
	}
}
