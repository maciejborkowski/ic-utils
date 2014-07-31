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
package org.impressivecode.utils.sourcecrawler.document;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.impressivecode.utils.sourcecrawler.model.JavaClazz;
import org.impressivecode.utils.sourcecrawler.model.JavaFile;

public class XMLDocumentWriterImpl implements DocumentWriter {
	private final String path;

	public XMLDocumentWriterImpl(String path) {
		this.path = path;
	}

	@Override
	public void write(List<JavaFile> parseFiles) throws IOException {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("root");
		writeFilesToDocument(parseFiles, root);
		writeDocument(document);
	}

	private void writeDocument(Document document) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileWriter(path), format);
		writer.write(document);
		writer.close();
	}

	private void writeFilesToDocument(List<JavaFile> parseFiles, Element root) {
		for (JavaFile javaFile : parseFiles) {
			Element file = root.addElement("file");
			Element classesElement = prepareFileElements(javaFile, file);
			List<JavaClazz> classes = javaFile.getClasses();
			writeClassesToFile(classesElement, classes);
		}
	}

	private Element prepareFileElements(JavaFile javaFile, Element file) {
		file.addElement("path").setText(javaFile.getFilePath());
		file.addElement("package").addText(javaFile.getPackageName());
		Element classesElement = file.addElement("classes");
		return classesElement;
	}

	private void writeClassesToFile(Element classesElement,
			List<JavaClazz> classes) {
		for (JavaClazz javaClazz : classes) {
			Element classElement = classesElement.addElement("class");
			classElement.addElement("name").setText(javaClazz.getClassName());
			classElement.addElement("access").setText(
					javaClazz.getClassAccess().getAccess());
			classElement.addElement("type").setText(
					javaClazz.getClassType().getName());
			classElement.addElement("exception").setText(
					Boolean.toString(javaClazz.isException()));
			classElement.addElement("inner").setText(
					Boolean.toString(javaClazz.isInner()));
            classElement.addElement("test").setText(
                    Boolean.toString(javaClazz.isTest()));
            classElement.addElement("final").setText(
                    Boolean.toString(javaClazz.isFinal()));
		}
	}

}
