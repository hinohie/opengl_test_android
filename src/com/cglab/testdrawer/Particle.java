package com.cglab.testdrawer;

import android.util.Log;

public class Particle {
	public vector2f pos;
	public vector2f dir;
	public vector2f tar;
	public long life = 8000;
	public float radius;
	
	public float seed;
	int state;
	
	Particle()
	{
		pos = new vector2f(0, 0);
		dir = new vector2f(0, 0);
		tar = new vector2f(0, 0);
	}
	void setPos(vector2f e)
	{
		pos.set(e);
	}
	void setDir(vector2f e)
	{
		dir.set(e);
	}
	void setTar(vector2f e)
	{
		tar.set(e);
	}
	void setLife(long e)
	{
		life = e;
	}
	void setSeed()
	{
		seed = tar.dist() + pos.X + 7;
		while(seed > 1.0f)
			seed /=7;
	//	Log.i("init","seed : "+seed);
		
	}
	public void setRadius(float f) {
		radius = f;
	}
	
	private vector2f pre_pos;
	private vector2f pre_dir;
	public boolean working = true;
	void Update(long dt)
	{
		if(working)
		{
			working = false;
			life -= dt;
			pre_pos = new vector2f(pos.X + dir.X*dt/1000.0f, pos.Y + dir.Y*dt/1000.0f);
			pre_dir = new vector2f(tar.X - pos.X, tar.Y - pos.Y);
			if(pre_dir.dist() > 1.0f)
				pre_dir.normal();
		}
	}
	void Force(vector2f f)
	{
		pre_dir.X += f.X;
		pre_dir.Y += f.Y;
	}
	void Accept()
	{
		pos.set(pre_pos);
		dir.set(pre_dir);
		working = true;
	}
}
