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
    AufgabeStore afs = new AufgabeStore();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("/NewRate.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int AID = Integer.parseInt(request.getParameter("aid"));
        int bnummer = Integer.parseInt(request.getParameter("user"));
        int note = 0;
        String rates = request.getParameter("rate");
            if ("1".equals(request.getParameter("one"))) {
            note = 1;
        }
        else if ("2".equals(request.getParameter("two"))) {
            note = 2;
        }
        else if ("3".equals(request.getParameter("three"))) {
            note = 3;
        }
        else if ("4".equals(request.getParameter("four"))) {
            note = 4;
        }
       else if ("5".equals(request.getParameter("five"))) {
            note = 5;
        }
        else if ("6".equals(request.getParameter("six"))) {
            note = 6;
        }
      String ss= "";
           ss = request.getParameter("comment");
            System.out.println(note);
            System.out.println(ss);
       afs.createNewRate(bnummer, AID , note , ss);
        errorMessage = "You added The Rate Successfully";
        doGet(request, response);
    }

}

