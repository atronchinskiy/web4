package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.*;

import java.util.List;

public class CarDao {

	private Session session;

	public CarDao(Session session) {
		this.session = session;
	}

	public List<Car> getAllCars(){
		List<Car> carsList = null;
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			carsList = session.createQuery("FROM Car").list(); //посмотреть get
			transaction.commit();
		}catch (HibernateException e) {
			//e.printStackTrace();
			if(transaction != null) transaction.rollback();
		} finally {
			try {
				if(session != null) session.close();
			}catch (Exception e){
				e.printStackTrace();
			}
			return carsList;
		}
	}

	//проверка наличия машины
	public Car getAvailableCar(String brand, String model, String licensePlate) {
		Car car = null;
		Transaction transaction = null;
		try {

			transaction = session.beginTransaction();
			Query q = session.createQuery("FROM Car WHERE brand= :br AND model = :m AND licensePlate = :lp");
			q.setParameter("br", brand).setParameter("m", model).setParameter("lp", licensePlate);
			List<Car> carList = q.list();
			if (!carList.isEmpty()) {
				car = carList.get(0);
			}
			transaction.commit();
		} catch (HibernateException e){
			if(transaction != null) transaction.rollback();
		} finally {
			try {
				if(session != null) session.close();
			}catch (Exception e){
				e.printStackTrace();
			}
			return car;
		}
	}

	public boolean addCar(String brand, String model, String licensePlate, Long price) {
		boolean flag = false;
		Transaction transaction = null;
		try {
	//check count of model
			transaction = session.beginTransaction();
			Query q = session.createQuery("SELECT COUNT(t.model) FROM Car AS t WHERE brand= :br");
			q.setParameter("br", brand);
			Long cnt = (Long) q.list().get(0);
			if (cnt < 10) {
				session.save(new Car(brand, model, licensePlate, price));
				transaction.commit();
				flag = true;
			}
		} catch (HibernateException e){
			if(transaction != null) transaction.rollback();
		} finally {
			try {
				if(session != null) session.close();
			}catch (Exception e){
				e.printStackTrace();
			}
			return flag;
		}
	}

	public void deleteCars() {
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.createQuery("DELETE Car").executeUpdate();
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
