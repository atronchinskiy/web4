package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.Car;
import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import util.DBException;
import util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class CarService {

    {
        carInstance = getInstance();
    }

    private static CarService carService;

    private SessionFactory sessionFactory;

    public static CarService carInstance;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }


    public List<Car> getAllCars() throws DBException {
        List<Car> carList = new ArrayList<>();
        try {
            carList = new CarDao(sessionFactory.openSession()).getAllCars();
        } catch (HibernateException e){
            throw new DBException(e);
        }
        return carList;
    }


    public Car getAvailableCar(String brand, String model, String licensePlate) throws DBException {
        Car car = null;
        try {
            car = new CarDao(sessionFactory.openSession()).getAvailableCar(brand, model, licensePlate);
        } catch (HibernateException e){
            throw new DBException(e);
        }
        return car;
    }

    public boolean addCar (String brand, String model, String licensePlate, Long price) throws DBException {
        boolean flag = false;
        try {
            flag = new CarDao(sessionFactory.openSession()).addCar(brand, model, licensePlate, price);
        } catch (HibernateException e){
            throw new DBException(e);
        }
        return flag;
    }
}
