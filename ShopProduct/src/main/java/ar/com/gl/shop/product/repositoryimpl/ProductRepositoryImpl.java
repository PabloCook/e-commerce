package ar.com.gl.shop.product.repositoryimpl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.ProductRepository;
import ar.com.gl.shop.product.repository.datasources.ProductDatasource;
import ar.com.gl.shop.product.services.StockService;

public class ProductRepositoryImpl implements Serializable, ProductRepository {

	private static final long serialVersionUID = 3876426318410983253L;
	private static ProductRepositoryImpl INSTANCE;

	private Connection con;
	private Statement st;
	private ResultSet rs;

	private ProductRepositoryImpl() {
	}

	public static ProductRepositoryImpl getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ProductRepositoryImpl();
		}
		return INSTANCE;
	}

	@Override
	public Product create(Product product) {

		final String query = "INSERT INTO product (name, description, price, stock, category, enabled) VALUES (?,?,?,?,?,?);";

		Product productSave = null;

		try {
			con = ProductDatasource.getProductDatasource().getConnection();
			PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, product.getName());
			pst.setString(2, product.getDescription());
			pst.setDouble(3, product.getPrice());
			pst.setLong(4, product.getStock().getId());
			pst.setLong(5, product.getCategory().getId());
			pst.setBoolean(6, product.getEnabled());

			pst.executeUpdate();

			rs = pst.getGeneratedKeys();

			if (rs.next()) {
				productSave = getBydId((long) rs.getInt(1));
			} else {
				throw new SQLException("Registro no encontrado");
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				con.close();
				// st.close();
				// rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				e.getMessage();
			}
		}

		return productSave;
	}

	@Override
	public List<Product> findAll() {
		final String query = "SELECT * FROM product;";
		List<Product> products = new ArrayList<Product>();
		try {
			con = ProductDatasource.getProductDatasource().getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			if (!rs.first()) {
				throw new SQLException("Registros no encontrados");
			} else {
				while (rs.next()) {
					Product product = new Product();
					product.setStock(new Stock());
					product.setCategory(new Category());

					product.setId(rs.getLong("id"));
					product.setName(rs.getString("name"));
					product.setDescription(rs.getString("description"));
					product.setPrice(rs.getDouble("price"));
					product.setEnabled(rs.getBoolean("enabled"));
					product.setStock(StockRepositoryImpl.getInstance().getById(rs.getLong("stock")));
					product.setCategory(CategoryRepositoryImpl.getInstance().getById(rs.getLong("category")));

					products.add(product);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				st.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return products;
	}

	@Override
	public void delete(Product product) {
		final String query = "DELETE FROM product WHERE id=?;";
		try {

			con = ProductDatasource.getProductDatasource().getConnection();
			st = con.createStatement();

			PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, product.getId());

			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				st.close();
				// rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Product getBydId(Long id) {
		final String query = "SELECT * FROM product WHERE id=?;";
		Product product = null;
		try {
			con = ProductDatasource.getProductDatasource().getConnection();
			st = con.createStatement();

			PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			pst.setLong(1, id);

			rs = pst.executeQuery();

			if (!rs.next()) {
				throw new SQLException("Registro no encontrado");
			} else {

				product = new Product();
				product.setStock(new Stock());
				product.setCategory(new Category());

				product.setId(rs.getLong("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getDouble("price"));
				product.setEnabled(rs.getBoolean("enabled"));
				product.setStock(StockRepositoryImpl.getInstance().getById(rs.getLong("stock")));
				product.setCategory(CategoryRepositoryImpl.getInstance().getById(rs.getLong("category")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				st.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return product;
	}

	@Override
	public Product update(Product product) {
		final String query = "UPDATE product SET name=?, description=?, price=?, category=?, enabled=? where id=?;";
		Product productSave = null;

		try {
			con = ProductDatasource.getProductDatasource().getConnection();
			PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, product.getName());
			pst.setString(2, product.getDescription());
			pst.setDouble(3, product.getPrice());
			pst.setLong(4, product.getCategory().getId());
			pst.setBoolean(5, product.getEnabled());
			pst.setLong(6, product.getId());

			pst.executeUpdate();

			rs = pst.getGeneratedKeys();
			
			if (rs.next()) {
				productSave = getBydId((long) rs.getInt(1));
			}else {
				throw new SQLException("Registro no encontrados");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				st.close();
				// rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productSave;

	}

}
