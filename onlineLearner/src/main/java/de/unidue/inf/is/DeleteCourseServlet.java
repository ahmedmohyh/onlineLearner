package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.stores.KursStore;

public class DeleteCourseServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setAttribute("error", "Du hast den Kurs nicht erstellt!");
		request.getRequestDispatcher("/deleteFail.ftl").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int KID = Integer.parseInt(request.getParameter("ks"));
		System.out.println(KID);
		KursStore ks = new KursStore();
		Kurs k = ks.get_kurs(KID);
		
		System.out.println(k.getErsteller());
		if(k.getErsteller() != 1) {
			ks.close();
			doGet(request, response);
		} 
		else {
			ks.deleteCourse(KID);
			ks.complete();
			ks.close();
			HauptseiteServlet main =  new HauptseiteServlet();
			main.doGet(request, response);
		}
		
	}
}
