package de.unidue.inf.is;

import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.stores.AufgabeStore;
import de.unidue.inf.is.stores.KursStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

//Überprüfung
public class NewRateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String errorMessage = "";
    int kid;
    int aNum;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("/NewRate.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        kid = Integer.parseInt(request.getParameter("kid"));
        aNum = Integer.parseInt(request.getParameter("anummer"));

        /*
        int KID = Integer.parseInt(request.getParameter("ks"));
        int aid = Integer.parseInt(request.getParameter("aID"));
        int bnummer = Integer.parseInt(request.getParameter("bnummer"));
        int note = Integer.parseInt(request.getParameter("note"));
        String comment = request.getParameter("comment");
        KursStore ks = new KursStore();
        AufgabeStore as = new AufgabeStore();
        Kurs k = ks.get_kurs(KID);

        try {
            if (!ks.ist_eingeschrieben(KID)) {
                errorMessage = "Du bist nicht im Kurs eingeschrieben!";
                as.close();
                doGet(request, response);
            } else if (k.getErsteller() == 1) {
                errorMessage = "Du kannst nicht deine eigene Abgabe bewerten!";
                as.close();
                doGet(request, response);
            } else {
                as.createNewRate(aid, bnummer, note, comment);
                as.complete();
                as.close();
                HauptseiteServlet main = new HauptseiteServlet();
                main.doGet(request, response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        */
    }
}
