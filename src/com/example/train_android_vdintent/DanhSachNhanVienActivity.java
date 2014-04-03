package com.example.train_android_vdintent;

import java.util.ArrayList;

import com.example.train_android_adpater.NhanVienAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

public class DanhSachNhanVienActivity extends Activity {

	NhanVienAdapter adapter;
	Button back;
	ListView listNhanVien;
	ArrayList<NhanVien> nhanViens = new ArrayList<NhanVien>();
	NhanVien nhanvienselected;
	PhongBan phongBan = new PhongBan();
	int positon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_danh_sach_nhan_vien);

		back = (Button) findViewById(R.id.btnBack);
		listNhanVien = (ListView) findViewById(R.id.listNhanVien);

		registerForContextMenu(listNhanVien);
		listNhanVien.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				nhanvienselected = nhanViens.get(arg2);
				positon = arg2;
				return false;
			}
		});

		getDataFormMain();
		addEvent();

	}

	private void updateMain() {
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("Phongban", phongBan);
		intent.putExtra("data", bundle);
		setResult(MainActivity.resultcode_updatethanhcong, intent);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.context_menu_nhanvien, menu);
		super.onCreateContextMenu(menu, v, menuInfo);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.suaNhanvien:
			suaNhanvien();
			break;

		case R.id.chuyenPhongban:
			chuyenPhongban();
			break;
		case R.id.xoaNv:
			xoaNhanvien();
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void xoaNhanvien() {
		nhanViens.remove(nhanvienselected);
		adapter.notifyDataSetChanged();

	}

	private void chuyenPhongban() {
		Intent intent = new Intent(this, ChuyenPhongActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("Nhanvien", nhanvienselected);
		intent.putExtra("data", bundle);
		nhanViens.remove(nhanvienselected);
		adapter.notifyDataSetChanged();
		startActivityForResult(intent, MainActivity.requestcode_chuyennhanvien);
	}

	private void suaNhanvien() {
		Intent intent = new Intent(this, ThemNhanVienActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("Nhanvien", nhanvienselected);
		intent.putExtra("data", bundle);
		startActivityForResult(intent, MainActivity.requestcode_suanhanvien);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case MainActivity.resultcode_nhapnvthanhcong:
			Bundle bundle = data.getBundleExtra("data");
			NhanVien nVien = (NhanVien) bundle.getSerializable("Nhanvien");
			nhanViens.set(positon, nVien);
			adapter.notifyDataSetChanged();
			break;

		case MainActivity.resultcode_chuyennhanvienthanhcong:
			Bundle bundle2 = data.getBundleExtra("data");
			PhongBan phongBan = (PhongBan) bundle2.getSerializable("Phongban");
			adapter.notifyDataSetChanged();
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void addEvent() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				updateMain();
				finish();
			}
		});

	}

	private void getDataFormMain() {
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("data");
		phongBan = (PhongBan) bundle.getSerializable("Phongban");
		nhanViens = phongBan.getListNhanvien();

		adapter = new NhanVienAdapter(this, R.layout.item_nhanvien, nhanViens);
		listNhanVien.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.danh_sach_nhan_vien, menu);
		return true;
	}

}
