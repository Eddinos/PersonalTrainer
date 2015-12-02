package com.personaltrainer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class PresentationTextServlet extends HttpServlet {
	public static final String PRESMSG_ENTITY_KEY = "PresentationMessage";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity presentationMsg = new Entity(PRESMSG_ENTITY_KEY);
		presentationMsg.setProperty("message", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc scelerisque turpis quis mauris volutpat maximus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eu sapien quis lorem ultrices fermentum. Donec quis purus congue, ullamcorper ligula a, lobortis mi. Ut porta neque in mi cursus, sed ultricies velit venenatis. Curabitur rhoncus tortor vulputate, dapibus mi at, dignissim turpis. Donec porta auctor neque et tristique. Integer nec libero nec purus efficitur venenatis ut a metus. Donec purus metus, sollicitudin faucibus venenatis vitae, volutpat ac justo. Donec interdum arcu id ullamcorper semper. Vestibulum porttitor non urna vel commodo. Sed ac mauris congue, porttitor quam sit amet, imperdiet lacus. Etiam congue et justo sit amet semper. ");
		
		datastore.put(presentationMsg);
		
	}
	
	
}
