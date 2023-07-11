package osu.cse3241;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class Querylets {
	
	public static int sqlQueryAllStrings(Connection conn, String sql, boolean has_params, List<String> params) {
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			if (has_params) {
				int i = 1;
				for (String param : params) {
					stmt.setString(i, param);
					i++;
				}
			}
			
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			return columnCount;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return -1;
	}
}
