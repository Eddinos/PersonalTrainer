package com.personaltrainer;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class SearchServlet extends HttpServlet{
		DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			String searchInput = req.getParameter("searchInput");
			
			Filter trainingPlanFilter = new FilterPredicate(
					searchInput,
					FilterOperator.NOT_EQUAL, null);

			Query q = new Query(TrainingPlanServlet.TRAININGPLAN_ENTITY_KEY);
					//.setFilter(trainingPlanFilter);

			PreparedQuery pq = datastore.prepare(q);

			ArrayList<String> planMatch = new ArrayList<String>();
			for (Entity result : pq.asIterable()) {
				if(result.getProperty("plan").toString().toLowerCase().contains(searchInput.toLowerCase()))
				{
					planMatch.add((String) result.getProperty("plan"));
				}
				
			}
			
			for (String trainingPlan : planMatch)
			{
				JSONObject plan = null;
				try {
					plan = new JSONObject(trainingPlan);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(plan);
				System.out.println(trainingPlan);
			}
			
		}
}
