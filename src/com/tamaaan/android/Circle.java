/**
 * 
 */
package com.tamaaan.android;

/**
 * @author Administrator
 *
 */
public class Circle
{
	public float radius;
	public float x;
	public float y;
	public float dx;
	public float dy;
	public float m;
	public int cr;
	public int cg;
	public int cb;

	public Circle(float radius, float x, float y, float dx, float dy, float m, int cr, int cb, int cg)
	{
		this.radius = radius;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.m = m;
		this.cr = cr - 30;
		this.cg = cg - 30;
		this.cb = cb - 30;
	}
}