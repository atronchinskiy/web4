package DAO;

import model.Car;
import org.hibernate.*;
import model.DailyReport;


import java.util.List;

public class DailyReportDao {

    private final Session session;

    public DailyReportDao(final Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = null;
        List<DailyReport> dailyReports = null;

        try {
            transaction = session.beginTransaction();
            dailyReports = session.createQuery("FROM DailyReport").list();
            transaction.commit();
        }catch (HibernateException e){
            if(transaction != null) transaction.rollback();
        } finally {
            try {
                if(session != null) session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dailyReports;
        }
    }

    public boolean deleteSoldCar (Car car) {
        String queryStr;
        boolean flag = false;
        Transaction transaction = null;

        try {
            Query qDel = session.createQuery("DELETE Car WHERE brand = :br AND model = :md AND licensePlate = :lp");
            qDel.setParameter("br", car.getBrand())
                    .setParameter("md", car.getModel())
                    .setParameter("lp", car.getLicensePlate());
            qDel.executeUpdate();
            flag = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
            return flag;
        }
    }

    public boolean addSaleInDailyReport(Car car) {
        String queryStr;
        boolean flag = false;
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

//check active day
            List<DailyReport> res = session.createQuery("FROM DailyReport").list();
            if (res.isEmpty()) {
                session.save(new DailyReport(Long.valueOf(0), Long.valueOf(0)));
            }

//      return max id
            List<DailyReport> listDailyReport = session.createQuery("FROM DailyReport AS t ORDER BY t.id DESC").list();
            DailyReport currentDailyReport = listDailyReport.get(0);

//      update max id (for current DailyReport)
            Query qUpd = session.createQuery("UPDATE DailyReport SET earnings = :earn, soldCars = :sold WHERE id = :idParam");
            qUpd.setParameter("earn", currentDailyReport.getEarnings() + car.getPrice())
                .setParameter("sold", currentDailyReport.getSoldCars() + 1)
                .setParameter("idParam", currentDailyReport.getId());
            qUpd.executeUpdate();
            transaction.commit();
            flag = true;
        }catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        }
    }

    public boolean addNewDaily() {
        boolean flag = false;
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(new DailyReport(Long.valueOf(0), Long.valueOf(0)));
            transaction.commit();
            flag = true;
        }catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
        } finally {
            try {
                if(session != null) session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;//!res.isEmpty();
        }
    }

    public DailyReport getLastReport() {
        String queryStr;
        boolean flag = false;
        Transaction transaction = null;
        DailyReport currentDailyReport = null;

        try {
            transaction = session.beginTransaction();
            List<DailyReport> listDailyReport = session.createQuery("FROM DailyReport AS t ORDER BY t.id DESC").list();
            currentDailyReport = listDailyReport.get(1);
            transaction.commit();
        }catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
        } finally {
            try {
                if(session != null) session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return currentDailyReport;//!res.isEmpty();
        }
    }

    public void deleteDailyReport() {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE DailyReport").executeUpdate();
            transaction.commit();
        }catch (HibernateException e){
            if(transaction != null) transaction.rollback();
        } finally {
            try {
                if(session != null) session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
