package sample.datanucleus_sample.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.JdoOptimisticLockingFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sample.datanucleus_sample.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"file:src/main/resources/spring/app-config.xml",
	"file:src/main/resources/spring/db-config.xml" })
public class UserDaoTest {

	private static final Log LOG = LogFactory.getLog(UserDaoTest.class);

	@Autowired
	private UserDao dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dao.deleteAll();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Date currentDate = new Date();

		User user = new User();
		user.setId(1L);
		user.setFirstName("fname");
		user.setLastName("Lname");
		user.setBirthDay(currentDate);
		user.setSex(1);

		// 新規作成
		User result = dao.save(user);
		assertThat(result.getId(), is(1L));
		assertThat(result.getFirstName(), is("fname"));
		assertThat(result.getLastName(), is("Lname"));
		assertThat(result.getBirthDay(), is(currentDate));
		assertThat(result.getSex(), is(1));

		// 変更
		result.setSex(0);
		User fresult = dao.save(result);
		assertThat(fresult.getId(), is(1L));
		assertThat(fresult.getFirstName(), is("fname"));
		assertThat(fresult.getLastName(), is("Lname"));
		assertThat(fresult.getBirthDay().getTime(), is(currentDate.getTime()));
		assertThat(fresult.getSex(), is(0));

		// 結果オブジェクトはデタッチされている事を確認する
		fresult.setSex(1);
		User dresult = dao.find(1L);
		// HBaseに入れるとミリ秒以下は切り捨て
		Date expected = DateUtils.truncate(currentDate, Calendar.SECOND);
		assertThat(dresult.getBirthDay().getTime(), is(expected.getTime()));
		assertThat(dresult.getSex(), is(0));

		// リストで取得
		List<User> list = dao.findAll();
		assertThat(list.size(), is(1));

		// 削除
		dao.delete(dresult);
		dresult = dao.find(1L);
		assertThat(dresult, nullValue());
	}

	@Test
	public void testTransaction() {
		Date currentDate = new Date();

		User user = new User();
		user.setId(2L);
		user.setFirstName("fname");
		user.setLastName("Lname");
		user.setBirthDay(currentDate);
		user.setSex(1);

		User result = null;
		try {
			result = dao.saveThrowException(user);
		} catch (Exception e) {
			// TODO: handle exception
		}
		assertThat(result, nullValue());

		// 1件も挿入されていない事を確認
		List<User> list = dao.findAll();
		assertThat(list, nullValue());
	}

	@Test
	public void testOptimisticTransaction() {
		Date currentDate = new Date();

		User user = new User();
		user.setId(3L);
		user.setFirstName("fname");
		user.setLastName("Lname");
		user.setBirthDay(currentDate);
		user.setSex(1);

		User result = dao.save(user);
		assertThat(result.getVersion(), is(1L));
		result.setSex(0);
		User result2 = dao.save(result);
		assertThat(result2.getVersion(), is(2L));
		User result3 = null;
		try {
			result.setSex(1);
			result3 = dao.save(result);
		} catch (JdoOptimisticLockingFailureException e) {
			LOG.info(e);
		}
		assertThat(result3, nullValue());

		User dresult = dao.find(3L);

		assertThat(dresult.getSex(), is(0));

		dao.delete(dresult);
	}
}
