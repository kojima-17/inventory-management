package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.been.AuditLogBeen;

public class AuditLogDAO {
	public int logging(AuditLogBeen al, Connection con) throws SQLException{
		String sql = """
				INSERT INTO 
				audit_logs (actor, action, entity, entity_id) 
				VALUES (?, ?, ?, ?);
				""";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, al.getActor());
			ps.setString(2, al.getAction());
			ps.setString(3, al.getEntity());
			ps.setInt(4, al.getEntityId());
			return ps.executeUpdate();
		}
	}
}
