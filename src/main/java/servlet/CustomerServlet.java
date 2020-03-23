package servlet;

import com.google.gson.Gson;
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

public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = null;
        try {
            CarService carServiceInstance = CarService.getInstance();
            json = gson.toJson(carServiceInstance.getAllCars());
        } catch (DBException e) {
            e.printStackTrace();
        }
        resp.getWriter().write(json);
        resp.setStatus(HttpStatus.OK_200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    //check car available
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        Car car = null;

        try {
            CarService carServiceInstance = CarService.getInstance();
            car = carServiceInstance.getAvailableCar(brand, model, licensePlate);

    //delete sold car and update daily report
            if (car != null) {
                DailyReportService.getInstance().addSaleInDailyReport(car);
            }
        } catch (DBException e) {
            e.printStackTrace();
        }

    }
}
