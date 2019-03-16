package entity;

/**
 * @depict: 构建通用的返回结果
 * @author: PCP
 * @create: 2019-02-21 20:00
 */
public class Result {

    private Integer code;//返回码
    private boolean flag;//是否成功
    private String message;//返回信息
    private Object data;//返回数据

    public Result() {
    }

    /**
     * @depict: 增、删、改操作的返回结果
     * @param1: code {@link Result#code}
     * @param2: flag {@link Result#flag}
     * @param3: message {{@link Result#message}
     * @author: PCP
     * @create: 2019/2/21 20:03
     */
    public Result(boolean flag,Integer code,String message) {
        this.code = code;
        this.flag = flag;
        this.message = message;
    }

    /**
     * @depict: 查询操作的返回结果
     * @param1: code {@link Result#code}
     * @param2: flag {@link Result#flag}
     * @param3: message {@link Result#message}
     * @author: PCP
     * @create: 2019/2/21 20:15
     */
    public Result(boolean flag,Integer code, String message, Object data) {
        this.code = code;
        this.flag = flag;
        this.message = message;
        this.data = data;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", flag=" + flag +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
