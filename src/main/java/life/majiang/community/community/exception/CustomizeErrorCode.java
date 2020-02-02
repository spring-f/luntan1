package life.majiang.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找到问题不在了，换一个试试"),
    COMMENT_NOT_FOUND(2002,"未选择任何问题"),
    FILE_UPLOAD_FAIL(2010,"图片上传失败")
    ;


    private String message;

     CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code=code;
    }

    private Integer code;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
