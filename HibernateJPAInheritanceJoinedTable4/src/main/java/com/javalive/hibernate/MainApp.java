package com.javalive.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.javalive.entity.Account;
import com.javalive.entity.CreditAccount;
import com.javalive.entity.DebitAccount;

/**
 * @author javalive.com
 */
public class MainApp {

	public static void main(String[] args) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();

			DebitAccount account = new DebitAccount();
			account.setBalance(20000.0);
			account.setInterestRate(10.0);
			account.setOwner("Vijay");
			account.setOverdraftFee(36d);
			session.save(account);

			CreditAccount account1 = new CreditAccount();
			account1.setBalance(30000.0);
			account1.setInterestRate(10.0);
			account1.setOwner("Suresh");
			account1.setCreditLimit(500000d);
			session.save(account1);

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		HibernateUtil.shutdown();
	}
}
