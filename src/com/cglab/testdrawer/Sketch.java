package com.cglab.testdrawer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;
import android.webkit.WebView.FindListener;
import android.widget.SeekBar;

public class Sketch extends Brush{
	protected FloatBuffer vertexBuffer = null; 	
	protected ShortBuffer indicesBuffer = null; 
	protected FloatBuffer colorBuffer = null;
    
	public boolean use_back = true;
	
	private void buf_make(vector2f s, float rrr, int detail, float ddd,vector2f t)
	{
		buf_size = detail;
		int size = buf_size + 2;
		
		float[] vertexData;
		float[] colorData;
		float[] textureData;
		float[] normalData;
		
		vertexData = new float[9*buf_size];
		colorData  = new float[12*buf_size];
		textureData = new float[6*buf_size];
		normalData = new float[9*buf_size];

		float r = rrr;
		float tx, ty;
		float i;
		if(use_back)
			i = 0;
		else
			i= (float) (rand.nextFloat() * 2 * Math.PI);
		//float i = 0;
		
		vector2f[] tt = new vector2f[size];		
		vector2f[] vv = new vector2f[size];
		int j, k;
		for(j=0;j<size; j++)
		{
			tx = (float) (r*Math.cos(i));
			ty = (float) (r*Math.sin(i));
			
			tt[j] = new vector2f((float)(Math.cos(j*2*Math.PI/size)),(float)(Math.sin(j*2*Math.PI/size)));
			vv[j] = new vector2f(s.X + tx, s.Y + ty);
			i += Math.PI * 2 / size;
		}
		
		int cnt=0;
		float rr,gg,bb;
		if(detail > 3)
		{
			rr = rand.nextFloat()/2 + 0.5f;
			gg = rand.nextFloat()/2 + 0.5f;
			bb = rand.nextFloat()/2 + 0.5f;
			
			rr = gg = bb = 1.0f;
		}
		else
		{
			float fff = ddd - rrr*2;
			if(ddd > 1.0)
				fff = 1.0f;
			else if(ddd <0)
				fff =  0;
			
			rr= gg = bb = fff;
		}
		for(j=1; j<size-1; j++)
		{
			vector2f v;
			v = vv[0];
			vertexData[9*cnt + 0] = v.X;
			vertexData[9*cnt + 1] = v.Y;
			vertexData[9*cnt + 2] = 0.0f;
			
			v = vv[j];
			vertexData[9*cnt + 3] = v.X;
			vertexData[9*cnt + 4] = v.Y;
			vertexData[9*cnt + 5] = 0.0f;
			
			v = vv[j+1];
			vertexData[9*cnt + 6] = v.X;
			vertexData[9*cnt + 7] = v.Y;
			vertexData[9*cnt + 8] = 0.0f;
			
			colorData[12*cnt + 0] =	rr;
			colorData[12*cnt + 1] = gg;
			colorData[12*cnt + 2] = bb;
			colorData[12*cnt + 3] = 1.0f;
			colorData[12*cnt + 4] = colorData[12*cnt + 8] = rr;
			colorData[12*cnt + 5] = colorData[12*cnt + 9] = gg;
			colorData[12*cnt + 6] = colorData[12*cnt + 10] = bb;
			colorData[12*cnt + 7] = colorData[12*cnt + 11] = 1.0f;
			boolean aaaa=false;
			/*
			if(aaaa)
			{
				float x;
				float y;
				
				x = ( tt[0].X/2 + 0.5f)*r + t.X;
				y = ( tt[0].Y/2 + 0.5f)*r + t.Y;
			//	Log.i("init","t x = "+x + "y = "+y);
				x = (x - (Background.center.X - Background.width/2))/Background.width;
				y = (- y + (Background.center.Y + Background.height/2))/Background.height;
			//	Log.i("init","c x = "+x + "y = "+y);
				textureData[6*cnt + 0] = x;
				textureData[6*cnt + 1] = y;
				x = ( tt[j].X/2 + 0.5f)*r + t.X;
				y = ( tt[j].Y/2 + 0.5f)*r + t.Y;
				x = (x - (Background.center.X - Background.width/2))/Background.width;
				y = (- y + (Background.center.Y + Background.height/2))/Background.height;
				textureData[6*cnt + 2] = x;
				textureData[6*cnt + 3] = y;
				x = ( tt[j+1].X/2 + 0.5f)*r + t.X;
				y = ( tt[j+1].Y/2 + 0.5f)*r + t.Y;
				x = (x - (Background.center.X - Background.width/2))/Background.width;
				y = (- y + (Background.center.Y + Background.height/2))/Background.height;
				textureData[6*cnt + 4] = x;
				textureData[6*cnt + 5] = y;
			}
			*/
			if(use_back)
			{
				float y = (- s.Y + (Background.center.Y + Background.height/2))/Background.height;
				float aaa = 3.5f;
				y = (aaa - 1)*y;
				/*
				textureData[6*cnt + 0] = (tt[0].X/2 + 0.5f) ;
				textureData[6*cnt + 1] = (y-tt[0].Y/2 + 0.5f) / aaa;
				textureData[6*cnt + 2] = (tt[j].X/2 + 0.5f) ;
				textureData[6*cnt + 3] = (y-tt[j].Y/2 + 0.5f) / aaa;
				textureData[6*cnt + 4] = (tt[j+1].X/2 + 0.5f) ;
				textureData[6*cnt + 5] = (y-tt[j+1].Y/2 + 0.5f) / aaa;
				*/

				textureData[6*cnt + 0] = 1-(tt[0].X/2 + 0.5f) ;
				textureData[6*cnt + 1] = 1-(y-tt[0].Y/2 + 0.5f) / aaa;
				textureData[6*cnt + 2] = 1-(tt[j].X/2 + 0.5f) ;
				textureData[6*cnt + 3] = 1-(y-tt[j].Y/2 + 0.5f) / aaa;
				textureData[6*cnt + 4] = 1-(tt[j+1].X/2 + 0.5f) ;
				textureData[6*cnt + 5] = 1-(y-tt[j+1].Y/2 + 0.5f) / aaa;
			}
			else
			{
				textureData[6*cnt + 0] = tt[0].X/2 + 0.5f;
				textureData[6*cnt + 1] = -tt[0].Y/2 + 0.5f;
				textureData[6*cnt + 2] = tt[j].X/2 + 0.5f;
				textureData[6*cnt + 3] = -tt[j].Y/2 + 0.5f;
				textureData[6*cnt + 4] = tt[j+1].X/2 + 0.5f;
				textureData[6*cnt + 5] = -tt[j+1].Y/2 + 0.5f;
			}
			
			normalData[9*cnt + 0] = 0.0f;
			normalData[9*cnt + 1] = 0.0f;
			normalData[9*cnt + 2] = 1.0f;
			normalData[9*cnt + 3] = 0.0f;
			normalData[9*cnt + 4] = 0.0f;
			normalData[9*cnt + 5] = 1.0f;
			normalData[9*cnt + 6] = 0.0f;
			normalData[9*cnt + 7] = 0.0f;
			normalData[9*cnt + 8] = 1.0f;
			cnt++;
		}
		
		m_VertexData = makeFloatBuffer(vertexData);
		m_ColorData = makeFloatBuffer(colorData);
		m_TextureData = makeFloatBuffer(textureData);
		m_NormalData = makeFloatBuffer(normalData);
		
	}
	private void draw_point(GL10 gl, vector2f s, float rr, float d)
    {

		gl.glMatrixMode(GL10.GL_MODELVIEW);
    	buf_make(s,rr, 1, d, s);
    	
	     gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	     gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	     //gl.glEnableClientState(GL10.GL_ALPHA);

		gl.glNormalPointer(GL10.GL_FLOAT, 0, m_NormalData); //1
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, m_VertexData);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, m_ColorData);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, buf_size*3);
		
	     gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	     gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
	private void draw_particle(GL10 gl, vector2f s, float rr, int tn,vector2f t)
    {

		gl.glMatrixMode(GL10.GL_MODELVIEW);
    	buf_make(s, rr, 9, 0, t);
    	
    	if(!use_back)
    	{
    		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0][tn]);
    	}
    	else
    	{
    		gl.glBindTexture(GL10.GL_TEXTURE_2D, Background.texture[Background.texture_num]);
    	}
    	
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
    }
	
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
	
    public void draw(GL10 gl){

    	int i, j, k;

    	j = target.size();
    	for(i=0; i<j; i++)
    	{
    		Particle p = target.get(i);
    		vector2f s = p.tar;
    		vector2f t = p.pos;
    		
    		float ddd = s.dist(t);
    		if(p.life > 1000 && ddd > p.radius * 3)
    			draw_point(gl, s, 0.03f, s.dist(t));
    		draw_particle(gl, t, p.radius,p.state, s);
    		
    	}
    	
    	Update();
	}
	
    long start_Time=-1;
	long end_Time;
	private float tdt(float x)
	{
		float y = 0.5f;
		float z = (float)Math.cos(x*19583) + (float)Math.sin(x * 251251);
		return y + 0.25f * z;
	}
	protected void Update()
	{
		int i, j, k;
		j = target.size();
		end_Time = System.currentTimeMillis();
		if(start_Time<0)
			start_Time = System.currentTimeMillis();
		else
		{
			long dt = end_Time - start_Time;
			start_Time = System.currentTimeMillis();
			
			for(i=0; i<j; i++)
			{
				Particle p = target.get(i);
				p.Update(dt);
				int dd = 10;
				int kx = i - dd;
				int ky = i + dd;
				if(kx<0)kx=0;
				if(ky>=j)ky=j;
			//	p.Force(new vector2f(rand.nextFloat()*2f - 1f, rand.nextFloat() + 0.4f));
				for(k=kx; k<ky; k++)
					if(i != k)
					{
						Particle q = target.get(k);
						vector2f t = q.pos;
						if(p.pos.dist(t) < p.radius + q.radius)
						{
							/*
							float fx, fy;
							float ff = 1.0f;
							
							vector2f w = p.pos.minus(t);
							if(w.dist() < radius/4)
								ff = 1;
							else
								ff = radius/4 / w.dist();
							
							
							fx = ff * (p.pos.X - t.X);
							fy = ff * (p.pos.Y - t.Y);
							vector2f v = new vector2f(fx, fy);
							Log.i("init","" +fx + " "+fy);
							if(v.dist() > 1.0f)
								v.normal();
							Log.i("init","" + v.dist());
							*/
							//p.Force(new vector2f(v.X, v.Y));
							
							p.Force(new vector2f((p.pos.X - t.X), (p.pos.Y - t.Y)));
						}
					}
			}
			for(i=0; i<j; i++)
			{
				Particle p = target.get(i);
				if(p.life < 0)
				{
					p.Force(new vector2f((tdt(p.seed)) - 0.5f+rand.nextFloat() * 0.8f - 0.4f, (float) (2.0f * Math.sqrt(-p.life/1000.0f))));
				}
				p.Accept();
				if(p.pos.Y > 4.0f)
				{
					target.remove(i);
					i--;
					j--;
				}
			}	
		}
    }
	
	
	@Override
	public void setStartPoint(vector2f s) {
		
		long life = (t_bar.getProgress() + 5) * 1000;
		
		Particle p = new Particle();
		p.setTar(s);
		p.setPos(new vector2f(s.X - 0.8f * s.Y / scale, -1.1f * scale));
		p.setSeed();
		p.setLife(life);
		p.state = rand.nextInt(textures[0].length);
		p.setRadius((r_bar.getProgress()/(1+0.5f*rand.nextFloat()) + 3.0f) * 0.01f);
	
		target.add(p);
	}
	@Override
	public void setMovingPoint(vector2f m) {
		// TODO Auto-generated method stub

		float noise_rate = 0.07f;
		float noise;
		long life = (t_bar.getProgress() + 5)*1000;
		int n = target.size();
		vector2f b = target.get(n-1).tar;
		
		float MOVE_DIST_MIN = 0.01f * (r_bar.getProgress() + 3);
		
		while(b.dist(m) > MOVE_DIST_MIN)
		{
			Particle p = new Particle();
			p.setTar(b);
			noise = noise_rate * (rand.nextFloat()*2 - 1);
			p.setPos(new vector2f(b.X - 0.8f * b.Y / scale, -1.1f * scale + noise));
			p.setSeed();
			p.setLife(life);
			p.setRadius((r_bar.getProgress()/(1+0.5f*rand.nextFloat()) + 3.0f) * 0.01f);
			p.state = rand.nextInt(textures[0].length);
			target.add(p);
			vector2f x = m.minus(b);
			x.normal();
			b = b.plus(x.scalarProduct(MOVE_DIST_MIN));
		}
		
	}

}
