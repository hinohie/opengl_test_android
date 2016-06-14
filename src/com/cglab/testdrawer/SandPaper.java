package com.cglab.testdrawer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class SandPaper {

	int n = 70;
	int m = 64;
	
	float max_dep = 0.3f;
	float min_x;
	float min_y;
	float dx = 0.09f;
	float dy = 0.09f;
	
	float scale = 0.0005f;
	
	float[][] map;
	float[][] dap;
	
	private vector2f temp;
	
	public void map_inil()
	{
		int i, j;
		for(i=0; i<n; i++)
			for(j=0; j<m; j++)
				map[i][j] = 0;
		min_x = - m/2 * dx;
		min_y = - n/2 * dy;
	}
	public SandPaper()
	{
		map = new float[n][];
		dap = new float[n][];
		for(int i=0; i<n; i++)
		{
			map[i] = new float[m];
			dap[i] = new float[m];
		}
		
		map_inil();
		buf_size = n*m*4;
	}
	public static float get_dist(vector2f l1, vector2f l2, vector2f p)
	{
		vector2f l = l2.minus(l1);
		vector2f r = new vector2f(l.Y, -l.X);
		r.normal();
		float ll = l.dist();
		float rr = r.dist();
		
		vector2f q2 = p.minus(l1);
		vector2f q = new vector2f(q2.X * r.X + q2.Y * r.Y, - q2.X * r.Y + q2.Y * r.X);
		
	//	Log.i("init",""+l.X+" "+l.Y +" "+q2.X+" "+q2.Y +" "+q.X+" "+q.Y +" ");
		
		if(q.Y < 0)
		{
			return l1.dist(p);
		}
		else if(q.Y > ll)
		{
			return l2.dist(p);
		}
		

		
		return Math.abs(q.X);
	}
	public void cleartarget()
	{
		int i, j;
		for(i=0; i<n; i++)
			for(j=0;j<m; j++)
			{
				map[i][j] = 0;
				dap[i][j] = 0.0f;
			}
	}
	public void attack(vector2f s, int use_cache)
	{
		int i=0, j=0;
		float sx, sy;
		float tx, ty;
		
		float max = 0.0f;
		
		if(use_cache >0)
			if(s.dist(temp) < 0.2f)
				return;
		
	//	Log.i("init","x = "+s.X +" y = "+s.Y);
		
		int min_i = 0, max_i = n;
		int min_j = 0, max_j = m;
		
		int di;
		
		di = 20;
		while(di>4)
		{
			for(i=min_i; i<max_i; i+=di)
			{
				sy = i*dy + min_y;
				ty = (i+di)*dy + min_y;
				if(s.Y >= sy && s.Y < ty)
				{
					for(j=min_j; j<max_j; j+=di)
					{
						sx = j*dx + min_x;
						tx = (j+di)*dx + min_x;
						if(s.X >= sx && s.X < tx)
							break;
					}
					break;
				}
			}
			min_i = i<di?0:i-di;
			min_j = j<di?0:j-di;
			max_i = i+di*2>n?n:i+di*2;
			max_j = j+di*2>m?m:j+di*2;
			
			di /= 4;
		}
		if(use_cache > 0)
		{
		di = 20;
		int temp_min_i = 0;
		int temp_min_j = 0;
		int temp_max_i = n;
		int temp_max_j = m;
		while(di>4)
		{
			for(i=temp_min_i; i<temp_max_i; i+=di)
			{
				sy = i*dy + min_y;
				ty = (i+di)*dy + min_y;
				if(temp.Y >= sy && temp.Y < ty)
				{
					for(j=temp_min_j; j<temp_max_j; j+=di)
					{
						sx = j*dx + min_x;
						tx = (j+di)*dx + min_x;
						if(temp.X >= sx && temp.X < tx)
							break;
					}
					break;
				}
			}
			temp_min_i = i<di?0:i-di;
			temp_min_j = j<di?0:j-di;
			temp_max_i = i+di*2>n?n:i+di*2;
			temp_max_j = j+di*2>m?m:j+di*2;
			
			di /= 4;
		}
		
		if(min_i > temp_min_i)
			min_i = temp_min_i;
		if(max_i < temp_max_i)
			max_i = temp_max_i;
		if(min_j > temp_min_j)
			min_j = temp_min_j;
		if(max_j < temp_max_j)
			max_j = temp_max_j;
		}
		
	//	Log.i("init","i = "+min_i + " " + max_i + " j = "+min_j + " " + max_j);
			
		for(i=min_i; i<max_i; i++)
		{
			ty = i*dy + min_y;
			for(j=min_j; j<max_j; j++)
			{
				tx = j*dx + min_x;
				vector2f t  = new vector2f(tx, ty);
				float r;
				if(use_cache > 0)
					r = get_dist(temp,s,t);
				else
				//	r = s.dist(t);
					r = 999;
				
				
				if(r < 0.2)
					dap[i][j] = -100f * (1.5f + (float)(Math.sqrt(0.2 - r)/Math.sqrt(0.2)));
				else if(r < 0.25)
					dap[i][j] = -1500000 *(r-0.25f)*(r-0.21f)/5;
				else
					dap[i][j] = 0.0f;
				
				/*
				if(r < 0.2)
				{		map[i][j] = -250 * scale;
				
				}
				else if(r < 0.3)
				{
					if(map[i][j] > - 1 * scale && map[i][j] < 1.0 * scale)
					{
						map[i][j] = (400*(r - 0.2f)/(0.10f)  - 250)* scale;
					}
					if(map[i][j] > - 100 * scale)
					{
						map[i][j] = 150 * scale;
					}
				}
				*/
//				Log.i("init","x = "+i+" y = "+ j + " = "+r + " = "+((r*r - dep) * Math.exp(-r*r/dep/2)));'
				
		//		map[i][j] = 0;
			}
		}
		temp = s;
		for(i=min_i; i<max_i; i++)
			for(j=min_j; j<max_j; j++)
			{
				map[i][j] += dap[i][j] * scale;
				if(map[i][j] > max_dep)
					map[i][j] = max_dep;
				else if(map[i][j] < -max_dep)
					map[i][j] = -max_dep;
			}
//		Log.i("init","dd"+max);
	}
	
	private FloatBuffer m_VertexData;
	private FloatBuffer m_NormalData;
	private FloatBuffer m_ColorData;
	private int buf_size;
	
	private void buf_make()
	{
		float[] vertexData;
		float[] colorData;
		float[] normalData;
		
		vertexData = new float[9*buf_size];
		colorData  = new float[12*buf_size];
		normalData = new float[9*buf_size];

		int i, j;
		float sx, sy;
		float tx, ty;
		float ux, uy, uz;
		float vx, vy, vz;
		float wx, wy, wz;
		int cnt = 0;
		
		for(i=0; i<n-1; i++)
		{
			sy = i*dy + min_y;
			ty = (i+1)*dy + min_y;
			for(j=0; j<m-1; j++)
			{
				sx = j*dx + min_x;
				tx = (j+1)*dx + min_x;
				
				ux = (sx+tx)/2;
				uy = (sy+ty)/2;
				uz = (map[i][j] + map[i][j+1] + map[i+1][j] + map[i+1][j+1])/4.0f;
				/*
				vx = sx; vy = sy; vz = map[i][j];
				wx = tx; wy = sy; wz = map[i][j+1];
				vertexData[9*cnt + 0] = ux;
				vertexData[9*cnt + 1] = uy;
				vertexData[9*cnt + 2] = uz;
				vertexData[9*cnt + 3] = vx;
				vertexData[9*cnt + 4] = vy;
				vertexData[9*cnt + 5] = vz;
				vertexData[9*cnt + 6] = wx;
				vertexData[9*cnt + 7] = wy;
				vertexData[9*cnt + 8] = wz;
				
				colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = 0.8f;
				colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = 0.5f;
				colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = 0.2f;
				colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 1.0f;
				
				cnt++;
				
				vx = tx; vy = sy; vz = map[i][j+1];
				wx = tx; wy = ty; wz = map[i+1][j+1];
				vertexData[9*cnt + 0] = ux;
				vertexData[9*cnt + 1] = uy;
				vertexData[9*cnt + 2] = uz;
				vertexData[9*cnt + 3] = vx;
				vertexData[9*cnt + 4] = vy;
				vertexData[9*cnt + 5] = vz;
				vertexData[9*cnt + 6] = wx;
				vertexData[9*cnt + 7] = wy;
				vertexData[9*cnt + 8] = wz;
				
				colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = 0.8f;
				colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = 0.5f;
				colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = 0.2f;
				colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 1.0f;
				
				cnt++;
				
				vx = tx; vy = ty; vz = map[i+1][j+1];
				wx = sx; wy = ty; wz = map[i+1][j];
				vertexData[9*cnt + 0] = ux;
				vertexData[9*cnt + 1] = uy;
				vertexData[9*cnt + 2] = uz;
				vertexData[9*cnt + 3] = vx;
				vertexData[9*cnt + 4] = vy;
				vertexData[9*cnt + 5] = vz;
				vertexData[9*cnt + 6] = wx;
				vertexData[9*cnt + 7] = wy;
				vertexData[9*cnt + 8] = wz;
				
				colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = 0.8f;
				colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = 0.5f;
				colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = 0.2f;
				colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 1.0f;
				
				cnt++;
				
				vx = sx; vy = ty; vz = map[i+1][j];
				wx = sx; wy = sy; wz = map[i][j];
				vertexData[9*cnt + 0] = ux;
				vertexData[9*cnt + 1] = uy;
				vertexData[9*cnt + 2] = uz;
				vertexData[9*cnt + 3] = vx;
				vertexData[9*cnt + 4] = vy;
				vertexData[9*cnt + 5] = vz;
				vertexData[9*cnt + 6] = wx;
				vertexData[9*cnt + 7] = wy;
				vertexData[9*cnt + 8] = wz;
				
				colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = 0.8f;
				colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = 0.5f;
				colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = 0.2f;
				colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 1.0f;
				
				cnt++;
				*/
				
				vertexData[6*cnt + 0] = sx;
				vertexData[6*cnt + 1] = sy;
				vertexData[6*cnt + 2] = map[i][j];
				vertexData[6*cnt + 3] = ux;
				vertexData[6*cnt + 4] = uy;
				vertexData[6*cnt + 5] = uz;
				
				colorData[8*cnt + 0] = colorData[8*cnt + 4] = 0.8f;
				colorData[8*cnt + 1] = colorData[8*cnt + 5] = 0.5f;
				colorData[8*cnt + 2] = colorData[8*cnt + 6] = 0.2f;
				colorData[8*cnt + 3] = colorData[8*cnt + 7] = 1.0f;
				
				cnt++;
				
				vertexData[6*cnt + 0] = ux;
				vertexData[6*cnt + 1] = uy;
				vertexData[6*cnt + 2] = uz;
				vertexData[6*cnt + 3] = tx;
				vertexData[6*cnt + 4] = ty;
				vertexData[6*cnt + 5] = map[i+1][j+1];
				
				colorData[8*cnt + 0] = colorData[8*cnt + 4] = 0.8f;
				colorData[8*cnt + 1] = colorData[8*cnt + 5] = 0.5f;
				colorData[8*cnt + 2] = colorData[8*cnt + 6] = 0.2f;
				colorData[8*cnt + 3] = colorData[8*cnt + 7] = 1.0f;
				
				cnt++;

				vertexData[6*cnt + 0] = tx;
				vertexData[6*cnt + 1] = ty;
				vertexData[6*cnt + 2] = map[i+1][j+1];
				vertexData[6*cnt + 3] = tx;
				vertexData[6*cnt + 4] = sy;
				vertexData[6*cnt + 5] = map[i][j+1];
				
				colorData[8*cnt + 0] = colorData[8*cnt + 4] = 0.8f;
				colorData[8*cnt + 1] = colorData[8*cnt + 5] = 0.5f;
				colorData[8*cnt + 2] = colorData[8*cnt + 6] = 0.2f;
				colorData[8*cnt + 3] = colorData[8*cnt + 7] = 1.0f;
				
				cnt++;
				
				vertexData[6*cnt + 0] = tx;
				vertexData[6*cnt + 1] = sy;
				vertexData[6*cnt + 2] = map[i][j+1];
				vertexData[6*cnt + 3] = ux;
				vertexData[6*cnt + 4] = uy;
				vertexData[6*cnt + 5] = uz;
				
				colorData[8*cnt + 0] = colorData[8*cnt + 4] = 0.8f;
				colorData[8*cnt + 1] = colorData[8*cnt + 5] = 0.5f;
				colorData[8*cnt + 2] = colorData[8*cnt + 6] = 0.2f;
				colorData[8*cnt + 3] = colorData[8*cnt + 7] = 1.0f;
				
				cnt++;

				vertexData[6*cnt + 0] = ux;
				vertexData[6*cnt + 1] = uy;
				vertexData[6*cnt + 2] = uz;
				vertexData[6*cnt + 3] = sx;
				vertexData[6*cnt + 4] = ty;
				vertexData[6*cnt + 5] = map[i+1][j];
				
				colorData[8*cnt + 0] = colorData[8*cnt + 4] = 0.8f;
				colorData[8*cnt + 1] = colorData[8*cnt + 5] = 0.5f;
				colorData[8*cnt + 2] = colorData[8*cnt + 6] = 0.2f;
				colorData[8*cnt + 3] = colorData[8*cnt + 7] = 1.0f;
				
				cnt++;

				vertexData[6*cnt + 0] = sx;
				vertexData[6*cnt + 1] = ty;
				vertexData[6*cnt + 2] = map[i+1][j];
				vertexData[6*cnt + 3] = tx;
				vertexData[6*cnt + 4] = ty;
				vertexData[6*cnt + 5] = map[i+1][j+1];
				
				colorData[8*cnt + 0] = colorData[8*cnt + 4] = 0.8f;
				colorData[8*cnt + 1] = colorData[8*cnt + 5] = 0.5f;
				colorData[8*cnt + 2] = colorData[8*cnt + 6] = 0.2f;
				colorData[8*cnt + 3] = colorData[8*cnt + 7] = 1.0f;
				
				cnt++;
			}
		}

		
		
		m_VertexData = makeFloatBuffer(vertexData);
		m_ColorData = makeFloatBuffer(colorData);
		m_NormalData = makeFloatBuffer(normalData);
		
	}
    private FloatBuffer makeFloatBuffer(float[] arr) {
		// TODO Auto-generated method stub
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);	
		return fb;
	}
	
	public void draw(GL10 gl){
		gl.glMatrixMode(GL10.GL_MODELVIEW);
    	buf_make();
    	
	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		gl.glNormalPointer(GL10.GL_FLOAT, 0, m_NormalData); //1
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, m_VertexData);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, m_ColorData);
		gl.glDrawArrays(GL10.GL_LINES, 0, buf_size*3);
		
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	    
	    Update();
	}
	
    long start_Time=-1;
	long end_Time;
	float speed = 0.003f;
	
	void Update()
	{
		end_Time = System.currentTimeMillis();
		if(start_Time<0)
			start_Time = System.currentTimeMillis();
		else
		{
			long dt = end_Time - start_Time;
			start_Time = System.currentTimeMillis();
		}
	}
}
