package life.majiang.community.community.enums;

public enum NotificationEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论"),

    ;

    public int getStatus() {
        return type;
    }

    public String getName() {
        return name;
    }

    private int type;
    private String name;

    NotificationEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }
}
