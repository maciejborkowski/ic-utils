/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.impressivecode.utils.sourcecrawler;

import java.io.File;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 *
 * @author nosal
 */
public class CommandLineValues {

    @Option(name = "-i", aliases = {"--input"}, required = true,
            usage = "directory to scann")
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
