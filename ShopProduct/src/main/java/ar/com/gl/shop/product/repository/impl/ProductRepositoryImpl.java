package ar.com.gl.shop.product.repository.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.repository.ProductRepository;
import ar.com.gl.shop.product.repository.datasources.ProductDatasource;

public class ProductRepositoryImpl implements Serializable, ProductRepository {

	private static final long serialVersionUID = 3876426318410983253L;
	private static ProductRepositoryImpl INSTANCE;

	private Connection con;
	private ResultSet rs;
	private PreparedStatement pst;

	private ProductRepositoryImpl() {
	}

	public static ProductRepositoryImpl getInstance() {
		if (isNull(INSTANCE)) {
			INSTANCE = new ProductRepositoryImpl();
		}
		return INSTANCE;
	}

	@Override
	public Product create(Product product) throws ItemNotFound {

		final String query = "INSERT INTO product (name, description, price, stock_id, category_id, enabled) VALUES (?,?,?,?,?,?);";

		Product productSave = null;

		try {
			con = ProductDatasource.getProductDatasource().getConnection();
			pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, product.getName());
			pst.setString(2, product.getDescription());
			pst.setDouble(3, product.getPrice());
			pst.setLong(4, product.getStock().getId());
			pst.setLong(5, product.getCategory().getId());
			pst.setBoolean(6, product.getEnabled());

			pst.executeUpdate();

			rs = pst.getGeneratedKeys();

			if (rs.next()) {
				productSave = getById((long) rs.getInt(1));
			} else {
				throw new ItemNotFound();
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				con.close();
				pst.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				e.getMessage();
			}
		}

		return productSave;
	}

	@Override
	public List<Product> findAll() throws ItemNotFound {
		final String query = "SELECT * FROM product;";
		List<Product> products = new ArrayList<Product>();
		try {
			con = ProductDatasource.getProductDatasource().getConnection();
			pst = con.prepareStatement(query);

			rs = pst.executeQuery();
			if (!rs.next()) {
				throw new ItemNotFound();

			}
			do {
				Product product = new Product();

				product.setId(rs.getLong("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getDouble("price"));
				product.setEnabled(rs.getBoolean("enabled"));
				product.setStock(StockRepositoryImpl.getInstance().getById(rs.getLong("stock_id")));
				product.setCategory(CategoryRepositoryImpl.getInstance().getById(rs.getLong("category_id")));

				products.add(product);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				pst.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return products;
	}

	@Override
	public void delete(Product product) throws ItemNotFound {
		final String query = "DELETE FROM product WHERE id=?;";
		try {

			con = ProductDatasource.getProductDatasource().getConnection();
			pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			pst.setLong(1, product.getId());

			Integer rs = pst.executeUpdate();
			
			if (rs.equals(0)) {
				throw new ItemNotFound();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Product getById(Long id) throws ItemNotFound {
		final String query = "SELECT * FROM product WHERE id=?;";
		Product product = null;
		try {
			con = ProductDatasource.getProductDatasource().getConnection();

			pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			pst.setLong(1, id);

			rs = pst.executeQuery();

			if (!rs.next()) {
				throw new ItemNotFound();
			} else {

				product = new Product();

				product.setId(rs.getLong("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getDouble("price"));
				product.setEnabled(rs.getBoolean("enabled"));
				product.setStock(StockRepositoryImpl.getInstance().getById(rs.getLong("stock_id")));
				product.setCategory(CategoryRepositoryImpl.getInstance().getById(rs.getLong("category_id")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				pst.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return product;
	}

	@Override
	public Product update(Product product) throws ItemNotFound {
		final String query = "UPDATE product SET name=?, description=?, price=?, category_id=?, enabled=? where id=?;";
		Product productSave = null;

		try {
			con = ProductDatasource.getProductDatasource().getConnection();
			pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, product.getName());
			pst.setString(2, product.getDescription());
			pst.setDouble(3, product.getPrice());
			pst.setLong(4, product.getCategory().getId());
			pst.setBoolean(5, product.getEnabled());
			pst.setLong(6, product.getId());

			Integer rs = pst.executeUpdate();

			if (rs.equals(0)) {
				throw new ItemNotFound();	
			}

				productSave = getById(product.getId());
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				pst.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productSave;

	}

}
