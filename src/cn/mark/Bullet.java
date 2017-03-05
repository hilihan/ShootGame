package cn.mark;

import java.util.Random;

/**
 * 子弹类
 * 飞行物
 */
public class Bullet extends FlyingObject {
	private int speed = 3;  //移动的速度
	
	/** 
	 * 构造方法
	 * 用来给子弹成员变量初始化
	 * 子弹出现的位置是跟随英雄机确定的，因此需要传参
	 *  */
	public Bullet(int x, int y){
		image = ShootGame.bullet; //导入子弹图片
		width = image.getWidth();       //通过image对象的getWidth()方法动态获取图片的宽
		height = image.getHeight();    //通过image对象的getHeight()方法动态获取图片的高
		this.x = x; 
		this.y = y;
	}
	
}
