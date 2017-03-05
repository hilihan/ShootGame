package cn.mark;
/**
 * 敌人接口
 * 所有敌人被干掉之后具有得分的行为
 * @author hilih
 */
public interface Enemy {
	/**
	 * 得分
	 * @return 分值
	 */
	public int getScore();
}
