package com.personaltrainer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
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
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

public class GetMessageServlet extends HttpServlet {
	public static final String MSG_KEY = "message";
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Map props = new HashMap();
		props.put(GCacheFactory.EXPIRATION_DELTA, 3600);
		props.put(MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT, true);		
		
		try {
			resp.setContentType("text/html");
			resp.getWriter().append(getWelcomeMsg(datastore, CacheManager.getInstance()
					.getCacheFactory().createCache(props)));
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getWelcomeMsg(DatastoreService datastore, Cache cache) {
		if (cache.get(MSG_KEY) != null) {
			return (String) cache.get(MSG_KEY);
		} else {
			Filter msgWelcomeFilter = new FilterPredicate(
					MSG_KEY,
					FilterOperator.NOT_EQUAL, null);

			Query q = new Query(PresentationTextServlet.PRESMSG_ENTITY_KEY)
					.setFilter(msgWelcomeFilter);

			PreparedQuery pq = datastore.prepare(q);

			String welcomeString = "";
			for (Entity result : pq.asIterable()) {
				welcomeString = (String) result.getProperty(MSG_KEY);
			}
			
			cache.put(MSG_KEY, welcomeString);

			return welcomeString;

		}
	}
}
