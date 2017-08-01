package win.pangniu.learn.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 结果类型枚举
 * Created by 超文 on 2017/5/2.
 * version 1.0
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultStatus {
    /**
     * 1 开头为判断文件在系统的状态
     */
    IS_HAVE(100, "文件已存在！"),

    NO_HAVE(101, "该文件没有上传过。"),

    ING_HAVE(102, "该文件上传了一部分。");


    private final int value;

    private final String reasonPhrase;


    ResultStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
