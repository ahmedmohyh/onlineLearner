package de.unidue.inf.is;

import de.unidue.inf.is.stores.AufgabeStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Überprüfung
public class NewRateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String errorMessage = "";
    AufgabeStore as;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("/NewRate.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int AID = Integer.parseInt(request.getParameter("aid"));
        int bnummer = Integer.parseInt(request.getParameter("user"));
        int rate = Integer.parseInt(request.getParameter("rates"));
        
        String comment= "";
        comment = request.getParameter("comment");
        System.out.println(rate);
        System.out.println(comment);
       
        as = new AufgabeStore();
        as.createNewRate(bnummer, AID , rate , comment);
        errorMessage = "You added The Rate Successfully";
        as.complete();
        as.close();
        doGet(request, response);
    }

}

