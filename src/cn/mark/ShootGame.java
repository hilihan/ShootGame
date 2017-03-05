package cn.mark;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;



/**
 * 主程序类 程序入口
 * 
 * @author hilih
 */
public class ShootGame extends JPanel {
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

	public static void main(String[] args) {
		JFrame frame = new JFrame("打飞机biu~biu~biu~");
		ShootGame game = new ShootGame(); // 创建面板对象
		frame.add(game); // 将面板添加到JFrame中
		frame.setSize(WIDTH, HEIGHT); // 设置窗口大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 点击窗口X按钮时同时也停止虚拟机运行
		frame.setLocationRelativeTo(null); // 设置窗体在桌面上出现的初始位置
		frame.setVisible(true); // 窗口可见
		frame.setIconImage(icon);
	}

}
