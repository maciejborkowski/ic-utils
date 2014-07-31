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
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
/**
 * @author nosal
 */
public class CommandLineValues {
    @Option(name = "-i", aliases = {"--input"}, required = true,
            usage = "directory to scan")
    private File source;
    @Option(name = "-o", aliases = {"--output"}, required = false,
            usage = "output file where xml file will be saved")
    private File output;
    private boolean errorFree = false;

    public CommandLineValues(String... args) {
        CmdLineParser parser = new CmdLineParser(this);
        parser.setUsageWidth(80);
        try {
            parser.parseArgument(args);

            if (!getSource().isDirectory()) {
                throw new CmdLineException(parser,
                        "--input is not valid directory.");
            }

            errorFree = true;
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }

    public boolean isErrorFree() {
        return errorFree;
    }

    public File getSource() {
        return source;
    }

    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

}
