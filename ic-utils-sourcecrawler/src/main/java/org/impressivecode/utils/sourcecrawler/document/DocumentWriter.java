package org.impressivecode.utils.sourcecrawler.document;

import java.io.IOException;
import java.util.List;

import org.impressivecode.utils.sourcecrawler.model.JavaFile;

public interface DocumentWriter {
	void write(List<JavaFile> parseFiles) throws IOException;
}
