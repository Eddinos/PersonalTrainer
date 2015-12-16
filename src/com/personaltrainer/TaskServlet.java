package com.personaltrainer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class TaskServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Queue queue = QueueFactory.getQueue("addPlanQueue");
		TaskOptions task = TaskOptions.Builder.withUrl("/trainingplan").param("trainingPlan", req.getParameter("trainingPlan"));
		queue.add(task);
	}
}
