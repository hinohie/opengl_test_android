package com.cglab.testdrawer;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;

public class ConfigActivity extends Activity{

	private MyGLSurfaceView_2 mGLView_2;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mGLView_2 = (MyGLSurfaceView_2) findViewById(R.id.draw);
		mGLView_2.getHolder().setFormat(PixelFormat.RGBA_8888);
	}
}
