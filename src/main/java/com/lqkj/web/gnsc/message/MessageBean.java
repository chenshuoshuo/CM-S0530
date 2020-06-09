package com.lqkj.web.gnsc.message;

import com.lqkj.web.gnsc.utils.UtilPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Page;

import java.io.Serializable;

/**
 * 服务器单个信息bean
 * Created by free on 2017/7/27 0027.
 */
@ApiModel(value = "MessageBean", description = "单个实体返回对象")
public class MessageBean<T> extends MessageBaseBean implements Serializable {

    public MessageBean() {
        this.setTime(System.currentTimeMillis());
    }

    public MessageBean(T data) {
        this.data = data;
        this.setTime(System.currentTimeMillis());
    }

    /**
     * 被包含的消息实体
     */
    @ApiModelProperty("包含的数据")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> MessageBean<T> ok() {
        MessageBean<T> messageBean = new MessageBean<>();
        messageBean.setStatus(true);
        return messageBean;
    }

    public static <T> MessageBean<T> ok(T data) {
        MessageBean<T> messageBean = new MessageBean<>();
        messageBean.setStatus(true);
        messageBean.setData(data);
        return messageBean;
    }

    public static <T> MessageBean<T> error(String data) {
        MessageBean<T> messageBean = new MessageBean<>();
        messageBean.setStatus(false);
        messageBean.setCode(-1);
        messageBean.setMessage(data);
        return messageBean;
    }

    /**
     *
     * @return
     */
    public static <T> MessageBean<T> construct(T data, String msg){
        MessageBean messageBean = new MessageBean();

        messageBean.setData(data);
        messageBean.setMessage(msg);
        messageBean.setStatus(true);

        int code;
        if(data instanceof Page){
            code = ((Page) data).getTotalElements() == 0L ? -100 : 200;
        } else if(data instanceof UtilPage){
            code = ((UtilPage) data).getTotalElements() == 0 ? -100 : 200;
        } else{
            code = data == null ? -100 : 200;
        }
        messageBean.setCode(code);

        return messageBean;
    }
}
