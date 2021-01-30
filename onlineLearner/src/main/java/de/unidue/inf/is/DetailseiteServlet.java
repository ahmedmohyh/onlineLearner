package de.unidue.inf.is;

import de.unidue.inf.is.domain.Aufgabe;
import de.unidue.inf.is.domain.Kurs;

import java.io.IOException;
import java.util.ArrayList;

import de.unidue.inf.is.domain.Einreichen;
import de.unidue.inf.is.stores.AufgabeStore;
import de.unidue.inf.is.stores.KursStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Servlet um die Kursübersicht anzuzeigen
public class DetailseiteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Kurs k = new Kurs();
    KursStore ks = new KursStore();
    AufgabeStore as = new AufgabeStore();
    ArrayList<Aufgabe> af = new ArrayList<Aufgabe>();
    ArrayList<Einreichen> ei = new ArrayList<>();

    DetailseiteServlet(Kurs kk) {
        k = kk;
    }

    DetailseiteServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("my_auf_list", af);
        request.setAttribute("my_k", k);
        //  request.setAttribute("my_ab_list",ei);
        request.getRequestDispatcher("/detailseite.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        af = as.get_A_einesKurses(k);
        System.out.println("i got here 1");
        ei = as.get_abgabe_eineskurses(k);
        System.out.println("i got here 2");
        for (Einreichen ee : ei) {
            int x = ee.getAnummer();
            for (Aufgabe a : af) {
                if (a.getAnummer() == x) {
                    if (ee.getAbgabetext().equals("")) {
                        a.setAbgabetext("Keine Abgabe");
                    } else {
                        a.setAbgabetext(ee.getAbgabetext());
                    }
                }
            }
        }
        for (Aufgabe a : af) {
            if (a.getAbgabetext().equals("")) {
                a.setAbgabetext("Keine Abgabe");
            }
        }
        doGet(request, response);
    }
}
