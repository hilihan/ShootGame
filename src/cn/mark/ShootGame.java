package cn.mark;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;




/**
 * 主程序类 程序入口
 * 
 * @author hilih
 */
public class ShootGame extends JPanel {
	private int state; //状态
	private static final int START = 0; //开始
	private static final int RUNNING = 1; //运行
	private static final int PAUSE = 2; //暂停
	private static final int GAME_OVER = 3; //结束
	
	public static final int WIDTH = 400; // 窗口宽
	public static final int HEIGHT = 654; // 窗口高
	/* 创建各个图片属性 */
	public static BufferedImage background; // 背景图
	public static BufferedImage start; // 开始
	public static BufferedImage airplane; // 敌机
	public static BufferedImage bee; // 小蜜蜂
	public static BufferedImage bullet; // 子弹
	public static BufferedImage hero0; // 英雄机图一
	public static BufferedImage hero1; // 英雄机图二
	public static BufferedImage pause; // 暂停
	public static BufferedImage gameover; // 游戏结束
	public static BufferedImage icon; // 游戏图标
	//静态代码块，初始化图片属性
	static {
		try {
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			icon = ImageIO.read(ShootGame.class.getResource("icon.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private Hero hero = new Hero();
	private FlyingObject[] flyings = {};
	private Bullet[] bullets = {};
	private int score = 0; //得分
	
	/**
	 * 重写paint方法
	 * 当一个JFrame设置为窗口可见时，会自动调用此方法
	 */
	public void paint(Graphics g){
		//画背景图
		g.drawImage(background, 0, 0,null );
		//画英雄机
		paintHero(g);
		//画敌人（敌机+小蜜蜂）
		paintFlyings(g);
		//画子弹
		paintBullets(g);
		//画分和画命
		paintScoreAndLife(g);
		//画状态
		paintState(g);
	}
	
	/**
	 * 画分和命
	 * @param g 画笔
	 */
	public void paintScoreAndLife(Graphics g){
		//设置画笔颜色
		g.setColor(Color.red);
		//设置画笔字体           字体                         粗体         字号
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,22));
		//画分
		g.drawString("SCORE："+score, 10, 25);
		//画命
		g.drawString("LIFE："+hero.getLife(), 10, 45);
	}
	
	
	/**
	 * 画英雄机
	 * @param g 画笔
	 */
	public void paintHero(Graphics g){
		//因为英雄机的x、y坐标不固定
		//所以根据hero对象的具体x和y值来在窗口对应位置上画位置
		g.drawImage(hero.image, hero.x, hero.y, null);
		
	}
	/**
	 * 画敌人（蜜蜂+小蜜蜂）
	 * @param g
	 */
	public void paintFlyings(Graphics g){
		//遍历敌人数组
		for(int i = 0; i < flyings.length; i++){
			//获取每一个敌人对象
			FlyingObject f = flyings[i];
			//画每一个敌人的位置
			g.drawImage(f.image, f.x, f.y, null);
		}
		
	}
	/**
	 * 画子弹
	 * @param g 画笔
	 */
	public void paintBullets(Graphics g){	
		//遍历子弹数组
		for(int i = 0; i < bullets.length; i++){
			//获取每一颗子弹
			Bullet b = bullets[i];
			//画每一颗子弹的位置
			g.drawImage(b.image, b.x, b.y, null);
		}
	}
	
	/** 
	 * 画游戏状态 */
	public void paintState(Graphics g) {
		switch (state) {
		case START: // 启动状态
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE: // 暂停状态
			g.drawImage(pause, 0, 0, null);
			System.out.println(state);
			break;
		case GAME_OVER: // 游戏终止状态
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}
	
	/**
	 * 生成敌人对象
	 * @return 敌机或者小蜜蜂（向上造型）
	 */
	public FlyingObject nextOne(){
		Random ran = new Random();
		int type = ran.nextInt(20);//生成0-19随机数
		if ( type == 0 ){ // 二十分之一机会生成小蜜蜂
			Bee b = new Bee();
			return b;
		}else{ //生成敌机
			Airplane a = new Airplane();
			return a;
		}
	}

	//入场计时器
	int flyEnteredIndex= 0;
	/**
	 * 敌人入场（该方法每10毫秒就被定时器运行一次）
	 */
	public void enterAction(){
		//10毫秒自增一次
		flyEnteredIndex++;
		//400毫秒（10*40）走一次，1秒大约产生2个左右的敌人
		if( flyEnteredIndex % 40 == 0  ){
			FlyingObject obj = nextOne();//获取新生随机敌人对象
			/* 将新生的对象存放到Flyings数组中，所以需要对数组进行扩容 */
			flyings = Arrays.copyOf(flyings, flyings.length+1);
			//将新生对象存入Flyings数组最后一位
			flyings[flyings.length - 1] = obj;
			
		}
	}
	
	/**
	 * 飞行物走步（该方法每10毫秒就被定时器运行一次）
	 */
	public void stepAction(){
		//英雄机走步
		hero.step();
		//遍历所有敌人，每个敌人走一步
		for( int i = 0; i < flyings.length; i++ ){
			flyings[i].step();
		}
		//遍历所有子弹，每个子弹走一步
		for( int i = 0; i < bullets.length; i++ ){
			bullets[i].step();
		}
	}
	
	/**
	 * 英雄机发射子弹（该方法每10毫秒就被定时器运行一次）
	 */
	int shootIndex = 0; //子弹发射计时器
	public void shootAction(){
		shootIndex++; //每10毫秒增1
		if ( shootIndex % 30 == 0 ){ //每300毫秒执行一次
			Bullet[] bs = hero.shoot();//英雄机发射子弹
			bullets = Arrays.copyOf(bullets, bullets.length+bs.length);//根据发射的子弹数量（单倍/双倍）来扩容
			//数组追加
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
		}
	}
	
	/**
	 * 删除越界的飞行物（该方法每10毫秒就被定时器运行一次）
	 * 
	 */
	public void outOfBoundsAction(){
		//小蜜蜂和敌机越界检查
		FlyingObject[] flyingLive = new FlyingObject[flyings.length]; //创建一个存活者数组，所有未越界的对象往这儿放
		int index = 0; //存活者计数器
		for ( int i = 0; i < flyings.length; i++ ){ //遍历所有的敌人数组
			FlyingObject f = flyings[i];
			if ( !f.outOfBounds() ){ // 不越界
				flyingLive[index] = f ; //将未越界的敌人往存活者数组放
				index++; //存活者+1
			}
		}
		flyings = Arrays.copyOf(flyingLive, index); //替换掉原敌人数组
		
		//子弹越界检查
		index = 0;//存活者计数归零
		Bullet[] bulletLive = new Bullet[bullets.length]; //创建存活者数组
		for ( int i = 0; i < bullets.length; i++ ){
			Bullet b = bullets[i];
			if ( !b.outOfBounds() ){ // 不越界
				bulletLive[index++] = b;//将未越界的子弹往存活者数组放
			}
		}
		bullets = Arrays.copyOf(bulletLive, index); //替换掉原子弹数组
		
	}
	
	/**
	 * 子弹与敌人的碰撞检测
	 */
	public void bangAction(){
		//循环遍历储存所有子弹的数组bullets
		for ( int i = 0; i < bullets.length; i++ ){
			Bullet b = bullets[i];
			boolean flag = bang(b);//碰撞检测
			//子弹移除
			if (flag){
				Bullet temp = bullets[i]; //被击中的子弹和最后一个子弹做交换
				bullets[i] = bullets[bullets.length - 1];
				bullets[bullets.length - 1] = temp;
				//删除最后一个子弹
				bullets = Arrays.copyOf(bullets, bullets.length - 1);
			}
		}
	}
	
	/**
	 * 单个子弹与所有敌人的碰撞检测
	 * @param b 子弹
	 */
	public boolean bang(Bullet b){
		int index = -1;//击中飞行物索引
		//遍历所有敌人的数组flyings，在该循环中判断当前子弹是否击中某个敌人
		for ( int i =0; i < flyings.length; i++ ){
			FlyingObject obj = flyings[i];
			//击中退出循环，记录击中飞行物在flyings数组中的索引index
			if( obj.shootBy(b) ){ //判断是否击中
				index = i; // 记录被击中的飞行物的索引
				break;
			}
		}
		//在flyings数组中找到该飞行物，移除
		if ( index != -1 ){ //有击中的飞行物
			FlyingObject one = flyings[index]; //记录被击中的飞行物
			
			FlyingObject temp = flyings[index]; //被击中的飞行物和最后一个飞行物做交换
			flyings[index] = flyings[flyings.length - 1];
			flyings[flyings.length - 1] = temp;
			//删除最后一个飞行物（就是被击中的）
			flyings = Arrays.copyOf(flyings, flyings.length - 1);
			
			//检查one的类型，如果是敌机，就加分
			if ( one instanceof Enemy ){
				Enemy e = (Enemy)one; //强制类型转换（向下造型）
				score += e.getScore();
			}
			//如果是奖励，设置奖励
			if ( one instanceof Award ){
				Award a = (Award)one;
				int type = a.getType();//获取奖励类型
				switch ( type ){
					case Award.DOUBLE_FIRE:
						//设置双倍火力
						hero.addDoubleFire();
						break;
					case Award.LIFE:
						//设置加命
						hero.addLife();
						break;
				}
			}
			return true;
		}
	return false;
	}
	
	/**
	 * 检查游戏是否结束
	 * @return true游戏结束
	 */
	public boolean isGameOver(){
		//遍历所有存储敌人的数组flyings
		for (int i = 0; i < flyings.length; i++) {
			int index = -1;
			FlyingObject obj = flyings[i];
			if (hero.hit(obj)) { // 检查英雄机与飞行物是否碰撞
				hero.subtractLife(); // 减命
				hero.setDoubleFire(0); // 双倍火力解除
				index = i; // 记录碰上的飞行物索引
			}
			if (index != -1) { //碰上了
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = t; // 碰上的与最后一个飞行物交换

				flyings = Arrays.copyOf(flyings, flyings.length - 1); // 删除碰上的飞行物
			}
		}
		//命数小于等于0说明游戏结束
		return hero.getLife() <= 0;
	}
	
	
	/** 检查游戏结束 */
	public void checkGameOverAction() {
		if (isGameOver()) {
			state = GAME_OVER; // 改变状态
		}
	}
	
	
	
	/**
	 * 程序启动执行
	 */
	public void action(){
		/* 鼠标侦听事件 */
		//创建监听器
		MouseAdapter l  = new MouseAdapter(){
			
			@Override
			public void mouseMoved(MouseEvent e) { //鼠标移动
				if (state == RUNNING) { // 运行状态下移动英雄机--随鼠标位置
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) { // 鼠标进入
				if (state == PAUSE) { // 暂停状态下运行
					state = RUNNING;
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) { // 鼠标退出
				if (state == RUNNING) { // 游戏未结束，则设置其为暂停
					state = PAUSE;
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) { // 鼠标点击
				switch (state) {
				case START:
					state = RUNNING; // 启动状态下运行
					break;
				case GAME_OVER: // 游戏结束，清理现场
					flyings = new FlyingObject[0]; // 清空飞行物
					bullets = new Bullet[0]; // 清空子弹
					hero = new Hero(); // 重新创建英雄机
					score = 0; // 清空成绩
					state = START; // 状态设置为启动
					break;
				}
			}
		};
		
		 
		
		this.addMouseMotionListener(l);//处理鼠标滑动事件
		this.addMouseListener(l);//处理鼠标事件
		
		/* 主流程控制 */
		Timer timer = new Timer();//定时器对象
		int intervel = 10; //定时间隔（以毫秒为单位）
		/*
		 * schedule(TimerTask task, long delay, long period)
		 * 第一个参数是定时任务，TimerTask是个抽象类,因为定时任务只需要定义一次，一般使用匿名内部类来实现
		 * 第二个参数是从程序启动到定时任务触发的时间间隔(毫秒)
		 * 第三个参数是定时任务每次反复启动的时间间隔(毫秒)
		 * */
		timer.schedule(new TimerTask(){
			  	@Override
			  	public void run() { //定时需要做的事
			  		if (state == RUNNING) { // 运行状态
			  			enterAction();//敌人入场
			  			stepAction();//飞行物的走步
			  			shootAction();//英雄机射击
			  			outOfBoundsAction();//删除越界的飞行物
			  			bangAction();//子弹与敌人的碰撞
			  			isGameOver();//英雄机碰撞检测
			  			checkGameOverAction(); // 检查游戏结束
			  		}
			  		repaint();//重画（调用paint()方法）
			  	}
			}, intervel, intervel);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("打飞机biu~biu~biu~");
		ShootGame game = new ShootGame(); // 创建面板对象
		frame.add(game); // 将面板添加到JFrame中
		frame.setSize(WIDTH, HEIGHT); // 设置窗口大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 点击窗口X按钮时同时也停止虚拟机运行
		frame.setLocationRelativeTo(null); // 设置窗体在桌面上出现的初始位置
		frame.setVisible(true); // 1.窗口可见；2.尽快调用paint()方法
		frame.setIconImage(icon);
		game.action(); //启动程序执行
	}

}
