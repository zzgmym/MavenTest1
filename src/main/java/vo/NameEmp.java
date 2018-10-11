package vo;

import domain.Employee;

import java.util.List;

public class NameEmp {
   private String uname;
   private List<Employee> list;
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public List<Employee> getList() {
        return list;
    }

    public void setList(List<Employee> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "NameEmp{" +
                "uname='" + uname + '\'' +
                ", list=" + list +
                '}';
    }
}
