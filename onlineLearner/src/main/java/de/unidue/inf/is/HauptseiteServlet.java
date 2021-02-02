package de.unidue.inf.is;

import java.io.IOException;
import java.util.ArrayList;

import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.stores.KursStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HauptseiteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ArrayList<Kurs> k = new ArrayList<>();
    private ArrayList<Kurs> k_mycourses = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        KursStore ks = new KursStore();
        k = ks.getAvailableCourses();
        k_mycourses = ks.get_my_courses();
        ks.complete();
        ks.close();

        request.setAttribute("avail", k);
        request.setAttribute("meine", k_mycourses);
        request.getRequestDispatcher("/Hauptseite.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
