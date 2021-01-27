package de.unidue.inf.is;

import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.domain.User;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import de.unidue.inf.is.stores.KursStore;
import de.unidue.inf.is.stores.UserStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Checkeinschreiben_servlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Kurs k = new Kurs();
	KursStore ks = new KursStore();
	String msg="";


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error", msg);
		request.getRequestDispatcher("/einschriebungsversuch.ftl").forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int KID = Integer.parseInt(request.getParameter("ks"));
		
		k = ks.get_kurs(KID);
		
		try {
			if (ks.ist_eingeschrieben(KID)) {
				msg = "You are already registered for this course";
			} else if (k.getFreiePlaetze()<=0){
				msg ="All places are already taken try next semester!";
			} else if (k.getSchluessel()!=null){
				String kKey;
				kKey = request.getParameter("key");

				if (request.getParameter("key").equals("")){
					msg = "You have to enter a key";
				} else if (!k.getSchluessel().equals(kKey)){
					msg = "Incorrect key";
				} else {
					msg= "You are now registered";
				}
			} else {
				msg= "You are now registered";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (msg.equals("You are now registered")){
			try {
				ks.sich_einschreiben(k);
				ks.complete();
				ks.close();
				Kurs_details kd = new Kurs_details(k);
	        	kd.doPost(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			ks.close();
			doGet(request, response);
		}
	}
}
