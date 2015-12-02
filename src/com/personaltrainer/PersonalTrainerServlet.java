package com.personaltrainer;
import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class PersonalTrainerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("<h1>Personal trainer</h1>");
	}
}
