package com.cglab.testdrawer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class Icy extends Brush{

	
	private void draw_ice(GL10 gl,IcyParticle p)
	{
		//int tn = rand.nextInt(textures[0].length);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
    	buf_make(p);
    	
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0][3]);
    	
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
	    gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

	//	gl.glNormalPointer(GL10.GL_FLOAT, 0, m_NormalData); //1
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
	private void buf_make(IcyParticle p) {
		// TODO Auto-generated method stub
		int i, j;
		buf_size = p.way.size() * 2;
		
		float[] vertexData;
		float[] colorData;
		float[] textureData;
		
		vertexData = new float[9*buf_size];
		colorData  = new float[12*buf_size];
		textureData = new float[6*buf_size];
		
		j=p.way.size();
		int cnt = 0;
		float r = p.radius * 0.2f;
		
		float rr, gg, bb;
		rr = gg = bb = 0.9f;
		
		vector2f s = p.tar;
		vector2f t;
		for(i=0; i<j; i++)
		{
			t = p.way.get(i);
			float angle = (float) Math.atan2(t.Y, t.X);
			float scalar = p.timer * speed;
			float rate = p.timer / (float)IcyParticle.birth_timer;
			float rate2 = -p.life / (float)IcyParticle.birth_timer;
			
			if(scalar < 0 ) {scalar = IcyParticle.birth_timer * speed; rate = 1.0f;}
			if(rate2 < 0) rate2 = 0;
			t = t.scalarProduct(scalar);
			t = t.plus(s);
			
			float px = (float) (r*Math.cos(angle + Math.PI/2));
			float py = (float) (r*Math.sin(angle + Math.PI/2));
			float qx = px * 0.6f + rand.nextFloat() * 0.01f;
			float qy = py * 0.6f + rand.nextFloat() * 0.01f;
		//	Log.i("init", "asdf" + px + " " + py + " " + angle + " tx " + (t.X-s.X) + " " + (t.Y-s.Y));
			
			/*
			vertexData[9*cnt + 0] = s.X + px;
			vertexData[9*cnt + 1] = s.Y + py;
			vertexData[9*cnt + 2] = 0;
			vertexData[9*cnt + 3] = s.X - px;
			vertexData[9*cnt + 4] = s.Y - py;
			vertexData[9*cnt + 5] = 0;
			vertexData[9*cnt + 6] = t.X + qx;
			vertexData[9*cnt + 7] = t.Y + qy;
			vertexData[9*cnt + 8] = 0;
			
			colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = rr;
			colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = gg;
			colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = bb;
			colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 1.0f;
			
			textureData[6*cnt + 0] = rate2;
			textureData[6*cnt + 1] = 0.0f;
			textureData[6*cnt + 2] = rate2;
			textureData[6*cnt + 3] = 1.0f;
			textureData[6*cnt + 4] = rate;
			textureData[6*cnt + 5] = 0.0f;
			
			cnt++;
			
			vertexData[9*cnt + 0] = t.X + qx;
			vertexData[9*cnt + 1] = t.Y + qy;
			vertexData[9*cnt + 2] = 0;
			vertexData[9*cnt + 3] = s.X - px;
			vertexData[9*cnt + 4] = s.Y - py;
			vertexData[9*cnt + 5] = 0;
			vertexData[9*cnt + 6] = t.X - qx;
			vertexData[9*cnt + 7] = t.Y - qy;
			vertexData[9*cnt + 8] = 0;
			
			colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = rr;
			colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = gg;
			colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = bb;
			colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 1.0f;
			
			textureData[6*cnt + 0] = rate;
			textureData[6*cnt + 1] = 0.0f;
			textureData[6*cnt + 2] = rate2;
			textureData[6*cnt + 3] = 1.0f;
			textureData[6*cnt + 4] = rate;
			textureData[6*cnt + 5] = 1.0f;
			
			cnt++;
			*/
			vertexData[9*cnt + 0] = s.X + px;
			vertexData[9*cnt + 1] = s.Y + py;
			vertexData[9*cnt + 2] = 0;
			vertexData[9*cnt + 3] = s.X - px;
			vertexData[9*cnt + 4] = s.Y - py;
			vertexData[9*cnt + 5] = 0;
			vertexData[9*cnt + 6] = t.X;
			vertexData[9*cnt + 7] = t.Y;
			vertexData[9*cnt + 8] = 0;
			
			colorData[12*cnt + 0] = colorData[12*cnt + 4] = colorData[12*cnt + 8] = rr;
			colorData[12*cnt + 1] = colorData[12*cnt + 5] = colorData[12*cnt + 9] = gg;
			colorData[12*cnt + 2] = colorData[12*cnt + 6] = colorData[12*cnt + 10] = bb;
			colorData[12*cnt + 3] = colorData[12*cnt + 7] = colorData[12*cnt + 11] = 1.0f;
			
			textureData[6*cnt + 0] = rate2;
			textureData[6*cnt + 1] = 0.0f;
			textureData[6*cnt + 2] = rate2;
			textureData[6*cnt + 3] = 1.0f;
			textureData[6*cnt + 4] = rate;
			textureData[6*cnt + 5] = 0.5f;
			
			cnt++;
		}
		
		m_VertexData = makeFloatBuffer(vertexData);
		m_ColorData = makeFloatBuffer(colorData);
		m_TextureData = makeFloatBuffer(textureData);
	}
	@Override
	public void draw(GL10 gl) {
		// TODO Auto-generated method stub
		
		int i, j;
		j = target.size();
		for(i=0; i<j; i++)
		{
			draw_ice(gl, (IcyParticle)target.get(i));
		}
		//Log.i("init","s"+j);
		
    	Update();
	}

    long start_Time=-1;
	long end_Time;
	float speed = 0.003f;
	private void Update()
	{
		int i, j, k;
		end_Time = System.currentTimeMillis();
		if(start_Time<0)
			start_Time = System.currentTimeMillis();
		else
		{
			long dt = end_Time - start_Time;
			start_Time = System.currentTimeMillis();
			j = target.size();
			for(i=0; i<j; i++)
			{
				IcyParticle p = (IcyParticle) target.get(i);
				p.life -= dt;
				if(p.life< -IcyParticle.birth_timer)
				{
					target.remove(i);
					i--;
					j--;
				}
				else if(p.timer > 0)
				{
					p.timer += dt;
					if(p.timer > IcyParticle.birth_timer)
					{
						p.timer = -1;
						for(int ii = 0; ii < p.way.size(); ii++)
						{
							vector2f d = p.way.get(ii);
							d = d.scalarProduct(speed * IcyParticle.birth_timer);
							//Log.i("init","asdf "+d.X + " " +d.Y);
							target.add(p.make_child(d));
						}
					}
				}
			}
		}
		
	}
	
	
	@Override
	public void setStartPoint(vector2f s) {
		// TODO Auto-generated method stub
		long life = (t_bar.getProgress() + 5) * 1000;
		
		IcyParticle p = new IcyParticle();
		p.setTar(s);
		p.setSeed();
		p.state= 3;
		p.setLife(life);

		p.setPos(new vector2f(rand.nextFloat(), rand.nextFloat()));
		p.setDir(new vector2f(rand.nextFloat(), rand.nextFloat()));
		p.setRadius((r_bar.getProgress()+4) * 0.01f);
		
		p.addWay(6);
		
		p.timer = 1;
		
		target.add(p);
	}

	@Override
	public void setMovingPoint(vector2f m) {
		// TODO Auto-generated method stub
		int n = target.size();
		Particle t = target.get(n-1);
		
		float MOVE_DIST_MIN = (r_bar.getProgress()+1) * 0.01f;
		if(t.tar.dist(m) > MOVE_DIST_MIN)
		{
			long life = (t_bar.getProgress() + 5) * 1000;
			
			IcyParticle p = new IcyParticle();
			p.setTar(m);
			p.setSeed();
			p.state = 3;
			p.setLife(life);
	
			p.setPos(new vector2f(rand.nextFloat(), rand.nextFloat()));
			p.setDir(new vector2f(rand.nextFloat(), rand.nextFloat()));
			p.setRadius((r_bar.getProgress()+4) * 0.01f);
			
			p.timer = 1;
			
			p.addWay(4);
			
			target.add(p);
		}
	}

}
