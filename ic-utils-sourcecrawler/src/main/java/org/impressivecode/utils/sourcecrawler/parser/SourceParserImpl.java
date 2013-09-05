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
package org.impressivecode.utils.sourcecrawler.parser;

import java.util.List;

import org.impressivecode.utils.sourcecrawler.files.FileHelper;
import org.impressivecode.utils.sourcecrawler.files.FileHelperImpl;
import org.impressivecode.utils.sourcecrawler.model.ClazzType;
import org.impressivecode.utils.sourcecrawler.model.JavaClazz;
import org.impressivecode.utils.sourcecrawler.model.JavaFile;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

import static com.google.common.collect.Lists.newArrayList;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Paweł Nosal
 */
public class SourceParserImpl implements SourceParser {

    private String throwableClassName;
    private FileHelper fileHelper;

    public SourceParserImpl() {
        this.fileHelper = new FileHelperImpl();
        Throwable tmpThrowable = new Throwable();
        Class<? extends Throwable> throwableClass = tmpThrowable.getClass();
        throwableClassName = throwableClass.getName();
    }

    public SourceParserImpl(FileHelper fileHelper) {
        this();
        this.fileHelper = fileHelper;
    }

    @Override
    public JavaFile parseSource(JavaSource sourceToParse) {
        JavaFile javaFile = new JavaFile();
        try {
            setupPackage(sourceToParse, javaFile);

            setupPath(sourceToParse, javaFile);

            List<JavaClazz> parsedClasses = parseClasses(sourceToParse);
            javaFile.setClasses(parsedClasses);
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(SourceParserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return javaFile;
    }

    private List<JavaClazz> parseClasses(JavaSource sourceToParse) {
        JavaClass[] javaClasses = sourceToParse.getClasses();
        List<JavaClazz> parsedClasses = newArrayList();
        for (JavaClass javaClass : javaClasses) {
            JavaClazz analyzedClass = analyzeClass(javaClass);
            parsedClasses.add(analyzedClass);
        }
        return parsedClasses;
    }

    private void setupPath(JavaSource sourceToParse, JavaFile javaFile) throws URISyntaxException, IOException {
        if (sourceToParse.getURL() != null) {
            URI uri = sourceToParse.getURL().toURI();
            File file = new File(uri);
            javaFile.setFilePath(file.getCanonicalPath());
        }
    }

    private void setupPackage(JavaSource sourceToParse, JavaFile javaFile) {
        String packageName = sourceToParse.getPackage();
        javaFile.setPackageName(packageName);
    }

    @Override
    public JavaClazz analyzeClass(JavaClass javaClass) {
        JavaClazz javaClazz = checkClassType(javaClass);
        boolean isException = checkIsThrowable(javaClass);

        javaClazz.setException(isException);
        javaClazz.setClassName(javaClass.getName());
        javaClazz.setInner(javaClass.isInner());
        return javaClazz;
    }

    private JavaClazz checkClassType(JavaClass javaClass) {
        JavaClazz javaClazz = new JavaClazz();
        if (javaClass.isEnum()) {
            javaClazz.setClassType(ClazzType.ENUM);
        } else if (javaClass.isInterface()) {
            javaClazz.setClassType(ClazzType.INTERFACE);
        } else if (javaClass.isAbstract()) {
            javaClazz.setClassType(ClazzType.ABSTRACT);
        } else {
            javaClazz.setClassType(ClazzType.CLASS);
            boolean b = fileHelper.isTest(javaClass.getSource());
            javaClazz.setTest(b);
        }
        return javaClazz;
    }

    @Override
    public boolean checkIsThrowable(JavaClass javaClass) {
        boolean isThrowable = javaClass.isA(throwableClassName);
        return isThrowable;
    }

}
