package de.unidue.inf.is;

import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.domain.User;

import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import  de.unidue.inf.is.stores.KursStore;
import de.unidue.inf.is.stores.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class Hauptseite extends HelloServlet {
    private static final long serialVersionUID = 1L;
    private ArrayList<Kurs> k = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
KursStore ks = new KursStore();
k = ks.getAvailableCourses();


        request.setAttribute("avail",k);
        request.getRequestDispatcher("/Hauptseite.ftl").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        HelloServlet h = new HelloServlet();

        h.doGet(request, response);
    }

}