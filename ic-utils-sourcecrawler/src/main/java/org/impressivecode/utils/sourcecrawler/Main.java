/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.impressivecode.utils.sourcecrawler;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.plugin.MojoExecutionException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 *
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
            SourceCrawler sourceCrawler = new SourceCrawler();
            sourceCrawler.execute(source.getPath(), output.getPath());

            parser.parseArgument(args);
        } catch (MojoExecutionException | CmdLineException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
}
