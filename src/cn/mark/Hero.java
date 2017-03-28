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

	/**
	 * 英雄机的走步
	 */
	@Override
	public void step() {
		//方法10毫秒被运行一次
		//切换图片hero0，hero1
		if(images.length>0){
			image = images[index++/10%images.length];  
		}
	}
	
	/**
	 * 英雄机发射子弹，此方法生成子弹对象
	 * @return 子弹数组
	 */
	public Bullet[] shoot(){
		Bullet[] bs;
		int xStep = this.width/4;//1/4的英雄机的宽
		int yStep = 20; //固定的子弹离英雄机的距离
		if ( doubleFire > 0 ){ //双倍
			bs = new Bullet[2];
			//左边子弹，英雄机的宽+英雄机的1/4宽，高度就是英雄机的高减去固定的距离
			bs[0] = new Bullet(this.x + 1*xStep, this.y - yStep);
			//右边子弹，英雄机的宽+英雄机的1/4宽，高度就是英雄机的高减去固定的距离
			bs[1] = new Bullet(this.x + 3*xStep, this.y - yStep);
			//发射一次双倍火力值，则火力值减去2
			doubleFire -= 2;
		}else{//单倍
			bs = new Bullet[1];
			bs[0] = new Bullet(this.x + 2*xStep, this.y - yStep);
		}
		return bs;
	}
	
	/**
	 * 英雄机随着鼠标动
	 * @param x 鼠标x
	 * @param y 鼠标y
	 */
	public void moveTo(int x, int y){
		this.x = x - this.width / 2;
		this.y = y - this.height / 2;
	}

	/**
	 * 越界检查
	 */
	public boolean outOfBounds() {
		//英雄机永不越界
		return false;
	}
	
	/**
	 * 英雄机增命
	 */
	public void addLife(){
		life++;
	}
	
	/**
	 * 英雄机增加双倍火力
	 */
	public void addDoubleFire(){
		doubleFire += 40;
	}
	
	/**
	 * 英雄机查看命数
	 * @return
	 */
	public int getLife(){
		return life;
	}
	
	/**
	 * 减命
	 */
	public void subtractLife(){
		life--;
	}
	
	/**
	 * 重新设置火力值
	 * @param doubleFire 火力值
	 */
	public void setDoubleFire( int doubleFire ){
		this.doubleFire = doubleFire;
	}
	
	/** 
	 * 碰撞算法 
	 * */
	public boolean hit(FlyingObject other){
		
		int x1 = other.x - this.width/2;                 //x坐标最小距离
		int x2 = other.x + this.width/2 + other.width;   //x坐标最大距离
		int y1 = other.y - this.height/2;                //y坐标最小距离
		int y2 = other.y + this.height/2 + other.height; //y坐标最大距离
	
		int herox = this.x + this.width/2;               //英雄机x坐标中心点距离
		int heroy = this.y + this.height/2;              //英雄机y坐标中心点距离
		
		return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;   //区间范围内为撞上了
	}
	
}
