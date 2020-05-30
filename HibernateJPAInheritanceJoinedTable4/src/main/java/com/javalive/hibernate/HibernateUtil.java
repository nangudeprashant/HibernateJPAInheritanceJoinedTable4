package com.javalive.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import com.javalive.entity.Account;
import com.javalive.entity.CreditAccount;
import com.javalive.entity.DebitAccount;

/**
 * Each subclass can also be mapped to its own table. This is also called
 * table-per-subclass mapping strategy. An inherited state is retrieved by
 * joining with the table of the superclass. A discriminator column is not
 * required for this mapping strategy. Each subclass must, however, declare a
 * table column holding the object identifier. The JOINED table inheritance
 * strategy addresses the data integrity concerns because every subclass is
 * associated with a different table. Polymorphic queries or @OneToMany base
 * class associations don’t perform very well with this strategy. However,
 * polymorphic @ManyToOne associations are fine, and they can provide a lot of
 * value. 
 * If data consistency is more important than performance and you need
 * polymorphic queries and relationships, the joined strategy is probably your
 * best option. 
 * Syntax: @Inheritance(strategy = InheritanceType.JOINED)
 */
public class HibernateUtil {

	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

				Map<String, Object> settings = new HashMap<String, Object>();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://localhost:3306/sakila?useSSL=false");
				settings.put(Environment.USER, "root");
				settings.put(Environment.PASS, "root");
				settings.put(Environment.HBM2DDL_AUTO, "create");
				settings.put(Environment.SHOW_SQL, "true");
				registryBuilder.applySettings(settings);
				registry = registryBuilder.build();

				MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(Account.class)
						.addAnnotatedClass(CreditAccount.class).addAnnotatedClass(DebitAccount.class);

				Metadata metadata = sources.getMetadataBuilder().build();

				// To apply logging Interceptor using session factory
				sessionFactory = metadata.getSessionFactoryBuilder()
						// .applyInterceptor(new LoggingInterceptor())
						.build();
			} catch (Exception e) {
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}
