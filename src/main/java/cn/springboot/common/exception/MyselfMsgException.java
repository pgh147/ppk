package cn.springboot.common.exception;
/**
 * 
 * @ClassName: MyselfMsgException<br>
 * @Description: 自定义信息的异常<br>
 * @author liutao<br>
 * @date 2016年9月29日上午9:54:54<br>
 *
 */
public class MyselfMsgException extends RuntimeException{

	    private static final long serialVersionUID = 1L;

	    public MyselfMsgException(String msg) {
	        super(msg);
	    }

	    public MyselfMsgException(String msg, Throwable cause) {
	        super(msg, cause);
	    }
}