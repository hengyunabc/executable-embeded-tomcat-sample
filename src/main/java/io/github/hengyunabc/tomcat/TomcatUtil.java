package io.github.hengyunabc.tomcat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author hengyunabc
 *
 */
public abstract class TomcatUtil {

	public static File createTempDir(String prefix, int port) throws IOException {
		File tempDir = Files.createTempDirectory(prefix + "." + "." + port).toFile();
		tempDir.deleteOnExit();
		return tempDir;
	}
}
