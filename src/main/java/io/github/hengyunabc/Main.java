package io.github.hengyunabc;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import io.github.hengyunabc.tomcat.EmbededContextConfig;
import io.github.hengyunabc.tomcat.EmbededStandardJarScanner;
import io.github.hengyunabc.tomcat.TomcatUtil;

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

		Host host = tomcat.getHost();
		Context context = tomcat.addWebapp(host, contextPath, contextDocBase, new EmbededContextConfig());

		context.setJarScanner(new EmbededStandardJarScanner());

		ClassLoader classLoader = Main.class.getClassLoader();

		// add /WEB-INF path to Tomcat WebResourceRoot
		TomcatUtil.addWebXmlResource(context, classLoader);

		context.setParentClassLoader(classLoader);

		tomcat.start();
		tomcat.getServer().await();
	}
}
