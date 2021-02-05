package de.unidue.inf.is;

import java.io.IOException;

import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.stores.KursStore;
import jdk.internal.vm.compiler.collections.EconomicMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewCourseServlet extends HttpServlet {
    KursStore ks;
    Kurs k = new Kurs();
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("my_k", k);
        request.getRequestDispatcher("/nichtEingeschrieben.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int KID = Integer.parseInt(request.getParameter("ks"));
        ks = new KursStore();
        try {
            if (!ks.ist_eingeschrieben(KID)) {
                System.out.println("nicht eingeschrieben");
                k = ks.get_kurs(KID);
                System.out.println(k.getName());
                ks.complete();
                ks.close();
                doGet(request, response);
            } else {
                System.out.println("eingeschrieben");
                k = ks.get_kurs(KID);
                System.out.println(k.getName());
                ks.complete();
                ks.close();
                DetailseiteServlet kd = new DetailseiteServlet(k);
                kd.doPost(request, response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
