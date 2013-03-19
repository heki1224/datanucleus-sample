package sample.datanucleus_sample.dao;

import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sample.datanucleus_sample.model.User;

@Repository
public class UserDao {

	private static final Log LOG = LogFactory.getLog(UserDao.class);

	@Autowired
	@Qualifier("persistenceManagerFactoryProxy")
	private PersistenceManagerFactory persistenceManagerFactory;

	/**
	 * 1件取得
	 * 
	 * @param id
	 * @return
	 */
	public User find(Long id) {
		User result = null;
		PersistenceManager pm = this.persistenceManagerFactory.getPersistenceManager();
		try {
			result = pm.getObjectById(User.class, id);
		} catch (JDOObjectNotFoundException e) {
			// 1件も存在しない時
			return null;
		}
		return pm.detachCopy(result);
	}

	/**
	 * 全件取得
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		List<User> result = null;
		PersistenceManager pm = this.persistenceManagerFactory.getPersistenceManager();
		Query query = pm.newQuery(User.class);
		result = (List<User>) query.execute();
		if (result == null || result.size() == 0) {
			return null;
		}
		return (List<User>) pm.detachCopyAll(result);
	}

	/**
	 * 新規作成および更新
	 * 
	 * @param input
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public User save(User input) {
		User result = null;
		PersistenceManager pm = this.persistenceManagerFactory.getPersistenceManager();
		try {
			User o = pm.getObjectById(User.class, input.getId());
			if (input.getVersion() != o.getVersion()) {
				// 楽観的排他制御
				LOG.info("Different Version.");
				return result;
			}
			// 更新する場合
			o.setFirstName(input.getFirstName());
			o.setLastName(input.getLastName());
			o.setBirthDay(input.getBirthDay());
			o.setSex(input.getSex());
			result = o;
		} catch (JDOObjectNotFoundException e) {
			// 1件も存在しない場合
			result = pm.makePersistent(input);
		}
		return result;
	}

	/**
	 * 新規作成および更新 （テスト用）
	 * 
	 * @param input
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public User saveThrowException(User input) {
		User result = null;
		PersistenceManager pm = this.persistenceManagerFactory.getPersistenceManager();
		try {
			User o = pm.getObjectById(User.class, input.getId());
			// 更新する場合
			o.setFirstName(input.getFirstName());
			o.setLastName(input.getLastName());
			o.setBirthDay(input.getBirthDay());
			o.setSex(input.getSex());
			result = o;
			throw new RuntimeException();
		} catch (JDOObjectNotFoundException e) {
			// 1件も存在しない場合
			result = pm.makePersistent(input);
			if (true) {
				throw new RuntimeException();
			}
		}
		return result;
	}

	/**
	 * 削除
	 * 
	 * @param input
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delete(User input) {
		PersistenceManager pm = this.persistenceManagerFactory.getPersistenceManager();
		pm.deletePersistent(input);
	}
}
