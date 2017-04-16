package kiss;

import kiss.domain.Bill;
import kiss.domain.PaymentSchedule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class BillDao {

	public static void main(String[] args) {
		insert();
	}

	private static void insert() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Bill bill = new Bill();
		String id = (String) session.save(bill);

		Bill billLoad = (Bill) session.load(Bill.class, id);
		billLoad.book(10L);
		session.save(billLoad);

		session.getTransaction().commit();
		session.close();
	}
}
