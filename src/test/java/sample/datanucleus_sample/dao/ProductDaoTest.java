package sample.datanucleus_sample.dao;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sample.datanucleus_sample.model.MyClass;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"file:src/main/resources/spring/app-config.xml",
	"file:src/main/resources/spring/db-config.xml" })
public class ProductDaoTest {

	@Autowired
	private ProductDao dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadProducts() {
		List<MyClass> actual = (List<MyClass>) dao.loadProducts();
		for (MyClass i : actual) {
			System.out.println(i.getId());
			i.setFirstName("yyyymmdd" + new Date().getTime());
			dao.save(i);
		}
	}
}
