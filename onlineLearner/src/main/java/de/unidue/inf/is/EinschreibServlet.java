package de.unidue.inf.is;

import java.io.IOException;

import de.unidue.inf.is.stores.KursStore;
import de.unidue.inf.is.domain.Kurs;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EinschreibServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Kurs k = new Kurs();
    KursStore ks;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("my_k", k);
        request.getRequestDispatcher("/newEnroll.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int KID = Integer.parseInt(request.getParameter("ks"));
        ks = new KursStore();
        k = ks.get_kurs(KID);
        System.out.println(k.getSchluessel());
        if(k.getSchluessel() == null) {
            try {
                ks.sich_einschreiben(k);
                ks.complete();
                ks.close();
                DetailseiteServlet kd = new DetailseiteServlet(k);
                kd.doPost(request, response);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
         else {
            ks.close();
            doGet(request, response);
         }     
    }
}

