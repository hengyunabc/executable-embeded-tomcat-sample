package io.github.hengyunabc;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author hengyunabc
 *
 */
@WebServlet(name = "annotation", urlPatterns = { "/annotation" })
public class AnnotationServlet extends HttpServlet {
	private static final long serialVersionUID = -7327285584754507249L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.getWriter().append("aaa annotation").close();
	}
}