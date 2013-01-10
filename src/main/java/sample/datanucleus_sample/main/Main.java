package sample.datanucleus_sample.main;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import sample.datanucleus_sample.model.MyClass;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PersistenceManagerFactory pmf =
			JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		MyClass class1 = new MyClass();
		class1.setId(11L);
		class1.setFirstName("firstName");
		
		pm.makePersistent(class1);

		Query query = pm.newQuery(MyClass.class);
		List<MyClass> results = (List<MyClass>) query.execute();
		for (MyClass result : results) {
			System.out.println(result.getId());
		}
	}
}
