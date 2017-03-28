package cn.mark;

import java.awt.image.BufferedImage;

/**
 * 飞行物父类
 * 所有的飞行物都具有以下属性和方法
 * @author hilih
 */
public abstract class FlyingObject {
	protected int x;    //x坐标
	protected int y;    //y坐标
	protected int width;    //宽
	protected int height;   //高
	protected BufferedImage image;   //图片 BufferedImage是Java中用来保存图片的一个类
	
	/**
	 * 飞行物走步
	 */
	public abstract void step();
	
	/**
	 * 检查是否越界
	 */
	public abstract boolean outOfBounds();
	
	/**
	 * 检查敌人是否被子弹击中
	 * @param bullet 子弹
	 * @return 
	 */
	public boolean shootBy(Bullet bullet){
		int x1 = this.x; //x1:敌人的x
		int x2 = this.x + this.width;//x2:敌人的x + 敌人的宽
		int y1 = this.y;//y1:敌人的y
		int y2 = this.y + this.height;//y2::敌人的y + 敌人的高
		int x = bullet.x; //子弹的x
		int y = bullet.y; //子弹的y
		
		return x >= x1 && x <= x2
				    &&
				    y >= y1 && y <= y2; //x在x1和x2之间，并且，y在y1和y2之间
	}	
}