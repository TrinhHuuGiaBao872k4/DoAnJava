package dao;

import dto.LuongDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.MySqlDataAccessHelper;

public class LuongDAO {
	public static Object arr;


	public ArrayList<LuongDTO> LuongAll() {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();
		ArrayList<LuongDTO> arr = new ArrayList<LuongDTO>();
		String sql = "Select * from luong order by maluong";
		try {
			ResultSet rs = conn.executeQuery(sql);
			while (rs.next()) {

				LuongDTO luong = new LuongDTO();
				luong.setMaLuong(rs.getInt("maluong"));

				luong.setLuongCB(rs.getInt("luongcoban"));

				arr.add(luong);
			}
		} catch (SQLException ex) {
			conn.displayError(ex);
		}
		conn.Close();
		return arr;
	}
	
	public int LuongGet(int maNv) {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();
		
		ArrayList<LuongDTO> arr = new ArrayList<LuongDTO>();
		String sql = "Select * from luong lg join nhanvien nv on nv.maluong = lg.maluong where nv.manv = " + maNv;
		
		try {
			ResultSet rs = conn.executeQuery(sql);
			while (rs.next()) {

				LuongDTO luong = new LuongDTO();

				luong.setMaLuong(rs.getInt("maluong"));

				luong.setLuongCB(rs.getInt("luongcoban"));

				arr.add(luong);
			}
		} catch (SQLException ex) {
			conn.displayError(ex);
		}
		
		conn.Close();
		return arr.get(0).getLuongCB();
	}


	public void LuongAdd(LuongDTO luongadd) {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();
		String sql = "INSERT INTO luong(luongcoban) VALUES(?)";
		conn.prepare(sql);

		conn.bind(1, luongadd.getLuongCB());


		conn.executeUpdatePre();

		conn.Close();
	}


	public void LuongEdit(LuongDTO luongadd) {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();
		String sql = "UPDATE luong SET luongcoban = ? WHERE maluong = ?";

		conn.prepare(sql);


		conn.bind(1, luongadd.getLuongCB());

		conn.bind(2, luongadd.getMaLuong());

		conn.executeUpdatePre();

		conn.Close();
	}


	public void LuongDelete(int maluong) {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();

		String sql = "DELETE FROM luong WHERE maluong = ?";

		conn.prepare(sql);

		conn.bind(1, maluong);

		conn.executeUpdatePre();

		conn.Close();
	}

}
