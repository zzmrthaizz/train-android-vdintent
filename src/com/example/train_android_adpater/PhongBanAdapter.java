package com.example.train_android_adpater;

import java.util.ArrayList;

import javax.crypto.interfaces.PBEKey;

import com.example.train_android_vdintent.NhanVien;
import com.example.train_android_vdintent.R;

import android.R.integer;
import android.app.Activity;
import android.hardware.Camera.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.train_android_vdintent.PhongBan;

public class PhongBanAdapter extends ArrayAdapter<PhongBan> {

	Activity context = null;
	ArrayList<PhongBan> phongBans = null;
	int layout;

	public PhongBanAdapter(Activity context, int resource,
			ArrayList<PhongBan> objects) {
		super(context, resource, objects);
		this.context = context;
		this.phongBans = objects;
		this.layout = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = context.getLayoutInflater().inflate(layout, null);

		TextView phongban = (TextView) convertView.findViewById(R.id.pb);
		TextView truongphong = (TextView) convertView.findViewById(R.id.tphong);
		TextView phophong = (TextView) convertView.findViewById(R.id.pphong);

		PhongBan pb = phongBans.get(position);
		phongban.setText(pb.getNamePhong() + "------Co " + pb.coutnv() + " nv");
		truongphong.setText("Truong phong: [Chua co]");
		NhanVien nv = pb.getTphong();
		if (pb.getTphong() != null) {
			truongphong.setText(nv.getName());
		}
		ArrayList<NhanVien> listpphong = new ArrayList<NhanVien>();
		listpphong = pb.getPphong();
		if (listpphong == null) {
			phophong.setText("Pho phong: [Chua co]");
		} else {
			String pp = "Pho phong: \n";
			for (int i = 0; i < listpphong.size(); i++) {

				pp += (i + 1) + listpphong.get(i).getName() + "\n";

			}
			phophong.setText(pp);

		}

		return convertView;
	}
}
