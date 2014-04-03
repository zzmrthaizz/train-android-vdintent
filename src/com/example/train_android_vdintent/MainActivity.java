package com.example.train_android_vdintent;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.example.train_android_adpater.PhongBanAdapter;

import android.R.integer;
import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	static ArrayList<PhongBan> phongBans = new ArrayList<PhongBan>();
	PhongBanAdapter adapter;
	EditText maPban;
	EditText tenPban;
	ListView listView;
	Button nhap;
	PhongBan phongbanselected;

	public static final int requestcode_nhapnv = 1;
	public static final int requestcode_xemdsnhanvien = 2;
	public static final int requestcode_datchuvu =3;
	public static final int requestcode_suanhanvien = 4;
	public static final int requestcode_chuyennhanvien =5;
	public static final int resultcode_datchuvuthanhcon = 31;
	public static final int resultcode_nhapnvthanhcong = 11;
	public static final int resultcode_updatethanhcong = 42;
	public static final int resultcode_suanhanvienthanhcong =41;
	public static final int resultcode_chuyennhanvienthanhcong =51;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		maPban = (EditText) findViewById(R.id.txtMaphong);
		tenPban = (EditText) findViewById(R.id.txtTenphong);
		nhap = (Button) findViewById(R.id.btnnhap);
		listView = (ListView) findViewById(R.id.listPhong);
		
		//Test data
		PhongBan pb = new PhongBan("Thai","test1");
		NhanVien nv = new NhanVien("Dang Vo Anh Thai", "01",true);
		NhanVien nv2 = new NhanVien("Nguyen My Tu Anh", "02",true);
		NhanVien nv3 = new NhanVien("Dang Vo Anh Thu", "03",true);
		PhongBan pb2 = new PhongBan("Anh", "test2");
		pb.addnv(nv);
		pb.addnv(nv2);
		pb.addnv(nv3);
		phongBans.add(pb);
		phongBans.add(pb2);
		//end data test
		adapter = new PhongBanAdapter(this, R.layout.item_phongban, phongBans);

		listView.setAdapter(adapter);

		nhap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Creat phongban
				String ten = tenPban.getText() + "";
				String ma = maPban.getText() + "";
				PhongBan phongBan = new PhongBan(ten, ma);
				phongBans.add(phongBan);
				adapter.notifyDataSetChanged();

			}
		});
		registerForContextMenu(listView);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				phongbanselected = phongBans.get(arg2);
				return false;
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.context_menu_phongban, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case R.id.themnv:
			dothemnv();
			break;
		case R.id.dsnhanvien:
			dohiends();
			break;
		case R.id.lapchucvu:
			dolapchucvu();
			break;
		case R.id.xoa:
			doxoa();
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void doxoa() {
		AlertDialog.Builder xoa = new AlertDialog.Builder(MainActivity.this);
		xoa.setTitle("Xoa du lieu");
		xoa.setMessage("Ban co chac muon xoa phong ban nay");
		xoa.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				phongBans.remove(phongbanselected);
				adapter.notifyDataSetChanged();

			}
		});
		xoa.setPositiveButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		xoa.show();

	}

	private void dolapchucvu() {
		Intent intent = new Intent(this, DatChucVuActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("Phongban", phongbanselected);
		intent.putExtra("data", bundle);
		startActivityForResult(intent, requestcode_datchuvu);

	}

	private void dohiends() {
		Intent intent = new Intent(this, DanhSachNhanVienActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("Phongban", phongbanselected);
		intent.putExtra("data", bundle);
		startActivityForResult(intent, requestcode_xemdsnhanvien);

	}

	private void dothemnv() {
		Intent intent = new Intent(this, ThemNhanVienActivity.class);
		startActivityForResult(intent, requestcode_nhapnv);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case resultcode_nhapnvthanhcong:
			Bundle bundle = data.getBundleExtra("data");
			NhanVien nv = (NhanVien) bundle.getSerializable("Nhanvien");
			phongbanselected.addnv(nv);
			adapter.notifyDataSetChanged();
			break;

		case resultcode_datchuvuthanhcon:
			bundle = data.getBundleExtra("data");
			PhongBan pb = (PhongBan) bundle.getSerializable("Phongban");
			phongbanselected.getListNhanvien().clear();
			phongbanselected.getListNhanvien().addAll(pb.getListNhanvien());
			adapter.notifyDataSetChanged();
			
			break;
		case resultcode_updatethanhcong:
			bundle = data.getBundleExtra("data");
			PhongBan pbupdate = (PhongBan) bundle.getSerializable("Phongban");
			phongbanselected.getListNhanvien().clear();
			phongbanselected.getListNhanvien().addAll(pbupdate.getListNhanvien());
			adapter.notifyDataSetChanged();
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public static ArrayList<PhongBan> getlistPhongBan() {
		return phongBans;
	}



}
