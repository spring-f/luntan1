package life.majiang.community.community.Model;

public class NodeFition {
    private long id;
    private long notifier;
    private long receiver;
    private long outerId;
    private Integer type;
    private long gmtCreate;
    private Integer status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNotifier() {
        return notifier;
    }

    public void setNotifier(long notifier) {
        this.notifier = notifier;
    }

    public long getReceiver() {
        return receiver;
    }

    public void setReceiver(long receiver) {
        this.receiver = receiver;
    }

    public long getOuterId() {
        return outerId;
    }

    public void setOuterId(long outerId) {
        this.outerId = outerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
