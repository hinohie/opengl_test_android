package com.cglab.testdrawer;

import java.util.Random;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import android.widget.SeekBar;

public class MyRenderer implements Renderer {
	private float MOVE_DIST_MIN = 0.07f;
	
	private Context context;
	
//	public Sketch sketch;
	public Brush[] sketch;
	public SandPaper paper;
	public int sketch_type;

	protected SeekBar r_bar;
	protected SeekBar t_bar;
	
	public MyRenderer(Context context)
	{
		this.context = context;
	}

	public void inil()
	{
	//	sketch = new Sketch();
		sketch = new Brush[3];
		sketch[0] = new Sketch();
		sketch[1] = new Thunder();
		sketch[2] = new Icy();
		
		sketch[0].scale = scale;
		sketch[1].scale = scale;
		sketch[2].scale = scale;
		paper = new SandPaper();
		Log.i("inil","Created");
	}
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{ 
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
		gl.glEnable(GL10.GL_DEPTH_TEST);
		
		Brush.loadGLTexture(gl, this.context);
		Background.loadGLTexture(gl, this.context);
		
	}

	   /* Called if the geometry of the view changes,
	    *  for example when the device's screen orientation changes.*/
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{ 
		gl.glViewport(0, 0, width, height); 
		float aspect = (float)width / height;
		float aaa = 1f;
		gl.glMatrixMode(GL10.GL_PROJECTION); 
		gl.glLoadIdentity();
		gl.glFrustumf(-aspect * aaa, aspect * aaa, -1.0f * aaa, 1.0f * aaa, 1.0f * aaa, 100.0f);
		
		X_min = 0.0f; X_max = width;
		Y_min = height; Y_max = 0.0f;
		VX_min = -aspect * scale; VX_max = aspect * scale;
		VY_min = -1.0f * scale; VY_max = 1.0f * scale;
		
		X_angle = 0;
		Y_angle = 0;
		
		this.width = VX_max - VX_min;
		this.height = VY_max - VY_min;

		Background.setCenter(new vector2f(0,0));
		Background.setSize(this.width, this.height);
	//	Brush.loadBackground(gl, context);
	}
	public int[] getConfigSpec() {
            int[] configSpec = {
                    EGL10.EGL_RED_SIZE,      8,
                    EGL10.EGL_GREEN_SIZE,    8,
                    EGL10.EGL_BLUE_SIZE,     8,
                    EGL10.EGL_ALPHA_SIZE,    8,
                    EGL10.EGL_DEPTH_SIZE,   16,
                    EGL10.EGL_NONE
            };
            return configSpec;
	}

	public float X_angle;
	public float Y_angle;
	
	public float scale = 3.0f;
	public float X_min, X_max;
	public float Y_min, Y_max;
	public float VX_min, VX_max;
	public float VY_min, VY_max;
	public float width, height;

	   /* Called for each redraw of the view. */
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); 
		gl.glMatrixMode(GL10.GL_MODELVIEW); 
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	    gl.glLoadIdentity();
	    gl.glTranslatef(0.0f, 0.0f, -1.0f * scale);
/*	    
	    final float[] ambient = {0, 0, 0, 1};
		final float[] specular = {1, 1, 1, 1};
		final float[] diffuse = {1, 1, 1, 1};
		final float[] position = {1f/2 * width, 1f/2 * height, -1f/10 * height, 1};
//		final float[] direction = {-1, -1, -1};
//		final float cutoff = 30;
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT1);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, specular, 0);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, position, 0);
*/
	//	gl.glRotatef(Y_angle, -1, 0, 0);
	//	gl.glRotatef(X_angle, 0, 1, 0);

	   // Triangle tr = new Triangle();
	   // tr.draw(gl);

//	    Background.draw(gl);
	    
	    if(sketch_type == 3)
	    {
	    	paper.draw(gl);
	    }
	    else
	    {
	    	if(sketch_type == 0)
	    		Background.draw(gl);
			sketch[sketch_type].mutexP();
			sketch[sketch_type].draw(gl);
			sketch[sketch_type].mutexV();
	    }
//		paper.draw(gl);
	} 
	
	public void setStartPoint(vector2f s)
	{
		if(sketch_type == 3)
		{
		paper.attack(s, 0);
		}
		else
		{
		sketch[sketch_type].mutexP();
		sketch[sketch_type].setStartPoint(s);
		sketch[sketch_type].mutexV();
		}
		//paper.attack(s, 10f);
	}
	public void setMovingPoint(vector2f m)
	{
		if(sketch_type == 3)
		{
		paper.attack(m, 1);
		}
		
		else
		{
		sketch[sketch_type].mutexP();
		sketch[sketch_type].setMovingPoint(m);
		sketch[sketch_type].mutexV();
		}
	}

	public void setSeekbar(SeekBar r, SeekBar t) {
		// TODO Auto-generated method stub
		r_bar = r;
		t_bar = t;
		sketch[0].setSeekbar(r, t);
		sketch[1].setSeekbar(r, t);
		sketch[2].setSeekbar(r, t);
	}
	public void changeState(int ns)
	{
		int s = sketch_type;
		if(s != 3)
		{
			sketch[s].mutexP();
			sketch[s].cleartarget();
			sketch_type = ns;
			sketch[s].mutexV();
		}
		else
		{
			paper.cleartarget();
			sketch_type = ns;
		}
	}
}
