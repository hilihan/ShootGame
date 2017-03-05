package cn.mark;

import java.awt.image.BufferedImage;

/**
 * 英雄机
 * 飞行物
 * @author hilih
 */
public class Hero extends FlyingObject {
	private int doubleFire;   //双倍火力
	private int life;   //命数
	
	private BufferedImage[] images = {};  //我们需要两张英雄机图片来实现英雄机的动态效果
	private int index = 0;                //英雄机图片切换索引
	
	/**
	 * 构造方法
	 * 初始化英雄机的成员变量
	 */
	public Hero(){
		life = 3;   //初始3条命
		doubleFire = 0;   //初始火力为0
		images = new BufferedImage[]{ShootGame.hero0, ShootGame.hero1}; //英雄机图片数组
		image = ShootGame.hero0;   //初始为hero0图片
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 400;
	}
	
	
	
}
