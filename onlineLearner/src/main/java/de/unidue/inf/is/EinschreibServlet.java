package de.unidue.inf.is;

import java.io.IOException;
import java.sql.SQLException;

import de.unidue.inf.is.stores.KursStore;
import de.unidue.inf.is.domain.Kurs;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EinschreibServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Kurs k = new Kurs();
    KursStore ks = new KursStore();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("my_k", k);
        request.getRequestDispatcher("/newEnroll.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int KID = Integer.parseInt(request.getParameter("ks"));
        try {
            k = ks.get_kurs(KID);
            System.out.println(k.getSchluessel());
            if(k.getSchluessel() == null) {
            	//Schreibe direkt ein
            	ks.sich_einschreiben(k);
            	ks.complete();
            	ks.close();
            	DetailseiteServlet kd = new DetailseiteServlet(k);
            	kd.doPost(request, response);
            }
            else {
            	ks.close();
            	doGet(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
