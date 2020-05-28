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

			// save the account object

			Account account2 = new Account();
			account2.setBalance(10000.0);
			account2.setInterestRate(10.0);
			account2.setOwner("Ramesh");
			session.save(account2);

			DebitAccount account = new DebitAccount();
			account.setBalance(10000.0);
			account.setInterestRate(10.0);
			account.setOwner("Ramesh");
			account.setOverdraftFee(36d);
			session.save(account);

			CreditAccount account1 = new CreditAccount();
			account1.setBalance(10000.0);
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
