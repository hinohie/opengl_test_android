package com.cglab.testdrawer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;
import android.webkit.WebView.FindListener;
import android.widget.SeekBar;

public abstract class Brush {
	protected FloatBuffer vertexBuffer = null; 	
	protected ShortBuffer indicesBuffer = null; 
	protected FloatBuffer colorBuffer = null;
	
	protected Random rand;
    public ArrayList<Particle> target;
    public float scale;
    
    public Brush(){

		rand = new Random();	
    	target = new ArrayList<Particle>();
    }
	    
    protected SeekBar r_bar;
    protected SeekBar t_bar;
	    
    protected FloatBuffer m_VertexData;
    protected FloatBuffer m_NormalData;
    protected FloatBuffer m_TextureData;
    protected FloatBuffer m_ColorData;
    protected int buf_size;

	protected boolean semapho = false;
	
	public void mutexP()
	{
		while(semapho);
			semapho = true;
	}
	public void mutexV()
	{
		semapho = false;
	}
	public void cleartarget(){
		int i, j;
		target.clear();
	}
	public abstract void draw(GL10 gl);
	public abstract void setStartPoint(vector2f s);
	public abstract void setMovingPoint(vector2f m);
	public void setSeekbar(SeekBar r, SeekBar t) {
		// TODO Auto-generated method stub
			r_bar = r;
			t_bar = t;
	}
    protected FloatBuffer makeFloatBuffer(float[] arr) {
		// TODO Auto-generated method stub
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);	
		return fb;
	}
    protected static int[][] textures = new int[2][];
    public static void loadGLTexture(GL10 gl, Context context) {
		// loading texture
    	Bitmap.Config config = Bitmap.Config.ARGB_8888;
    	int[] snow_image = {R.drawable.snow1,R.drawable.snow2,R.drawable.snow3,R.drawable.snow4,R.drawable.snow5};
    	int[] light_image = {R.drawable.lightning1, R.drawable.lightning2, R.drawable.lightning3, R.drawable.lightning4};
    	textures[0] = new int[snow_image.length];
		gl.glGenTextures(snow_image.length, textures[0], 0);
    	for(int i=0; i<snow_image.length; i++)
    	{
			Bitmap bitmap;
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					snow_image[i]);
	
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0][i]);
	
			// create nearest filtered texture
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	
			//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
	//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
	//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
	
			// Use Android GLUtils to specify a two-dimensional texture image from our bitmap 
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
			// Clean up
			bitmap.recycle();
    	}
    	
    	textures[1] = new int[light_image.length];
		gl.glGenTextures(light_image.length, textures[1], 0);
    	for(int i=0; i<light_image.length; i++)
    	{
			Bitmap bitmap;
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					light_image[i]);
	
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[1][i]);
	
			// create nearest filtered texture
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	
			//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
	//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
	//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
	
			// Use Android GLUtils to specify a two-dimensional texture image from our bitmap 
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
			// Clean up
			bitmap.recycle();
    	}
    	
    //	loadBackground(gl, context);
	}

    /*
    public static int bgi_n = 100;
    public static int bgi_m = 100;
    protected static int[] background;
    public static void loadBackground(GL10 gl, Context context)
    {
    	Bitmap bitmap = Background.image;
    	vector2f center = Background.center;
    	float width = Background.width;
    	float height = Background.height;
    	float dx = height / bgi_n;
    	float dy = width / bgi_m;
    	int i, j;
    	
    	background = new int[bgi_n * bgi_m];
		gl.glGenTextures(bgi_n * bgi_m, background, 0);
    	for(i=0; i<bgi_n; i++)
    		for(j=0; j<bgi_m; j++)
    		{
    			float x = i * dx;
    			float y = j * dy;
    			Bitmap semi_b = Bitmap.createBitmap(bitmap, (int)y, (int)x, (int)dy, (int)dx);
    			gl.glBindTexture(GL10.GL_TEXTURE_2D, background[bgi_m * i + j]);
    			
    			// create nearest filtered texture
    			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
    			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
    	
    			//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
    	//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
    	//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
    	
    			// Use Android GLUtils to specify a two-dimensional texture image from our bitmap 
    			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, semi_b, 0);
    			// Clean up
    			semi_b.recycle();
    		}
    }
    */

}
