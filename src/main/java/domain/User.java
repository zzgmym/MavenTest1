package domain;


public class User {
    private int id;
    private String uname;
    private String userid;
    private String password;
    private String dept;
    private  int admin;
    private String email;
    private String telephone;
    private String openid;
    private String openid2;
    private String unionid;

    public String getOpenid2() {
        return openid2;
    }

    public void setOpenid2(String openid2) {
        this.openid2 = openid2;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getId() {
        return id;
    }
    public String getDept() {
        return dept;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", dept='" + dept + '\'' +
                ", admin=" + admin +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", openid='" + openid + '\'' +
                '}';
    }
}
