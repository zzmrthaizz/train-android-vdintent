package com.example.train_android_adpater;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.example.train_android_vdintent.NhanVien;
import com.example.train_android_vdintent.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
	Activity context;
	int resource;
	ArrayList<NhanVien> nhanViens;

	public NhanVienAdapter(Activity context, int resource,
			ArrayList<NhanVien> objects) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
		this.nhanViens = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = context.getLayoutInflater().inflate(resource, null);

		TextView ten = (TextView) convertView.findViewById(R.id.viewTen);
		TextView chucvu = (TextView) convertView.findViewById(R.id.viewChucvu);
		TextView gioitinh = (TextView) convertView
				.findViewById(R.id.viewGioitinh);

		NhanVien nVien = nhanViens.get(position);

		ten.setText(nVien.getName() + "");
		chucvu.setText(nVien.getChucvu().getChucVu());
		if (nVien.isGioitinh()) {
			gioitinh.setText("Nam");
		} else {
			gioitinh.setText("Nu");
		}

		return convertView;
	}
}
