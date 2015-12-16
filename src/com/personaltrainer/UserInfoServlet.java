package com.personaltrainer;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
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
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.appengine.repackaged.com.google.api.client.http.HttpTransport;
import com.google.appengine.repackaged.com.google.api.client.http.javanet.NetHttpTransport;
import com.google.appengine.repackaged.com.google.api.client.json.jackson.JacksonFactory;

public class UserInfoServlet extends HttpServlet {
	
	public final static String USERNAME_KEY = "userName";
	public final static String USEREMAIL_KEY = "userEmail";
	public final static String CLIENT_ID = "275970790590-p5mmctk01ejdvt4fs2kprqfg561i9dio.apps.googleusercontent.com";
	private static final String APPS_DOMAIN_NAME = "http://localhost:8888";
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Map props = new HashMap();
		props.put(GCacheFactory.EXPIRATION_DELTA, 3600);
		props.put(MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT, true);	
		Cache cache = null;
		try {
			cache = CacheManager.getInstance().getCacheFactory().createCache(props);
		} catch (CacheException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String idTokenString = req.getParameter("id_token");
		
		HttpTransport transport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
	    .setAudience(Arrays.asList(CLIENT_ID))
	    .build();

		GoogleIdToken idToken=null;
		try {
			idToken = verifier.verify(idTokenString);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (idToken != null) {
		  Payload payload = idToken.getPayload();
		  if (payload.getEmailVerified()){
		    cache.put(USERNAME_KEY, payload.get("name"));
		    cache.put(USEREMAIL_KEY, payload.getEmail());
		    
		    resp.getWriter().write(cache.get(USERNAME_KEY) + "\n" + cache.get(USEREMAIL_KEY));
		    
		  } 
		  else {
		    System.out.println("Invalid ID token.");
		  }
		} else {
		  System.out.println("Invalid ID token.");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map props = new HashMap();
		props.put(GCacheFactory.EXPIRATION_DELTA, 3600);
		props.put(MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT, true);	
		Cache cache = null;
		try {
			cache = CacheManager.getInstance().getCacheFactory().createCache(props);
		} catch (CacheException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		resp.getWriter().write(cache.get(USERNAME_KEY) + "\n" + cache.get(USEREMAIL_KEY));
	}

}
