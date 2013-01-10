package sample.datanucleus_sample.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sample.datanucleus_sample.model.MyClass;

@Repository
public class ProductDao {
	@Autowired
	private PersistenceManagerFactory persistenceManagerFactory;

	public Object loadProductsByCategory(String category) {
		PersistenceManager pm = this.persistenceManagerFactory.getPersistenceManager();
		try {
			Query query = pm.newQuery(MyClass.class);
			List<MyClass> results = (List<MyClass>) query.execute();
			return results.get(0);
		} finally {
			pm.close();
		}
	}

	@Transactional
	public MyClass save(MyClass input) {
		PersistenceManager pm = this.persistenceManagerFactory.getPersistenceManager();
		try {
			return pm.makePersistent(input);
		} finally {
			pm.close();
		}
	}
}
