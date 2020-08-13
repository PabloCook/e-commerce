package ar.com.gl.shop.product.repository.datasources;

import com.mysql.cj.jdbc.MysqlDataSource;

public class CategoryDatasource extends DBUtil{


	public static MysqlDataSource getCategoryDatasource() {
		return getDatasource();
	}
}
