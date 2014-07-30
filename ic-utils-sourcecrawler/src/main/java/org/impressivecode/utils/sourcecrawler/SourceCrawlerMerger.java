package org.impressivecode.utils.sourcecrawler;
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
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.impressivecode.utils.sourcecrawler.document.DocumentMerger;
import org.impressivecode.utils.sourcecrawler.document.XMLDocumentMergerImpl;

/**
 * Goal which merges scanner output
 * @phase package
 */
@Mojo(name = "merge")
public class SourceCrawlerMerger extends AbstractMojo {
	@Parameter(defaultValue = "${basedir}")
    private String directory;
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		String output = "sourcecrawler.xml";
		try {
			mergeXMLFiles(directory, output);
		} catch (IOException e) {
			throw new MojoExecutionException("IOException");
		}
	}
	
	void mergeXMLFiles(String directory, String output) throws IOException, MojoFailureException{
		DocumentMerger documentMerger = new XMLDocumentMergerImpl(directory, output);
		documentMerger.createMergedFile();
	}

}
