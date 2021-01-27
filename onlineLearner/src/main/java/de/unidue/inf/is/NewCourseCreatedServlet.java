package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.stores.KursStore;

public class NewCourseCreatedServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static KursStore ks;
    private static String errorMessageString;
    private static String[] errorMessage =
            {
                    "Kurs wurde erfolgreich erstellt!",
                    "Die Länge von dem Namen ist nicht korrekt (Die Länge muss 1 bis 50 Zeichen betragen)",
                    "Die Angabe für die freien Plätze ist keine Zahl",
                    "Es dürfen höchstens 100 freie Plätze angegeben werden",
                    "Es wurde keine Beschreibung angegeben"
            };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", errorMessageString);
        request.getRequestDispatcher("/newCourseCreated.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ks = new KursStore();
        Kurs k = new Kurs();
        errorMessageString = errorMessage[0];

        //check user input
        int BID = Integer.parseInt(request.getParameter("user"));
        String kTitle = request.getParameter("title");
        if (kTitle.length() > 50 || kTitle.equals("")) {
            errorMessageString = errorMessage[1];
        }
        int kFreePlaces = 0;
        try {
            kFreePlaces = Integer.parseInt(request.getParameter("limit"));
        } catch (NumberFormatException e) {
            errorMessageString = errorMessage[2];
        }
        if (kFreePlaces > 100) {
            errorMessageString = errorMessage[3];
        }
        if (request.getParameter("description").equals("")) {
            errorMessageString = errorMessage[4];
        } else {
            k.setBeschreibungstext(request.getParameter("description"));
        }
        String kKey;
        if (request.getParameter("key") == null) {
            kKey = "";
        } else {
            kKey = request.getParameter("key");
        }

        //Put user input into new course
        k.setKid(9);
        k.setName(kTitle);
        k.setSchluessel(kKey);
        k.setFreiePlaetze(kFreePlaces);
        k.setErsteller(BID);

        //Create new entry in course via KursStore
        if (errorMessageString.equals(errorMessage[0])) {
            ks.createNewCourse(k);
            ks.complete();
            ks.close();
        }

        doGet(request, response);
    }
}
