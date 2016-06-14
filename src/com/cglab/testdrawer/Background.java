package com.cglab.testdrawer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Background {

	public static Bitmap image;
	public static int[] texture;
	public static int texture_num;
	public static int texture_cnt = 4;
	
    protected static FloatBuffer m_VertexData;
    protected static FloatBuffer m_NormalData;
    protected static FloatBuffer m_TextureData;
    protected static FloatBuffer m_ColorData;
    protected static int buf_size=2;
    
    public static vector2f center;
    public static float width;
    public static float height;
    
    
	public Background()
	{
		
	}
	public static void loadGLTexture(GL10 gl, Context context) {
		// loading texture
    	Bitmap.Config config = Bitmap.Config.ARGB_8888;
    	texture = new int[texture_cnt];
		gl.glGenTextures(texture_cnt, texture, 0);
		
		int[] back_friends = {R.drawable.plant, R.drawable.coffe, R.drawable.butter, R.drawable.weddingcake};
		for(int i=0; i<texture_cnt; i++)
		{
		
		image = BitmapFactory.decodeResource(context.getResources(),
				//R.drawable.coffe);
				back_friends[i]);
	
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[i]);
	
			// create nearest filtered texture
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	
			//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
	//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
	//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
	
			// Use Android GLUtils to specify a two-dimensional texture image from our bitmap 
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, image, 0);
		// Clean up
			image.recycle();
		}
	}
	
	public static void setCenter(vector2f c)
	{
		center = new vector2f(c.X, c.Y);
	}
	public static void setSize(float w,float h)
	{
		width = w;
		height = h;
	}
    protected static FloatBuffer makeFloatBuffer(float[] arr) {
		// TODO Auto-generated method stub
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);	
		return fb;
	}
    private static void buf_make()
    {
    	float[] vertexData;
    	float[] normalData;
    	float[] textureData;
    	float[] colorData;
    	
    	vertexData = new float[9 * buf_size];
    	normalData = new float[9 * buf_size];
    	textureData = new float[6 * buf_size];
    	colorData = new float[12 * buf_size];
    	
    	int cnt = 0;
    	
    	float rr, gg, bb;
    	rr = gg = bb = 0.4f;
    	
    	vertexData[9*cnt + 0] = center.X - width/2;
    	vertexData[9*cnt + 1] = center.Y - height/2;
    	vertexData[9*cnt + 2] = -0.001f;
    	vertexData[9*cnt + 3] = center.X - width/2;
    	vertexData[9*cnt + 4] = center.Y + height/2;
    	vertexData[9*cnt + 5] = -0.001f;
    	vertexData[9*cnt + 6] = center.X + width/2;
    	vertexData[9*cnt + 7] = center.Y + height/2;
    	vertexData[9*cnt + 8] = -0.001f;
    	
    	textureData[6*cnt + 0] = 0.0f;
    	textureData[6*cnt + 1] = 1.0f;
    	textureData[6*cnt + 2] = 0.0f;
    	textureData[6*cnt + 3] = 0.0f;
    	textureData[6*cnt + 4] = 1.0f;
    	textureData[6*cnt + 5] = 0.0f;
    	
    	normalData[9*cnt + 0] = normalData[9*cnt + 3] = normalData[9*cnt + 6] = 0.0f;
    	normalData[9*cnt + 1] = normalData[9*cnt + 4] = normalData[9*cnt + 7] = 0.0f;
    	normalData[9*cnt + 2] = normalData[9*cnt + 5] = normalData[9*cnt + 8] = 1.0f;

    	colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = rr;
    	colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = gg;
    	colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = bb;
    	colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 0.3f;
    	
    	cnt++;
    	vertexData[9*cnt + 0] = center.X + width/2;
    	vertexData[9*cnt + 1] = center.Y + height/2;
    	vertexData[9*cnt + 2] = -0.001f;
    	vertexData[9*cnt + 3] = center.X - width/2;
    	vertexData[9*cnt + 4] = center.Y - height/2;
    	vertexData[9*cnt + 5] = -0.001f;
    	vertexData[9*cnt + 6] = center.X + width/2;
    	vertexData[9*cnt + 7] = center.Y - height/2;
    	vertexData[9*cnt + 8] = -0.001f;
    	
    	textureData[6*cnt + 0] = 1.0f;
    	textureData[6*cnt + 1] = 0.0f;
    	textureData[6*cnt + 2] = 0.0f;
    	textureData[6*cnt + 3] = 1.0f;
    	textureData[6*cnt + 4] = 1.0f;
    	textureData[6*cnt + 5] = 1.0f;
    	
    	normalData[9*cnt + 0] = normalData[9*cnt + 3] = normalData[9*cnt + 6] = 0.0f;
    	normalData[9*cnt + 1] = normalData[9*cnt + 4] = normalData[9*cnt + 7] = 0.0f;
    	normalData[9*cnt + 2] = normalData[9*cnt + 5] = normalData[9*cnt + 8] = 1.0f;

    	colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = rr;
    	colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = gg;
    	colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = bb;
    	colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 0.3f;
    	
    	cnt++;
    	

		m_VertexData = makeFloatBuffer(vertexData);
		m_ColorData = makeFloatBuffer(colorData);
		m_TextureData = makeFloatBuffer(textureData);
		m_NormalData = makeFloatBuffer(normalData);
    }
	public static void draw(GL10 gl)
	{
		gl.glMatrixMode(GL10.GL_MODELVIEW);
    	buf_make();
    	
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[texture_num]);
    	
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
	    gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		gl.glNormalPointer(GL10.GL_FLOAT, 0, m_NormalData); //1
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, m_VertexData);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, m_ColorData);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0,  m_TextureData);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, buf_size*3);
		
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    
	    gl.glDisable(GL10.GL_TEXTURE_2D);
	    gl.glDisable(GL10.GL_BLEND);

	    Update();
	}
	
static	   long start_Time=-1;
static		long end_Time;
static		long timer = 0;
	static	long elapse_time = 10000;
	private static void Update()
	{
		end_Time = System.currentTimeMillis();
		if(start_Time<0)
		{
			start_Time = System.currentTimeMillis();
			timer = 0;
		}
		else
		{
			long dt = end_Time - start_Time;
			start_Time = System.currentTimeMillis();
			timer += dt;
			if(timer > elapse_time)
			{
				//timer -= elapse_time;
				timer = 0;
				texture_num = (texture_num + 1)%texture_cnt;
			}
		}
	}
}
