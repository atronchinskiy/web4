package servlet;

import org.eclipse.jetty.http.HttpStatus;
import service.DailyReportService;
import util.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewDayServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			if (DailyReportService.getInstance().addNewDaily()){
				resp.setStatus(HttpStatus.OK_200);
			} else {
				resp.setStatus(HttpStatus.FORBIDDEN_403);
			}
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}
