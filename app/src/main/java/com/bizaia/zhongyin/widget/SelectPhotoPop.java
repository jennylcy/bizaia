package com.bizaia.zhongyin.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bizaia.zhongyin.R;


@SuppressLint("ViewConstructor")
public class SelectPhotoPop extends PopupWindow {
	private View mMenuView;
	private TextView select_photo_camera;
	private TextView select_photo_album;
	private Button photo_cancel_button;
	private View view;

	@SuppressWarnings("deprecation")
	public SelectPhotoPop(Activity context, OnClickListener itemsOnClick) {
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = myInflater.inflate(R.layout.view_select_photo, null);
		view = (View) mMenuView.findViewById(R.id.view);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		select_photo_camera = (TextView) mMenuView.findViewById(R.id.select_photo_camera);
		select_photo_album = (TextView) mMenuView.findViewById(R.id.select_photo_album);
		photo_cancel_button = (Button) mMenuView
				.findViewById(R.id.photo_cancel_button);
		select_photo_camera.setOnClickListener(itemsOnClick);
		select_photo_album.setOnClickListener(itemsOnClick);
		photo_cancel_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		this.setContentView(mMenuView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setBackgroundDrawable(new BitmapDrawable());
		this.setAnimationStyle(R.style.PopupAnimation);
	}
	


}
