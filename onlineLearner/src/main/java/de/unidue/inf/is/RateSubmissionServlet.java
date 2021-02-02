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
		if (af.getName().equals("")){
			String msg = "There are no Assignments to be rated in this course";
			request.setAttribute("error", msg);
			request.getRequestDispatcher("/Bewertungsversuch.ftl").forward(request, response);
		}
		else {
			request.setAttribute("Sub", af);
			request.getRequestDispatcher("/RateSubmission.ftl").forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int KID = Integer.parseInt(request.getParameter("kid"));
		as = new AufgabeStore();
		ei = as.get_random_einreichen(KID);
		String s = "";
		s=as.get_abgabe_text(ei.getAid());
		
		af = as.getAufgabe(KID, ei.getAnummer());
		af.setAbgabetext(s);
		as.complete();
		as.close();
		doGet(request, response);		
	}

}
