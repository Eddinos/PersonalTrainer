package com.personaltrainer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.util.ajax.JSON;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class TrainingPlanServlet extends HttpServlet{
	
	public static final String TRAININGPLAN_ENTITY_KEY = "trainingPlan";
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*JSONObject plan = null;
		try {
			plan = new JSONObject(req.getParameter("trainingPlan"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Entity trainginPlan = new Entity(TRAININGPLAN_ENTITY_KEY);
		String plan = req.getParameter("trainingPlan"); 
		trainginPlan.setProperty("plan", plan);
		datastore.put(trainginPlan);
		
	}
}
