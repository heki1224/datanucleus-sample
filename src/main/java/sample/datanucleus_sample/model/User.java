package sample.datanucleus_sample.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.Extensions;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

@PersistenceCapable(detachable = "true", cacheable = "false")
@Version(strategy = VersionStrategy.VERSION_NUMBER, column = "VERSION", extensions = { @Extension(vendorName = "datanucleus", key = "field-name", value = "version") })
@Extensions({
	@Extension(vendorName = "datanucleus", key = "hbase.columnFamily.meta.bloomFilter", value = "ROWKEY"),
	@Extension(vendorName = "datanucleus", key = "hbase.columnFamily.meta.inMemory", value = "true") })
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private long id;

	@Column(name = "meta:firstName")
	private String firstName;

	@Column(name = "meta:lastName")
	private String lastName;

	@Column(name = "meta:birthDay")
	private Date birthDay;

	@Column(name = "meta:sex")
	private Integer sex;

	private long version;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
}
