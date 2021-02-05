package de.unidue.inf.is;

import java.io.IOException;

import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.stores.KursStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EinschreibCheckServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Kurs k = new Kurs();
	KursStore ks;
	String msg="";


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error", msg);
		request.getRequestDispatcher("/einschriebungsversuch.ftl").forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int KID = Integer.parseInt(request.getParameter("ks"));
		ks = new KursStore();
		k = ks.get_kurs(KID);
		try {
			if (ks.ist_eingeschrieben(KID)) {
				msg = "You are already registered for this course";
			} else if (k.getFreiePlaetze() <= 0) {
				msg = "All places are already taken try next semester!";
			} else if (k.getSchluessel() != null) {
				String kKey;
				kKey = request.getParameter("key");

				if (request.getParameter("key").equals("")) {
					msg = "You have to enter a key";
				} else if (!k.getSchluessel().equals(kKey)) {
					msg = "Incorrect key";
				} else {
					msg = "You are now registered";
				}
			} else {
				msg = "You are now registered";
			}

			if (msg.equals("You are now registered")) {
				try {
					ks.sich_einschreiben(k);
					ks.complete();
					ks.close();
					DetailseiteServlet kd = new DetailseiteServlet(k);
					kd.doPost(request, response);
				}catch (Exception e){
					e.printStackTrace();
				}
			} else {
				ks.close();
				doGet(request, response);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
