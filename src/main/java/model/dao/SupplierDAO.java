package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.been.SupplierBeen;

public class SupplierDAO {
	private final String SQL_SELECT_TEMPLATE = """
			SELECT id, name, lead_time_days, phone, email
			FROM suppliers
			WHERE 1 = 1
			""";
	
	public List<SupplierBeen> findAll() {
		String sql = SQL_SELECT_TEMPLATE + ";";
		try(Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			return execSelectAndCreateList(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Optional<SupplierBeen> findById(int id){
		String sql = SQL_SELECT_TEMPLATE + "AND id = ?;";
		try(Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, id);
			return execSelectAndCreateOptional(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public int getRows() {
		String sql = "SELECT COUNT(*) FROM suppliers;";
		try(Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()){
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int insert(SupplierBeen supplier, Connection con) throws SQLException{ 
		String sql ="""
				INSERT INTO 
				suppliers (name, lead_time_days, phone, email) 
				VALUES (?, ?, ?, ?);
				""";
		try(PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			ps.setString(1, supplier.getName());
			ps.setInt(2, supplier.getLeadTimeDays());
			ps.setString(3, supplier.getPhone());
			ps.setString(4, supplier.getEmail());
			int rows = ps.executeUpdate();
			if(rows == 1) {
				try(ResultSet rs = ps.getGeneratedKeys()){
					rs.next();
					return rs.getInt(1);
				}
			}
		}
		return 0;
	}
	
	public int update(SupplierBeen supplier, Connection con) throws SQLException {
		String sql = """
				UPDATE suppliers SET name = ?, lead_time_days = ?, phone = ?
						, email = ?, updated_at = now()
				WHERE id = ?;
				""";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, supplier.getName());
			ps.setInt(2, supplier.getLeadTimeDays());
			ps.setString(3, supplier.getPhone());
			ps.setString(4, supplier.getEmail());
			ps.setInt(5, supplier.getId());
			return ps.executeUpdate();
		}
	}
	
	private List<SupplierBeen> execSelectAndCreateList(PreparedStatement ps) throws SQLException {
		List<SupplierBeen> list = new ArrayList<>();
		try(ResultSet rs = ps.executeQuery()){
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int leadTimeDays =  rs.getInt("lead_time_days");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				list.add(new SupplierBeen(id, name, leadTimeDays, phone, email));
			}
			return list;
		}
	}
	
	private Optional<SupplierBeen> execSelectAndCreateOptional(PreparedStatement ps) throws SQLException{
		try(ResultSet rs = ps.executeQuery()){
			if(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int leadTimeDays =  rs.getInt("lead_time_days");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				return Optional.of(new SupplierBeen(id, name, leadTimeDays, phone, email));
			}
		}
		return Optional.empty();
	}
}
