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
		fileScanner.scanDirectoryFiles(path, fileProcessor);
		assertThat(list).isNotEmpty();
		JavaDocBuilder builder = new JavaDocBuilder();
		for (Path path2 : list) {
			builder.addSource(path2.toFile());
		}
		
		JavaSource[] javaSources = builder.getSources();
		for (JavaSource javaSource : javaSources) {
			
			System.out.println(javaSource.getURL().getPath());
			JavaClass[] classes = javaSource.getClasses();
			for (JavaClass javaClass : classes) {
				System.out.println("    "+javaClass.getName());
				System.out.println("    "+javaClass.isInterface());
				System.out.println("    "+javaClass.isEnum());
				System.out.println("    "+javaClass.isInner());
				String string = new Throwable().getClass().getName();
				if(javaClass.getSuperJavaClass()!=null)
					System.out.println(javaClass.getSuperJavaClass().isA(string));
			}
			
		}
	}
}
