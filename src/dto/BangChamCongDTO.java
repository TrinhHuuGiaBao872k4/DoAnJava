
package dto;

/**
 *
 * @author ADMIN
 */
public class BangChamCongDTO {
	private int MaChamCong;
	private int NgayTrongThang;
	private String TrangThai;

	public BangChamCongDTO() {
	}

	public BangChamCongDTO(BangChamCongDTO bcc) {
		this.MaChamCong = bcc.MaChamCong;
		this.NgayTrongThang = bcc.NgayTrongThang;
		this.TrangThai = bcc.TrangThai;
	}


	public int getMaChamCong() {
		return MaChamCong;
	}

	public int getNgayTrongThang() {
		return NgayTrongThang;
	}

	public String getTrangThai() {
		return TrangThai;
	}


	public void setMaChamCong(int machamcong) {
		MaChamCong = machamcong;
	}

	public void setNgayTrongThang(int ngaytrongthang) {
		NgayTrongThang = ngaytrongthang;
	}

	public void setTrangThai(String trangthai) {
		TrangThai = trangthai;
	}
}
