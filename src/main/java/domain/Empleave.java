package domain;

public class Empleave {
    private String id;
    private String uname;
    private String dept;
    private String utype;
    private String udate;
    private String startdate;//starttime
    private String enddate;//endtime
    private String starttime;
    private String endtime;
    private String totaltime;
    private String reason;
    private String submittime;
    private String imgpath;//imgpath
    private String imgname;//imgname
    private String auditor;
    private String checkreason;
    private String checktime;
    private String status;

    @Override
    public String toString() {
        return "Empleave{" +
                "id='" + id + '\'' +
                ", uname='" + uname + '\'' +
                ", dept='" + dept + '\'' +
                ", utype='" + utype + '\'' +
                ", udate='" + udate + '\'' +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", totaltime='" + totaltime + '\'' +
                ", reason='" + reason + '\'' +
                ", submittime='" + submittime + '\'' +
                ", imgpath='" + imgpath + '\'' +
                ", imgname='" + imgname + '\'' +
                ", auditor='" + auditor + '\'' +
                ", checkreason='" + checkreason + '\'' +
                ", checktime='" + checktime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
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

    public String getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(String totaltime) {
        this.totaltime = totaltime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSubmittime() {
        return submittime;
    }

    public void setSubmittime(String submittime) {
        this.submittime = submittime;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
