package de.unidue.inf.is;
import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.domain.User;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import  de.unidue.inf.is.stores.KursStore;
import de.unidue.inf.is.stores.UserStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class viewkurs_servlet extends HttpServlet {
    KursStore ks = new KursStore();
    Kurs k = new Kurs();
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("my_k",k);
        request.getRequestDispatcher("/nicht_ein_Ansicht.ftl").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int KID = Integer.parseInt(request.getParameter("ks"));

        try {
          if(!ks.ist_eingeschrieben(KID)) {
              System.out.println("nicht eingeschrieben");
              k = ks.get_kurs(KID);
              System.out.println(k.getName());
              doGet(request,response);
          } else {
              System.out.println("eingeschrieben");
              k = ks.get_kurs(KID);
              System.out.println(k.getName());
              Kurs_details kd = new Kurs_details(k);
              kd.doPost(request, response);
          }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
