package de.unidue.inf.is;

import de.unidue.inf.is.domain.Aufgabe;
import de.unidue.inf.is.domain.Bewertung;
import de.unidue.inf.is.domain.Kurs;

import java.io.IOException;
import java.util.ArrayList;

import de.unidue.inf.is.domain.Einreichen;
import de.unidue.inf.is.stores.AufgabeStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DetailseiteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Kurs k = new Kurs();
    AufgabeStore as;
    ArrayList<Aufgabe> af = new ArrayList<Aufgabe>();
    ArrayList<Einreichen> ei = new ArrayList<>();
    ArrayList<Bewertung> bw = new ArrayList<>();

    DetailseiteServlet(Kurs kk) {
        k = kk;
    }

    DetailseiteServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("my_auf_list", af);
        request.setAttribute("my_k", k);
        request.getRequestDispatcher("/detailseite.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        as = new AufgabeStore();
    	af = as.get_A_einesKurses(k);
        System.out.println("i got here 1");
        ei = as.get_abgabe_eineskurses(k);
        System.out.println("i got here 2");
        for (Einreichen ee : ei) {
            Bewertung b = new Bewertung();
            b = as.get_Bewertung_einerabgabe(ee.getAid());
            ee.setBewertung(Integer.toString(b.getNote()));
            int x = ee.getAnummer();
            for (Aufgabe a : af) {
                if (a.getAnummer() == x) {
                    if (ee.getAbgabetext().equals("")) {
                        a.setAbgabetext("Keine Abgabe");
                    }
                    else {
                        a.setAbgabetext(ee.getAbgabetext());
                    }
                    if (ee.getBewertung().equals("") || ee.getBewertung().equals("0")){
                        a.setBewertung(" Keine Bewertung Vorhanden");
                    }
                    else  {
                        a.setBewertung(ee.getBewertung());
                    }
                }
            }
        }
        for (Aufgabe a : af) {
            if (a.getAbgabetext().equals("")) {
                a.setAbgabetext("Keine Abgabe");
            }
            if (a.getBewertung().equals("")){
                System.out.println("got into bew");
                a.setBewertung(" Keine Bewertung Vorhanden");
            }
        }
        as.complete();
        as.close();
        doGet(request, response);
    }
}
