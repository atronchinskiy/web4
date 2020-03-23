package servlet;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import service.DailyReportService;
import util.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DailyReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = null;

        if (req.getPathInfo().contains("all")) {
            try {
                json = gson.toJson(DailyReportService.getInstance().getAllDailyReports());
                resp.getWriter().write(json);
                resp.setStatus(HttpStatus.OK_200);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } else if (req.getPathInfo().contains("last")) {
            try {
                json = gson.toJson(DailyReportService.getInstance().getLastReport());
                resp.getWriter().write(json);
                resp.setStatus(HttpStatus.OK_200);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DailyReportService.getInstance().deleteData();
            resp.setStatus(HttpStatus.OK_200);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
