package org.impressivecode.utils.sourcecrawler;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
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

public class FirstTest {
	@Test(enabled = false)
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
		Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "root" );
		for (JavaFile javaFile : parseFiles) {
			Element file = root.addElement("file");
			file.addElement("path").setText(javaFile.getFilePath());
			file.addElement("package").addText(javaFile.getPackageName());
			Element classesElement = file.addElement("classes");
			List<JavaClazz> classes = javaFile.getClasses();
			for (JavaClazz javaClazz : classes) {
				Element classElement = classesElement.addElement("class");
				classElement.addElement("type").setText(javaClazz.getClassType().getName());
				classElement.addElement("name").setText(javaClazz.getClassName());
				classElement.addElement("exception").setText(Boolean.toString(javaClazz.isException()));
				classElement.addElement("inner").setText(Boolean.toString(javaClazz.isInner()));
                classElement.addElement("test").setText(Boolean.toString(javaClazz.isTest()));
			}
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		 XMLWriter writer = new XMLWriter(
		            new FileWriter( "output.xml" ),format
		        );
		 
		        writer.write( document );
		        writer.close();

    }
}
