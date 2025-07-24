package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bus.LuongBUS;
import dto.LuongDTO;

public class Luong2GUI extends JPanel {

	LuongBUS luongBUS = new LuongBUS();
	MyProps myprops = new MyProps();

	JPanel pnlTabel;
	JTable tbLuong;

	JPanel pnlForm, pnlButton;

	JLabel lblMaluong, lblHSluong, lblLuongcb, lblHSpc;

	JTextField txtMaluong, txtHSluong, txtLuongcb, txtHSpc;

	JButton btThem, btSua, btXoa;

	final String MA_LUONG = "Mã Lương";

	final String LUONG_COBAN = "Lương Cơ Bản";


	public Luong2GUI() {
		initCompenents();
	}


	public void initCompenents() {
		this.setLayout(null);
		this.setBackground(Color.red);

		initPnlForm();
		initForm();
		initPnlTabel();
		initTbluong();

		tblLuongMouseListener();

		initButton();
		btThemClicked();
		btSuaClicked();
		btXoaClicked();
	}


	public void initPnlForm() {
		pnlForm = new JPanel();
		pnlForm.setBounds(0, 0, ContentPanel.WIDTH, ContentPanel.HEIGHT * 60 / 100);
		pnlForm.setLayout(new GridBagLayout());
		this.add(pnlForm);
	}

	public void initForm() {
		GridBagConstraints cons = new GridBagConstraints();


		lblMaluong = new JLabel(MA_LUONG);
		lblMaluong.setFont(myprops.DEFAULT_FONT_SMALL);
		cons = myprops.MyGridBagConstraints(3, 1, 1, 1, true, true);
		pnlForm.add(lblMaluong, cons);

		txtMaluong = myprops.RoundedTextField(2);
		txtMaluong.setEditable(false);
		cons = myprops.MyGridBagConstraints(4, 1, 1, 1, true, true);
		pnlForm.add(txtMaluong, cons);


		lblLuongcb = new JLabel(LUONG_COBAN);
		lblLuongcb.setFont(myprops.DEFAULT_FONT_SMALL);
		cons = myprops.MyGridBagConstraints(4, 2, 1, 1, true, true);
		pnlForm.add(lblLuongcb, cons);

		txtLuongcb = myprops.RoundedTextField(5);
		txtLuongcb.setEditable(false);
		cons = myprops.MyGridBagConstraints(5, 2, 2, 1, true, true);
		pnlForm.add(txtLuongcb, cons);


	}

	public void initTbluong() {
		tbLuong = new JTable();

		setModelTable();
		tbLuong.getTableHeader().setReorderingAllowed(false);


		tbLuong.getTableHeader().setResizingAllowed(false);


		tbLuong.setAutoCreateRowSorter(true);


		JScrollPane scroll = new JScrollPane(tbLuong);
		pnlTabel.add(scroll);
	}


	public void initPnlTabel() {
		pnlTabel = new JPanel();
		pnlTabel.setBounds(0, pnlForm.getHeight(), ContentPanel.WIDTH, ContentPanel.HEIGHT - pnlForm.getHeight());
		this.add(pnlTabel);
	}

	public void setModelTable() {
		Vector<String> header = new Vector<String>();
		header.add(MA_LUONG);

		header.add(LUONG_COBAN);


		DefaultTableModel dtm = new DefaultTableModel(header, 0) {
			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return Integer.class;
				default:
					return String.class;
				}
			}
		};

		ArrayList<LuongDTO> lst = new ArrayList<LuongDTO>();
		lst = luongBUS.LuongAll();

		LuongDTO l = new LuongDTO();
		for (int i = 0; i < lst.size(); i++) {
			l = lst.get(i);
			Object[] row = { l.getMaLuong(),

					l.getLuongCB(), 
			};
			dtm.addRow(row);
		}
		tbLuong.setModel(dtm);
	}

	private void tblLuongMouseListener() {
		tbLuong.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tbLuong.getSelectedRow();

				txtMaluong.setText(tbLuong.getValueAt(row, 0).toString());
				txtHSluong.setText(tbLuong.getValueAt(row, 1).toString());
				txtLuongcb.setText(tbLuong.getValueAt(row, 2).toString());
				txtHSpc.setText(tbLuong.getValueAt(row, 3).toString());
			}
		});
	}

	private void btThemClicked() {
		btThem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LuongDTO luongdto = new LuongDTO();


				luongdto.setLuongCB(Integer.valueOf(txtLuongcb.getText()));


				luongBUS.LuongAdd(luongdto);

				JOptionPane.showMessageDialog(null, "Thêm thành công");

				setModelTable();
			}
		});
	}

	private void btSuaClicked() {
		btSua.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LuongDTO luongdto = new LuongDTO();

				luongdto.setMaLuong(Integer.valueOf(txtMaluong.getText()));

				luongdto.setLuongCB(Integer.valueOf(txtLuongcb.getText()));


				luongBUS.LuongEdit(luongdto);

				JOptionPane.showMessageDialog(null, "Sửa thành công");

				setModelTable();
			}
		});
	}

	private void btXoaClicked() {
		btXoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int maluong = Integer.valueOf(txtMaluong.getText());

				luongBUS.LuongDelete(maluong);

				JOptionPane.showMessageDialog(null, "Xóa thành công");
				setModelTable();
			}
		});
	}

	private void initButton() {
		GridBagConstraints cons = new GridBagConstraints();
		pnlButton = new JPanel();


		btThem = new JButton();
		myprops.BtnFlat(btThem);
		btThem.setBackground(Color.decode("#43ecdb"));
		btThem.setForeground(Color.red);
		btThem.setFont(new Font("Verdana", Font.PLAIN, 12));
		btThem.setText("Thêm");
		btThem.setSize(20, 10);


		btXoa = new JButton();
		myprops.BtnFlat(btXoa);
		btXoa.setBackground(Color.decode("#43d1ec"));
		btXoa.setForeground(Color.red);
		btXoa.setFont(new Font("Verdana", Font.PLAIN, 12));
		btXoa.setText("Xóa");

		btSua = new JButton();
		myprops.BtnFlat(btSua);

		btSua.setForeground(Color.red);
		btSua.setFont(new Font("Verdana", Font.PLAIN, 12));
		btSua.setText("Sửa");

		pnlButton.add(btThem);
		pnlButton.add(btSua);
		pnlButton.add(btXoa);

		cons = myprops.MyGridBagConstraints(4, 1, 1, 1, true, true);
		pnlForm.add(pnlButton, cons);
	}

}
