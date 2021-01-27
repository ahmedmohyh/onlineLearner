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

public class Einschreiben_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Kurs k = new Kurs();
    KursStore ks = new KursStore();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("my_k", k);
        request.getRequestDispatcher("/new_enroll.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int KID = Integer.parseInt(request.getParameter("ks"));
        try {
            k = ks.get_kurs(KID);
            doGet(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

