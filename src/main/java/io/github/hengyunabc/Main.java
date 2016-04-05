package io.github.hengyunabc;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * 
 * @author hengyunabc
 *
 */
public class Main {

	public static void main(String[] args) throws ServletException, LifecycleException, IOException {

		String hostName = "localhost";
		int port = 8080;
		String contextPath = "";

		String tomcatBaseDir = TomcatUtil.createTempDir("tomcat", port).getAbsolutePath();
		String contextDocBase = TomcatUtil.createTempDir("tomcat-docBase", port).getAbsolutePath();

		Tomcat tomcat = new Tomcat();
		tomcat.setBaseDir(tomcatBaseDir);

		tomcat.setPort(port);
		tomcat.setHostname(hostName);

		Context context = tomcat.addWebapp(contextPath, contextDocBase);

		ClassLoader classLoader = Main.class.getClassLoader();

		// add /WEB-INF path to Tomcat WebResourceRoot
		TomcatUtil.addWebXmlResource(context, classLoader);
		// add all /META-INF/resources paths to Tomcat WebResourceRoot
		TomcatUtil.addClasspathResources(classLoader, context);

		context.setParentClassLoader(classLoader);

		tomcat.start();
		tomcat.getServer().await();
	}
}
