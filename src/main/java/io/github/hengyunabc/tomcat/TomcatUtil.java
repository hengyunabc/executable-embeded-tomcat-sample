package io.github.hengyunabc.tomcat;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author hengyunabc
 *
 */
public abstract class TomcatUtil {

	public static File createTempDir(String prefix, int port) throws IOException {
		File tempDir = File.createTempFile(prefix + ".", "." + port);
		tempDir.delete();
		tempDir.mkdir();
		tempDir.deleteOnExit();
		return tempDir;
	}
}
