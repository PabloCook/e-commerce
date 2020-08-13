package ar.com.gl.shop.product.repository.datasources;

import com.mysql.cj.jdbc.MysqlDataSource;

public class StockDatasource extends DBUtil{


	public static MysqlDataSource getStockDatasource() {
		return getDatasource();
	}
	

}
