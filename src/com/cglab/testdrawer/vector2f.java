package com.cglab.testdrawer;

public class vector2f {

	public float X,Y;
	
	public vector2f(float nx, float ny)
	{ X = nx; Y = ny;}
	public vector2f(float nx)
	{ X = nx; Y = 0;}
	public vector2f()
	{ X = 0; Y = 0;}
	
	public void set(vector2f nv)
	{ X = nv.X; Y = nv.Y;}
	public void set(float nx, float ny)
	{ X = nx; Y = ny;}
	public void set(float nx)
	{ X = nx; Y = 0;}
	public void set()
	{ X = 0; Y = 0;}
	
	public void normal()
	{
		double z = X*X+Y*Y;
		if(z < 0.0000000001)
			return;
		z = Math.sqrt(z);
		X /= z; Y /= z;
	}
	
	public static float dotPruduct(vector2f p, vector2f q)
	{
		float r = p.X*q.X + p.Y*q.Y;
		return r;
	}
	public vector2f scalarProduct(float x)
	{
		vector2f r = new vector2f(X*x, Y*x);
		return r;
	}
	public vector2f plus(vector2f p)
	{
		vector2f q = new vector2f(X+p.X, Y+p.Y);
		return q;
	}
	public vector2f minus(vector2f p)
	{
		vector2f q = new vector2f(X-p.X, Y-p.Y);
		return q;
	}
	
	public float dist(vector2f q)
	{
		double xx = X - q.X;
		double yy = Y - q.Y;
		double zz = xx*xx + yy*yy;
		
		return (float)Math.sqrt(zz);
	}
	public float dist(){
		double zz = X*X + Y*Y;
		return (float)Math.sqrt(zz);
	}
}
