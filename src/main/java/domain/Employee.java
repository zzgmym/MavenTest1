package domain;

public class Employee {
    private String id;
    private String uname;
    private String dept;
    private String udate;
    private String starttime;
    private String endtime;
    private float totaltime;
    private String reason;
    private String status;
    private String auditor;
    private String submittime;
    private String checkreason;
    private String checktime;

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", uname='" + uname + '\'' +
                ", dept='" + dept + '\'' +
                ", udate='" + udate + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", totaltime=" + totaltime +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", auditor='" + auditor + '\'' +
                ", submittime='" + submittime + '\'' +
                ", checkreason='" + checkreason + '\'' +
                ", checktime='" + checktime + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getUdate() {
        return udate;
    }

    public void setUdate(String udate) {
        this.udate = udate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public float getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(float totaltime) {
        this.totaltime = totaltime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmittime() {
        return submittime;
    }

    public void setSubmittime(String submittime) {
        this.submittime = submittime;
    }

    public String getCheckreason() {
        return checkreason;
    }

    public void setCheckreason(String checkreason) {
        this.checkreason = checkreason;
    }

    public String getChecktime() {
        return checktime;
    }

    public void setChecktime(String checktime) {
        this.checktime = checktime;
    }

}


