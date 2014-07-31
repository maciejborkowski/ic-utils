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
package org.impressivecode.utils.sourcecrawler;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.impressivecode.utils.sourcecrawler.document.DocumentWriter;
import org.impressivecode.utils.sourcecrawler.document.XMLDocumentWriterImpl;
import org.impressivecode.utils.sourcecrawler.files.FileHelper;
import org.impressivecode.utils.sourcecrawler.files.FileHelperImpl;
import org.impressivecode.utils.sourcecrawler.files.FileProcessor;
import org.impressivecode.utils.sourcecrawler.files.FileProcessorImpl;
import org.impressivecode.utils.sourcecrawler.files.FileScanner;
import org.impressivecode.utils.sourcecrawler.files.FileScannerImpl;
import org.impressivecode.utils.sourcecrawler.model.JavaFile;
import org.impressivecode.utils.sourcecrawler.parser.FilesParser;
import org.impressivecode.utils.sourcecrawler.parser.FilesParserImpl;
import org.impressivecode.utils.sourcecrawler.parser.SourceParser;
import org.impressivecode.utils.sourcecrawler.parser.SourceParserImpl;

import com.thoughtworks.qdox.JavaProjectBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * Goal which touches a timestamp file.
 *
 * @phase process-sources
 */
@Mojo(name = "scan")
public class SourceCrawlerScanner extends AbstractMojo {
	@Parameter(defaultValue = "${basedir}")
    private String directory;
	
    public void execute() throws MojoExecutionException {
    	File file = new File(directory);
    	String directoryName = file.getName();
        String path = "sourcecrawler-" + directoryName + ".xml";
        execute(directory, path);
    }

    public void execute(String input, String output) throws MojoExecutionException {
        getLog().info("Start scan files.");
        try {
            File src = new File(input + "/src");
            if(src.isDirectory()){
                List<JavaFile> parsedFiles = prepareFileList(src.getCanonicalPath());
                writeXMLDocument(parsedFiles, output);
            }
        } catch(IOException e) {
    		getLog().error(e.getMessage());
    		throw new MojoExecutionException(e.getMessage());
        }
        getLog().info("Finish.");
    }
    
    public List<JavaFile> executeCleanly(final String input) throws FileNotFoundException, IOException{
		return prepareFileList(input);
    }
    
    public void writeXMLDocument(List<JavaFile> parsedFiles, final String output) throws IOException {
    		DocumentWriter writer = new XMLDocumentWriterImpl(output);
    		writer.write(parsedFiles);
    }
    
    private List<JavaFile> prepareFileList(final String filesToParse) throws IOException,
            FileNotFoundException {
        List<Path> scanDirectoryFiles = generateFileList(filesToParse);
        List<JavaFile> parseFiles = generateFilesListToParse(scanDirectoryFiles);
        return parseFiles;
    }

    private List<JavaFile> generateFilesListToParse(
            List<Path> scanDirectoryFiles) throws IOException {
    	JavaProjectBuilder builder = new JavaProjectBuilder();
        SourceParser sourceParser = new SourceParserImpl();
        FilesParser fileParser = new FilesParserImpl(builder, sourceParser);
        return fileParser.parseFiles(scanDirectoryFiles);
    }

    private List<Path> generateFileList(final String filesToParse) throws IOException {
    	Path path = Paths.get(filesToParse);
        FileHelper fileHelper = new FileHelperImpl();
        PathMatcher matcher = fileHelper.getPathMatcher("glob:*.java");
        FileScanner fileScanner = new FileScannerImpl(fileHelper);
        FileProcessor fileProcessor = new FileProcessorImpl(
                new ArrayList<Path>(), matcher);

        List<Path> scanDirectoryFiles = fileScanner.scanDirectoryFiles(path,
                fileProcessor);
        return scanDirectoryFiles;
    }
}
