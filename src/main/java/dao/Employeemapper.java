package dao;

import domain.Empleave;
import domain.Employee;

import java.util.List;

public interface Employeemapper {
     Employee findById(String id);
     List<Empleave> findByName(String uname);
     List<Empleave> findUncheckByAuditor(String auditor);
     List<Empleave> findCheckedByAuditor(String auditor);
     List<Empleave> findUncheckByName(String uname);
     List<Empleave> findCheckedByName(String uname);
     int add(Employee e) ;
     int passById(String id,String auditor,String checktime,String checkreason);
     int unpassById(String id,String auditor,String checktime,String checkreason);
     List<String> findEveryName();
     List<Employee> findEmpByDate(String udate, String dept);
     List<Employee> findEmpByDate2(String udate, String dept);
    // int findCountByDate(String udate,String dept);//只查询审核通过的记录
     List<String> findNameByDate(String udate,String dept);//查询某天不重复的申请人姓名
     List<String> isDateRepeat(String udate,String uname);
     int editEmp(Employee e);
     List<Employee> findEveryEmp(String dept);
     int delEmpById(String id);
     int revokeById(String id,String revokeTime);
     int findUncheckCount(String auditor);
}
