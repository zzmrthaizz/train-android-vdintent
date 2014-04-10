package com.example.train_android_vdintent;

import java.util.ArrayList;

import com.example.MySQLiteHelper;

import android.os.Bundle;
import android.R.anim;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RadioButton;

public class DatChucVuActivity extends Activity {

	PhongBan phongBan;
	ArrayList<NhanVien> nhanViens = new ArrayList<NhanVien>();
	ArrayAdapter<NhanVien> adapterTruongphong;
	ArrayAdapter<NhanVien> adapterPhophong;
	ListView listTruongphong;
	ListView listPhophong;
	Button ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dat_chuc_vu);

		getFromWidgets();

		getDataFromMain();
		addEvent();

	}

	private void addEvent() {
		listTruongphong.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				nhanViens.get(arg2).setChucvu(ChucVu.TruongPhong);

			}
		});
		applyChange();
		listPhophong.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				nhanViens.get(arg2).setChucvu(ChucVu.PhoPhong);

			}
		});

	}

	private void applyChange() {
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				Bundle bundle = new Bundle();
				bundle.putSerializable("Phongban", phongBan);
				intent.putExtra("data", bundle);
				setResult(MainActivity.resultcode_datchuvuthanhcon, intent);
				finish();

			}
		});

	}

	private void getFromWidgets() {
		listTruongphong = (ListView) findViewById(R.id.listTruongphong);
		listPhophong = (ListView) findViewById(R.id.listPhophong);
		listTruongphong.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listPhophong.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		ok = (Button) findViewById(R.id.okChucvu);

	}

	private void getDataFromMain() {

		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("data");
		phongBan = (PhongBan) bundle.getSerializable("Phongban");
		nhanViens = phongBan.getListNhanvien();
		adapterTruongphong = new ArrayAdapter<NhanVien>(this,
				android.R.layout.simple_list_item_single_choice, nhanViens);
		adapterPhophong = new ArrayAdapter<NhanVien>(this,
				android.R.layout.simple_list_item_multiple_choice, nhanViens);
		listTruongphong.setAdapter(adapterTruongphong);
		listPhophong.setAdapter(adapterPhophong);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dat_chuc_vu, menu);
		return true;
	}

}
