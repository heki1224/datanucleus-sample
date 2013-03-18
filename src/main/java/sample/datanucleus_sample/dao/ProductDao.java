package sample.datanucleus_sample.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sample.datanucleus_sample.model.MyClass;

@Repository
public class ProductDao {

	@Autowired
	@Qualifier("persistenceManagerFactoryProxy")
	private PersistenceManagerFactory persistenceManagerFactory;

	public List<MyClass> loadProducts() {
		PersistenceManager pm = this.persistenceManagerFactory.getPersistenceManager();
		Query query = pm.newQuery(MyClass.class);
		List<MyClass> results = (List<MyClass>) query.execute();
		return (List<MyClass>) pm.detachCopyAll(results);
	}

	@Transactional(rollbackFor = Exception.class)
	public MyClass create(MyClass input) {
		PersistenceManager pm = this.persistenceManagerFactory.getPersistenceManager();
		MyClass result = pm.makePersistent(input);
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public MyClass save(MyClass input) {
		PersistenceManager pm = this.persistenceManagerFactory.getPersistenceManager();
		MyClass result = pm.makePersistent(input);
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(MyClass input) {
		PersistenceManager pm = this.persistenceManagerFactory.getPersistenceManager();
		pm.deletePersistent(input);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteAll(List<MyClass> list) {
		PersistenceManager pm = this.persistenceManagerFactory.getPersistenceManager();
		pm.deletePersistentAll(list);
	}
}
