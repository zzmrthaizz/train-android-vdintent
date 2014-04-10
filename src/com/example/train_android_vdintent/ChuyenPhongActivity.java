package com.example.train_android_vdintent;

import java.util.ArrayList;

import com.example.MySQLiteHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ChuyenPhongActivity extends Activity {
	NhanVien nv;
	Button back;
	ListView listphong;
	PhongBan phongbanselected;
	PhongBan phongbancurent;
	ArrayList<PhongBan> arrphong = new ArrayList<PhongBan>();
	ArrayAdapter<PhongBan> adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chuyen_phong);
		listphong = (ListView) findViewById(R.id.listChonphong);
		back =(Button) findViewById(R.id .btnBackchuyen);
		getDataFromDs();
		arrphong = MainActivity.getlistPhongBan();
		adapter = new ArrayAdapter<PhongBan>(this,
				android.R.layout.simple_list_item_single_choice, arrphong);
		listphong.setAdapter(adapter);
		listphong.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		addevent();
	}

	private void addevent() {
		listphong.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				phongbanselected = arrphong.get(arg2);
				phongbanselected.addnv(nv);

			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = getIntent();
				Bundle bundle = new Bundle();
				bundle.putSerializable("Phongban", phongbanselected);
				intent.putExtra("data", bundle);
				setResult(MainActivity.resultcode_chuyennhanvienthanhcong, intent);
				finish();
				
			}
		});

	}

	private void getDataFromDs() {
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("data");
		nv = (NhanVien) bundle.getSerializable("Nhanvien");
		phongbancurent = (PhongBan) bundle.getSerializable("Phongban");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// In flate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chuyen_phong, menu);
		return true;
	}

}
