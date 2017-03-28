package cn.mark;

import java.util.Random;

/**
 * 敌机类
 * 敌机是飞行物，同时被干掉的时候玩家还能得分
 * @author hilih
 */
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 3; //移动速度
	
	/** 
	 * 构造方法
	 * 用来给我们的成员变量初始化
	 *  */
	public Airplane(){
		image = ShootGame.airplane; //导入敌机图片
		width = image.getWidth();       //通过image对象的getWidth()方法动态获取图片的宽
		height = image.getHeight();    //通过image对象的getHeight()方法动态获取图片的高
		y = -height;                               //负的敌机的高（负数代表不在窗口内）        
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width); //x位置在窗口中随机出现
	}
	
	/**
	 * 重写Enmey接口中的得分方法
	 * @return 玩家得分
	 */
	@Override
	public int getScore() {
		//打掉一个敌机获得5分
		return 5;
	}

	/**
	 * 敌机走步
	 */
	@Override
	public void step() {
		//向下走步
		y+=speed;
	}

	/**
	 * 越界检查
	 */
	@Override
	public boolean outOfBounds() {
		//敌机的y坐标大于等于窗口高就说明越界了
		return this.y >= ShootGame.HEIGHT;
		
	}

}
