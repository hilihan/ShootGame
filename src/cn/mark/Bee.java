package cn.mark;

import java.util.Random;

/**
 *小蜜蜂类
 *既是飞行物，也是奖励
 * @author hilih
 *
 */
public class Bee extends FlyingObject implements Award {
	//蜜蜂在界面中左右晃，因此需要两个控制速度
	private int xSpeed = 1;  //x坐标移动的速度
	private int ySpeed = 2;  //y坐标移动的速度
	private int awardType;  //奖励类型
	
	/** 
	 * 构造方法
	 * 用来给我们的成员变量初始化
	 *  */
	public Bee(){
		image = ShootGame.bee; //导入小蜜蜂图片
		width = image.getWidth();       //通过image对象的getWidth()方法动态获取图片的宽
		height = image.getHeight();    //通过image对象的getHeight()方法动态获取图片的高
		y = -height;                               //负的小蜜蜂的高（负数代表不在窗口内）        
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width); //x位置在窗口中随机出现
		awardType = rand.nextInt(2);//随机生成0到1之间的数
	}

	/**
	 * 重写奖励方法，获取奖励类型
	 * @return 奖励类型
	 */
	@Override
	public int getType() {
		//奖励类型：0或者1
		return awardType;
	}

}
