package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlDataAccessHelper {
	String Host;
	String UserName;
	String Password;
	String DataBase;

	Connection connect;
	Statement stmt;
	PreparedStatement preStmt;
	ResultSet result;

	public MySqlDataAccessHelper() {
		this.Host = "127.0.0.1";
		this.UserName = "root";
		this.Password = "Gia@bao872k4@";
		this.DataBase = "quanlynhansu";

		this.connect = null;
		this.stmt = null;
		this.result = null;
	}

	protected void driverTest() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected Connection getConnect() {
		if (this.connect == null) {
			driverTest();


			String url = "jdbc:mysql://" + this.Host + "/" + this.DataBase
					+ "?useLegacyDatetimeCode=false&serverTimezone=UTC";
			url += "&user=" + this.UserName;
			url += "&password=" + this.Password;

			try {
				this.connect = DriverManager.getConnection(url);
			} catch (SQLException e) {
				displayError(e);
			}
		}
		return this.connect;
	}


	protected Statement getStatement() {
		if (this.stmt == null) {
			try {
				this.stmt = this.getConnect().createStatement();
			} catch (SQLException e) {
				displayError(e);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return stmt;
	}

	public ResultSet executeQuery(String Query) {
		try {
			this.result = this.getStatement().executeQuery(Query);
		} catch (SQLException e) {
			displayError(e);
		}
		return this.result;
	}

	public int executeUpdate(String Query) {
		int res = Integer.MIN_VALUE;
		try {
			res = this.getStatement().executeUpdate(Query);
		} catch (SQLException e) {
			displayError(e);
		} finally {
			this.Close();
		}

		return res;
	}

	public void prepare(String sql) {
		try {
			this.preStmt = this.getConnect().prepareStatement(sql);
		} catch (SQLException e) {
			displayError(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void bind(int index, Object o) {
		try {
			if (o == null) {
				this.preStmt.setNull(index, java.sql.Types.VARCHAR);
			} else {
				switch (o.getClass().getCanonicalName()) {
				case "java.lang.Integer":
					this.preStmt.setInt(index, (Integer) o);
					break;
				case "java.lang.String":
					this.preStmt.setNString(index, (String) o);
					break;
				case "java.lang.Double":
					this.preStmt.setDouble(index, (Double) o);
					break;
				default:
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public ResultSet executeQueryPre() {
		try {

			this.result = this.preStmt.executeQuery();
		} catch (SQLException e) {
			displayError(e);
		}
		return this.result;
	}

	public int executeUpdatePre() {
		int res = Integer.MIN_VALUE;
		try {

			res = this.preStmt.executeUpdate();
		} catch (SQLException e) {
			displayError(e);
		} finally {
			this.Close();
		}

		return res;
	}

	public void Close() {
		try {
			if (this.result != null) {
				this.result.close();
				this.result = null;
			}

			if (this.stmt != null) {
				this.stmt.close();
				this.stmt = null;
			}

			if (this.connect != null) {
				this.connect.close();
				this.connect = null;
			}
		} catch (SQLException e) {
			displayError(e);
		}
	}

	public void displayError(SQLException ex) {
		System.out.println(" Error Message:" + ex.getMessage());
		System.out.println(" SQL State:" + ex.getSQLState());
		System.out.println(" Error Code:" + ex.getErrorCode());
	}
}