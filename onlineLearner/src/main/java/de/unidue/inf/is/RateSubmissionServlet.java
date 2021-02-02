package de.unidue.inf.is;

import de.unidue.inf.is.domain.Aufgabe;
import de.unidue.inf.is.domain.Einreichen;
import de.unidue.inf.is.stores.AufgabeStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RateSubmissionServlet extends HttpServlet {
	Einreichen ei = new Einreichen();
	Aufgabe af = new Aufgabe();
	AufgabeStore as;
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int KID = Integer.parseInt(request.getParameter("kid"));
		as = new AufgabeStore();
		ei = as.get_random_einreichen(KID);
		af.setKid(KID);
		Aufgabe temp = new Aufgabe();
		temp = as.getAufgabe(KID , ei.getAnummer());
		String s = "";
		s=as.get_abgabe_text(ei.getAid());
		as.complete();
		as.close();
		
		af.setBeschreibung(temp.getBeschreibung());
		af.setName(temp.getName());
		af.setAbgabetext(s);
		af.setAID(ei.getAid());
		af.setBnummer(ei.getBnummer());
		if (af.getName().equals("")){
			String msg = "There are no Assignments to be rated in this course";
			request.setAttribute("error", msg);
			request.getRequestDispatcher("/Bewertungsversuch.ftl").forward(request, response);
		}
		else {
			request.setAttribute("Sub", af);
			//System.out.println("I was in do get");
			request.getRequestDispatcher("/RateSubmission.ftl").forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//	System.out.println("I was in do post");
		doGet(request, response);
	}

}
