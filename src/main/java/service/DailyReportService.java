package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.Car;
import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import util.DBException;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() throws DBException  {
        try {
            return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
        }catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public DailyReport getLastReport() throws DBException {
        try {
            return new DailyReportDao(sessionFactory.openSession()).getLastReport();
        }catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public boolean addSaleInDailyReport (Car car) throws DBException {
        try {
            return new DailyReportDao(sessionFactory.openSession()).addSaleInDailyReport(car);
        }catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public boolean addNewDaily() throws DBException {
        try {
            return new DailyReportDao(sessionFactory.openSession()).addNewDaily();
        }catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void deleteData() throws DBException {
        try {
            new DailyReportDao(sessionFactory.openSession()).deleteDailyReport();
            new CarDao(sessionFactory.openSession()).deleteCars();
        }catch (HibernateException e) {
            throw new DBException(e);
        }
    }
}
