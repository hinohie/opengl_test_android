package com.cglab.testdrawer;

import java.util.ArrayList;
import java.util.Random;

public class IcyParticle extends Particle{

	ArrayList<IcyParticle> child;
	ArrayList<vector2f> way;
	public float com_angle;
	public long timer;
	public static long birth_timer = 1000;
	static Random rand = new Random();
	public IcyParticle()
	{
		child = new ArrayList<IcyParticle>();
		way = new ArrayList<vector2f>();
		timer = 0;
	}
	public void addWay(int n)
	{
		way.clear();
		while(n-->0)
		{
			float ss = state * state * 20;
			float tt = (float) ((rand.nextFloat() * 2 - 1) * Math.PI) * ss / 180 + com_angle;
			float rr = (rand.nextFloat() + 0.01f) * radius;
			vector2f w = new vector2f((float)(rr * Math.cos(tt)), (float)(rr * Math.sin(tt)));
			way.add(w);
		}
	}
	public IcyParticle make_child(vector2f s)
	{
		IcyParticle nc = new IcyParticle();
		nc.setTar(tar.plus(s));
		nc.setLife(life - 200);
		nc.setPos(new vector2f(rand.nextFloat(), rand.nextFloat()));
		nc.setDir(new vector2f(rand.nextFloat(), rand.nextFloat()));
		nc.setSeed();
		nc.setRadius(radius * 0.7f);
		nc.state = state-1;
		nc.timer = 1;
		
		nc.com_angle = (float) Math.atan2(s.Y, s.X);
		nc.addWay(state*2-3);
		
		child.add(nc);
		
		return nc;
	}
}
