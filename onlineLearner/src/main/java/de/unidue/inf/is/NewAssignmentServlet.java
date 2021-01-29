package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Aufgabe;
import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.stores.AufgabeStore;
import de.unidue.inf.is.stores.KursStore;

public class NewAssignmentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Kurs k = new Kurs();
	Aufgabe a = new Aufgabe();
	KursStore ks;
	AufgabeStore as;
	int kid;
	int aNum;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		kid = Integer.parseInt(request.getParameter("kid"));
		aNum = Integer.parseInt(request.getParameter("anum"));
		
		ks = new KursStore();
		k = ks.get_kurs(kid);
		ks.complete();
		ks.close();
		
		as = new AufgabeStore();
		a = as.getAufgabe(kid, aNum);
		as.complete();
		as.close();
		
		request.setAttribute("kid", kid);
		request.setAttribute("aNum", aNum);
		request.setAttribute("kName", k.getName());
		request.setAttribute("aufName", a.getName());
		request.setAttribute("aufText", a.getBeschreibung());
		request.getRequestDispatcher("/newAssignment.ftl").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
