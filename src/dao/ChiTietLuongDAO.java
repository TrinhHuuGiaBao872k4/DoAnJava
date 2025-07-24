package dao;

import dto.ChiTietLuongDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.MySqlDataAccessHelper;

public class ChiTietLuongDAO {
	public static Object arr;

	public ArrayList<ChiTietLuongDTO> ChiTietLuongAll() {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();
		ArrayList<ChiTietLuongDTO> arr = new ArrayList<ChiTietLuongDTO>();
		String sql = "Select * from chitietluong";
		try {
			ResultSet rs = conn.executeQuery(sql);
			while (rs.next()) {

				ChiTietLuongDTO ct = new ChiTietLuongDTO(null);
	
				ct.setMaLuong(rs.getInt("MaLuong"));
				ct.setLuongCB(rs.getInt("LuongCB"));
				ct.setTongSoNgay(rs.getInt("TongSoNgay"));
				ct.setSoNgayNghi(rs.getInt("SoNgayNghi"));

				arr.add(ct);
			}
		} catch (SQLException ex) {
			conn.displayError(ex);
		}
		conn.Close();
		return arr;
	}


	public void ChiTietLuongAdd(ChiTietLuongDTO chitietluongadd) {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();
		String sql = "INSERT INTO luong (tongsongay, luongcb, songaynghi)" + "VALUES(?, ?, ?)";

		conn.prepare(sql);

		conn.bind(1, chitietluongadd.getTongSoNgay());
		conn.bind(2, chitietluongadd.getLuongCB());
		conn.bind(3, chitietluongadd.getSoNgayNghi());

		conn.executeUpdatePre();

		conn.Close();
	}


	public void ChiTietLuongEdit(ChiTietLuongDTO chitietluongadd) {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();
		String sql = "UPDATE luong SET tongsongay = ?, luongcb = ?, songaynghi = ? WHERE maluong = ?";

		conn.prepare(sql);


		conn.bind(1, chitietluongadd.getTongSoNgay());
		conn.bind(2, chitietluongadd.getLuongCB());
		conn.bind(3, chitietluongadd.getSoNgayNghi());
		conn.bind(4, chitietluongadd.getMaLuong());

		conn.executeUpdatePre();

		conn.Close();
	}


	public void ChiTietLuongDelete(int maluong) {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();

		String sql = "DELETE FROM luong WHERE maluong = ?";

		conn.prepare(sql);

		conn.bind(1, maluong);

		conn.executeUpdatePre();

		conn.Close();
	}
}