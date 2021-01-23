package de.unidue.inf.is;
import de.unidue.inf.is.domain.Aufgabe;
import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.domain.User;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import de.unidue.inf.is.domain.einreichen;
import de.unidue.inf.is.stores.AufgabeStore;
import  de.unidue.inf.is.stores.KursStore;
import de.unidue.inf.is.stores.UserStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class Kurs_details extends HttpServlet  {
    private static final long serialVersionUID = 1L;
    Kurs k = new Kurs();
    KursStore ks = new KursStore();
    AufgabeStore as = new AufgabeStore();
    ArrayList<Aufgabe> af = new ArrayList<Aufgabe>();
    ArrayList<einreichen> ei = new ArrayList<>();
    Kurs_details(Kurs kk){
    k=kk;
    }
    Kurs_details(){
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("my_auf_list",af);
        request.setAttribute("my_k",k);
      //  request.setAttribute("my_ab_list",ei);
        request.getRequestDispatcher("/Kurs_Details.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        af = as.get_A_einesKurses(k);
        System.out.println("i got here 1");
        ei = as.get_abgabe_eineskurses(k);
        System.out.println("i got here 2");
        for (einreichen ee : ei){
           int x = ee.getAnummer();
           for (Aufgabe a : af){
               if (a.getAnummer() ==x){
                   if (ee.getAbgabetext()==""){
                       a.setAbgabetext("Keine Abgabe");
                   }
                   else {
                       a.setAbgabetext(ee.getAbgabetext());
                   }
               }
           }
        }
        for (Aufgabe a : af){
            if (a.getAbgabetext()==""){
                a.setAbgabetext("Keine Abgabe");
            }
        }
        doGet(request, response);
    }
}
