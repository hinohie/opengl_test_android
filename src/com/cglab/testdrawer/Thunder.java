package com.cglab.testdrawer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class Thunder extends Brush {
	private float[] vertexData;
	private float[] colorData;
	private float[] textureData;
	private float[] normalData;
	int cnt=0;
	protected void connect_tiggle(Particle p)
	{
		vector2f s = p.pos;
		vector2f t = p.tar;
		vector2f u = p.dir;
		
		float r = p.radius;
		float rr,gg,bb;
		float tt = (float) ((s.X + 0.5) * Math.PI);
		float px = (float) (t.X + r * Math.sin(tt));
		float py = (float) (t.Y + r * Math.cos(tt));
		
		float g = p.radius * rand.nextFloat()*5.0f+ 0.5f;
		float qx = (float)(t.X + g * u.X);
		float qy = (float)(t.Y + g * u.Y);
		
		rr = (1-s.Y) * 0.2f;
		gg = (1-s.Y) * 0.3f;
		bb = (1-s.Y) * 0.8f;
		
		vertexData[9*cnt + 0] = px;
		vertexData[9*cnt + 1] = py;
		vertexData[9*cnt + 2] = 0.0f;
		vertexData[9*cnt + 3] = qx;
		vertexData[9*cnt + 4] = qy;
		vertexData[9*cnt + 5] = 0.0F;
		vertexData[9*cnt + 6] = t.X;
		vertexData[9*cnt + 7] = t.Y;
		vertexData[9*cnt + 8] = 0;
		
		colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = rr;
		colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = gg;
		colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = bb;
		colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 1.0f;
		
		textureData[6*cnt + 0] = 0.0f;
		textureData[6*cnt + 1] = 1.0f;
		textureData[6*cnt + 2] = 0.0f;
		textureData[6*cnt + 3] = 0.0f;
		textureData[6*cnt + 4] = 0.5f;
		textureData[6*cnt + 5] = 1.0f;
		
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
	
	protected void connect_thunder(vector2f t,vector2f s,float rrr, boolean cutted)
	{
		float noise_1 = rand.nextFloat();
		float noise_2 = rand.nextFloat();
		
		float r = rrr * (noise_1*0.6f - 0.3f + 1f);
		
		float tt = (float) ((s.X + 0.5) * Math.PI);
		float ttt = (float) (tt + Math.PI * (noise_2 * 0.1 + 0.9));
		
		float px = (float) (r * Math.sin(tt));
		float py = (float) (r * Math.cos(tt));
		float qx = (float) (r * Math.sin(ttt));
		float qy = (float) (r * Math.cos(ttt));
		vector2f q = new vector2f(qx+t.X, qy + t.Y);
		vector2f p = new vector2f(px+t.X, py + t.Y);
		
		float rr,gg,bb;
		
		rr = (1-s.Y) * 0.2f;
		gg = (1-s.Y) * 0.3f;
		bb = (1-s.Y) * 0.8f;
		
		if(cutted)
		{
			vertexData[9*cnt + 0] = p.X;
			vertexData[9*cnt + 1] = p.Y;
			vertexData[9*cnt + 2] = 0;
			vertexData[9*cnt + 3] = q.X;
			vertexData[9*cnt + 4] = q.Y;
			vertexData[9*cnt + 5] = 0;
			vertexData[9*cnt + 6] = p.X;
			vertexData[9*cnt + 7] = p.Y;
			vertexData[9*cnt + 8] = 0; 
			
			colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = rr;
			colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = gg;
			colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = bb;
			colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 1.0f;
			
			textureData[6*cnt + 0] = 0.0f;
			textureData[6*cnt + 1] = 0.0f;
			textureData[6*cnt + 2] = 0.0f;
			textureData[6*cnt + 3] = 1.0f;
			textureData[6*cnt + 4] = 1.0f;
			textureData[6*cnt + 5] = 0.0f;
			
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
			
			vertexData[9*cnt + 0] = p.X;
			vertexData[9*cnt + 1] = p.Y;
			vertexData[9*cnt + 2] = 0;
			vertexData[9*cnt + 3] = q.X;
			vertexData[9*cnt + 4] = q.Y;
			vertexData[9*cnt + 5] = 0;
			vertexData[9*cnt + 6] = q.X;
			vertexData[9*cnt + 7] = q.Y;
			vertexData[9*cnt + 8] = 0; 
			
			colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = rr;
			colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = gg;
			colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = bb;
			colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 1.0f;
			
			textureData[6*cnt + 0] = 1.0f;
			textureData[6*cnt + 1] = 0.0f;
			textureData[6*cnt + 2] = 0.0f;
			textureData[6*cnt + 3] = 1.0f;
			textureData[6*cnt + 4] = 1.0f;
			textureData[6*cnt + 5] = 1.0f;
			
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
		else
		{
			vertexData[9*cnt + 0] = vertexData[9*cnt - 12 + 0];
			vertexData[9*cnt + 1] = vertexData[9*cnt - 12 + 1];
			vertexData[9*cnt + 2] = vertexData[9*cnt - 12 + 2];
			vertexData[9*cnt + 3] = vertexData[9*cnt - 6 + 3];
			vertexData[9*cnt + 4] = vertexData[9*cnt - 6 + 4];
			vertexData[9*cnt + 5] = vertexData[9*cnt - 6 + 5];
			vertexData[9*cnt + 6] = p.X;
			vertexData[9*cnt + 7] = p.Y;
			vertexData[9*cnt + 8] = 0;
			

			colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = rr;
			colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = gg;
			colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = bb;
			colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 1.0f;
			
			textureData[6*cnt + 0] = 0.0f;
			textureData[6*cnt + 1] = 1.0f;
			textureData[6*cnt + 2] = 0.0f;
			textureData[6*cnt + 3] = 0.0f;
			textureData[6*cnt + 4] = 1.0f;
			textureData[6*cnt + 5] = 1.0f;
			
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
			vertexData[9*cnt + 0] = p.X;
			vertexData[9*cnt + 1] = p.Y;
			vertexData[9*cnt + 2] = 0;
			vertexData[9*cnt + 3] = vertexData[9*cnt - 15 + 3];
			vertexData[9*cnt + 4] = vertexData[9*cnt - 15 + 4];
			vertexData[9*cnt + 5] = vertexData[9*cnt - 15 + 5];
			vertexData[9*cnt + 6] = q.X;
			vertexData[9*cnt + 7] = q.Y;
			vertexData[9*cnt + 8] = 0;
			
			colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = rr;
			colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = gg;
			colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = bb;
			colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 1.0f;
			
			textureData[6*cnt + 0] = 1.0f;
			textureData[6*cnt + 1] = 1.0f;
			textureData[6*cnt + 2] = 0.0f;
			textureData[6*cnt + 3] = 0.0f;
			textureData[6*cnt + 4] = 1.0f;
			textureData[6*cnt + 5] = 0.0f;
			
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
		
	}
	
	@Override
	public void draw(GL10 gl) {
		// TODO Auto-generated method stub
		int i, j, k;

		gl.glMatrixMode(GL10.GL_MODELVIEW);
    	j = target.size();
    	k = r_bar.getProgress() + 1;
    	
    	buf_size = j * 3;

    	vertexData = new float[9*buf_size];
    	colorData  = new float[12*buf_size];
    	textureData  = new float[6*buf_size];
    	normalData = new float[9*buf_size];
    	cnt=0;
		for(i=0; i<j; i++)
		{
    		Particle p = target.get(i);
    		vector2f s = p.tar;
    		vector2f t = p.pos;
    		boolean cut = false;
    		if(i == 0) cut = true;
    		else if(i > 0 && p.tar.dist(target.get(i-1).tar) > p.radius * 2)
    			cut = true;
    		connect_thunder(s, t, p.radius,cut);
		}
		for(i=0; i<j; i++)
		{
			Particle p = target.get(i);
			if(p.seed < 0)
			{
				connect_tiggle(p);
			}
		}
		m_VertexData = makeFloatBuffer(vertexData);
		m_ColorData = makeFloatBuffer(colorData);
		m_TextureData = makeFloatBuffer(textureData);
		m_NormalData = makeFloatBuffer(normalData);

		int dd = rand.nextInt(textures[1].length);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[1][dd]);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
	    gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		gl.glNormalPointer(GL10.GL_FLOAT, 0, m_NormalData); //1
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, m_VertexData);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0,  m_TextureData);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, m_ColorData);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, buf_size*3);
		
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		
	    gl.glDisable(GL10.GL_TEXTURE_2D);
	    gl.glDisable(GL10.GL_BLEND);
	    
		Update();
	}
    long start_Time=-1;
	long end_Time;
	private void Update()
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
				p.Accept();
				p.dir = new vector2f();
				p.pos.Y += dt / 300.0f;
				if(p.pos.Y > 1.0f)
				{
					p.pos = new vector2f(p.pos.X + rand.nextFloat()*0.2f - 0.1f, rand.nextFloat() - 1);
					if(p.seed > 0 && rand.nextBoolean())
					{
						p.dir = new vector2f (rand.nextFloat()*2.0f - 1.0f, rand.nextFloat() * 2.0f - 1.0f);
						p.seed -= 4.0f;
						p.pos.Y += p.seed;
					}
					else
						p.seed += rand.nextFloat();
				}
				if(p.life < 0)
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
		// TODO Auto-generated method stub
		long life = (t_bar.getProgress() + 5) * 1000;
		
		Particle p = new Particle();
		p.setTar(s);
		p.setPos(new vector2f(rand.nextFloat(), -1.1f * scale));
		p.setSeed();
		p.setLife(life);
		p.setRadius((r_bar.getProgress() + 1) * 0.005f);
	
		target.add(p);
	}

	@Override
	public void setMovingPoint(vector2f m) {
		// TODO Auto-generated method stub
		

		float noise_rate = 0.07f;
		float noise;
		long life = (t_bar.getProgress() + 5) * 1000;
		int n = target.size();
		vector2f b = target.get(n-1).tar;
		
		float MOVE_DIST_MIN = 0.015f * (r_bar.getProgress() + 1);
		
		while(b.dist(m) > MOVE_DIST_MIN)
		{
			vector2f x = m.minus(b);
			x.normal();
			
			Particle p = new Particle();
			p.setTar(b);
			noise = noise_rate * (rand.nextFloat()*2 - 1);
			
			double nx = -Math.atan2(x.Y, x.X) / Math.PI;
			nx += 0.5;
			
			p.setPos(new vector2f((float) nx, -1.1f * scale + noise));
			p.setSeed();
			p.setLife(life);
			p.setRadius((r_bar.getProgress()+1) * 0.015f);
			target.add(p);
			b = b.plus(x.scalarProduct(MOVE_DIST_MIN));
		}

		
	}

}
