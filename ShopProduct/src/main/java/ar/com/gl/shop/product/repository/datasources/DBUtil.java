package ar.com.gl.shop.product.repository.datasources;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DBUtil {

	private static final String PROPERTIES_PATH = "src/main/resources/db.properties";

	private static Properties properties = null;
	private static MysqlDataSource dataSource;
	static {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(PROPERTIES_PATH));

			dataSource = new MysqlDataSource();
			dataSource.setUrl(properties.getProperty("MYSQL_DB_URL"));
			dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
			dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static MysqlDataSource getDatasource() {
		return dataSource;
	}
}
