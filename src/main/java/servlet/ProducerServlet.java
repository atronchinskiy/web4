package servlet;

import model.Car;
import org.eclipse.jetty.http.HttpStatus;
import service.CarService;
import service.DailyReportService;
import util.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        String price = req.getParameter("price");
        boolean flag = false;

        try {
            flag = CarService.getInstance().addCar(brand, model, licensePlate, Long.valueOf(price));
            if (flag) {
                resp.setStatus(HttpStatus.OK_200);
            } else {
                resp.setStatus(HttpStatus.FORBIDDEN_403);
            }

        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
