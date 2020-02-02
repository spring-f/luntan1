package life.majiang.community.community.enums;

public enum NotificationTypeEnum {
    UNREAD(0),READ(1);
    ;

    public int getStatus() {
        return type;
    }

    private int type;

    NotificationTypeEnum(int status) {
        this.type = status;
    }
}
