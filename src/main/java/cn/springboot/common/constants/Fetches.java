package cn.springboot.common.constants;

public interface Fetches {
	// redis缓存类别
	String ROWS = "rows:";
	String ROW = "row:";
	String NAME = "name:";
	// 若设置为all,则会缓存rows,row,name
	String ALL = "all";
	
	// 公共方法
	String findOneByNo = "findOneByNo";
	String findVO = "findVO";
	String findVOAll = "findVOAll";
}
