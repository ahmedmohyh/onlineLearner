package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.stores.AufgabeStore;
import de.unidue.inf.is.stores.KursStore;


public class NewAssignmentCheckServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getRequestDispatcher("/newAssignmentError.ftl").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int bnummer = Integer.parseInt(request.getParameter("user"));
		int kid = Integer.parseInt(request.getParameter("kid"));
		int anummer = Integer.parseInt(request.getParameter("aNum"));
		String text = request.getParameter("abText");
		
		AufgabeStore as = new AufgabeStore();
		if(!as.checkAssignment(bnummer, kid, anummer)) {
			as.neueAbgabe(bnummer, kid, anummer, text);
			KursStore ks = new KursStore();
			Kurs k = ks.get_kurs(kid);
			ks.complete();
			ks.close();
			DetailseiteServlet dserv = new DetailseiteServlet(k);
			dserv.doPost(request, response);
		}
		else {
			as.close();
			doGet(request, response);
		}
	}

}
