package com.cglab.testdrawer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import android.opengl.GLSurfaceView;

public class MainActivity extends Activity {

	private MyGLSurfaceView mGLView;
	private Button btn_0;
	private Button btn_1;
	private Button btn_2;
	private Button btn_3;
	private Button btn_4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mGLView = (MyGLSurfaceView) findViewById(R.id.show);
		mGLView.getHolder().setFormat(PixelFormat.RGBA_8888);
		SeekBar r_bar = (SeekBar) findViewById(R.id.seek_radius);
		SeekBar t_bar = (SeekBar) findViewById(R.id.seek_timer);

		btn_1 = (Button) findViewById(R.id.btn_balloom);
		btn_2 = (Button) findViewById(R.id.btn_thunder);
		btn_4 = (Button) findViewById(R.id.btn_sand);

		btn_1.setOnClickListener(mClickT1);
		btn_2.setOnClickListener(mClickT1);
		btn_4.setOnClickListener(mClickT1);
		
		r_bar.setMax(20);
		r_bar.setProgress(6);
		
		t_bar.setMax(10);
		t_bar.setProgress(3);
		
		mGLView.setSeekbar(r_bar, t_bar);
	}
	Button.OnClickListener mClickT1 = (new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_balloom:
				mGLView.changeState(0);
				btn_1.setEnabled(false);
				btn_2.setEnabled(true);
				btn_4.setEnabled(true);
				break;
			case R.id.btn_thunder:
				mGLView.changeState(1);
				btn_1.setEnabled(true);
				btn_2.setEnabled(false);
				btn_4.setEnabled(true);
				break;
			case R.id.btn_sand:
				mGLView.changeState(3);
				btn_1.setEnabled(true);
				btn_2.setEnabled(true);
				btn_4.setEnabled(false);
				break;
			}
		}
	});

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
