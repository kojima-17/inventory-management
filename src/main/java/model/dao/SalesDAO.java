package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SalesDAO {
	public int quickInsert(String text, Connection con) throws DAOException {
		String sql = """
				INSERT INTO sales (customer_note)
				VALUES (?);
				""";
		try(PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			ps.setString(1, text);
			if(ps.executeUpdate() != 1) {
				throw new DAOException("販売ヘッダの追加に失敗しました");
			} else {
				try(ResultSet rs = ps.getGeneratedKeys()){
					if(rs.next()) {
						return rs.getInt(1);
					} else {
						throw new DAOException("販売ヘッダの追加に失敗しました");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new DAOException("販売ヘッダの追加に失敗しました");
	}
}
