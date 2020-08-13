package ar.com.gl.shop.product.repository.datasources;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ProductDatasource extends DBUtil{


	public static MysqlDataSource getProductDatasource() {
		return getDatasource();
	}
	
}
