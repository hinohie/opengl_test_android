package com.cglab.testdrawer;

import java.text.AttributedCharacterIterator.Attribute;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.EGLConfig;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class MyGLSurfaceView_2 extends GLSurfaceView{

	MyRenderer_2 render;
	public MyGLSurfaceView_2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		render = new MyRenderer_2(context);
		setRenderer(render);
	}

	public MyGLSurfaceView_2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		render = new MyRenderer_2(context);
		render.inil();
		setRenderer(render);
	}
	
	public void setSeekbar(SeekBar r, SeekBar t)
	{
		render.setSeekbar(r,t);
	}
	public void changeState(int ns)
	{
		render.changeState(ns);
		render.sketch_type = ns;
	}


	private float sx, tx, x;
	private float sy, ty, y;
	private float get_view_x(float s)
	{
		float d = s - render.X_min;
		float e = render.X_max - render.X_min;
		float f = render.VX_max - render.VX_min;
		
		return f * d / e + render.VX_min;
	}
	private float get_view_y(float s)
	{
		float d = s - render.Y_min;
		float e = render.Y_max - render.Y_min;
		float f = render.VY_max - render.VY_min;
		
		return f * d / e + render.VY_min;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		
		switch(action)
		{
		case MotionEvent.ACTION_DOWN:
			sx = event.getX();
			sy = event.getY();
			
			tx = get_view_x(sx);
			ty = get_view_y(sy);
			
			render.setStartPoint(new vector2f(tx, ty));
			break;
		case MotionEvent.ACTION_MOVE:
			x = event.getX();
			y = event.getY();
			
			tx = get_view_x(x);
			ty = get_view_y(y);
			
//			Log.i("move","x "+x+" y "+y+" tx"+tx+" ty"+ty);
			
			render.setMovingPoint(new vector2f(tx, ty));
			
			break;
		case MotionEvent.ACTION_UP:
			return false;
		}
		return true;
	}
}