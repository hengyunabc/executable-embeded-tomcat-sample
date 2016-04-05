package io.github.hengyunabc;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceRoot.ResourceSetType;
import org.apache.catalina.webresources.StandardRoot;

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

	/**
	 * try to find WEB-INF path and add to tomcat context WebResourceRoot, if
	 * not found, return false.
	 * 
	 * @param context
	 * @param classLoader
	 * @return
	 */
	public static boolean addWebXmlResource(Context context, ClassLoader classLoader) {

		WebResourceRoot resourceRoot = context.getResources();
		if (resourceRoot == null) {
			resourceRoot = new StandardRoot(context);
			context.setResources(resourceRoot);
		}

		URL resource = classLoader.getResource("WEB-INF/web.xml");

		if (resource != null) {
			String webXmlUrlString = resource.toString();
			URL root;
			try {
				root = new URL(webXmlUrlString.substring(0, webXmlUrlString.length() - "WEB-INF/web.xml".length()));
				resourceRoot.createWebResourceSet(ResourceSetType.RESOURCE_JAR, "/WEB-INF", root, "/WEB-INF");
				return true;
			} catch (MalformedURLException e) {
				// ignore
			}
		}

		return false;
	}

	/**
	 * Add resources from the classpath.
	 */
	public static void addClasspathResources(ClassLoader classLoader, Context context) {
		if (classLoader instanceof URLClassLoader) {
			for (URL url : ((URLClassLoader) classLoader).getURLs()) {
				String file = url.getFile();
				if (file.endsWith(".jar") || file.endsWith(".jar!/")) {
					String jar = url.toString();
					if (!jar.startsWith("jar:")) {
						// A jar file in the file system. Convert to Jar URL.
						jar = "jar:" + jar + "!/";
					}
					addResourceSet(jar, context);
				} else if (url.toString().startsWith("file:")) {
					String dir = url.toString().substring("file:".length());
					if (new File(dir).isDirectory()) {
						addResourceSet(url.toString(), context);
					}
				}
			}
		}
	}

	private static void addResourceSet(String resource, Context context) {
		try {
			if (isInsideNestedJar(resource)) {
				// It's a nested jar but we now don't want the suffix because
				// Tomcat
				// is going to try and locate it as a root URL (not the resource
				// inside it)
				resource = resource.substring(0, resource.length() - 2);
			}
			URL url = new URL(resource);
			String path = "/META-INF/resources";
			context.getResources().createWebResourceSet(ResourceSetType.RESOURCE_JAR, "/", url, path);
		} catch (Exception ex) {
			// Ignore (probably not a directory)
		}
	}

	private static boolean isInsideNestedJar(String dir) {
		return dir.indexOf("!/") < dir.lastIndexOf("!/");
	}
}
