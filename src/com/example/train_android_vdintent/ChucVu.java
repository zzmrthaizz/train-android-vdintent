package com.example.train_android_vdintent;

public enum ChucVu {
	TruongPhong("Truong phong"), PhoPhong("Pho phong"), NhanVien("Nhan vien");

	private String cv;

	ChucVu(String cv) {
		this.cv = cv;
	}
	public String getChucVu() {
		return this.cv;
	}

}
