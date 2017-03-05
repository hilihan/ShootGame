package cn.mark;
/**
 * 奖励接口
 * 实现这个接口的实现类具有获取奖励的功能
 * @author hilih
 */
public interface Award {
	public int DOUBLE_FIRE = 0;  //双倍火力
	public int LIFE = 1;   //1条命
	
	/** 
	 * 获得奖励类型(上面的0或1) 
	 * @return  0代表双倍火力，1代表命数
	 * */
	public int getType();
	
}
