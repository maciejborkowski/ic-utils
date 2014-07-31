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

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.plugin.MojoExecutionException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
/**
 * @author nosal
 */
public class Main {
    public static void main(String[] args) {
        try {
            CommandLineValues values = new CommandLineValues(args);
            CmdLineParser parser = new CmdLineParser(values);
            File output = new File("sourcecrawler.xml");
            File source = values.getSource();
            if (values.getOutput()!=null && !values.getOutput().exists()) {
                output = values.getOutput();
            }
            if(values.getOutput()!=null && values.getOutput().isDirectory()){
                output = new File(values.getOutput().getAbsoluteFile()+"sourcecrawler.xml");
            }
            SourceCrawlerScanner sourceCrawler = new SourceCrawlerScanner();
            sourceCrawler.execute(source.getPath(), output.getPath());

            parser.parseArgument(args);
        } catch (MojoExecutionException | CmdLineException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
}
