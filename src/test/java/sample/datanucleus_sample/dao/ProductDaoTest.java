package sample.datanucleus_sample.dao;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.jdo.JDOHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	"file:src/test/resources/spring/app-config.xml",
	"file:src/test/resources/spring/db-config.xml" })
public class ProductDaoTest {

	private static final Log LOG = LogFactory.getLog(ProductDaoTest.class);

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
		LOG.info("before load size=" + actual.size());
		MyClass input = new MyClass();
		Random r = new Random(System.currentTimeMillis());
		input.setId(r.nextLong());
		LOG.info("input id=" + input.getId());
		input.setFirstName("yyyymmdd" + new Date().getTime());
		try {
			dao.save(input);
			LOG.info("JDO version=" + JDOHelper.getVersion(input));
		} catch (Exception e) {
			LOG.info(e);
		}
		actual = (List<MyClass>) dao.loadProducts();
		LOG.info("after load size=" + actual.size());
	}
}
