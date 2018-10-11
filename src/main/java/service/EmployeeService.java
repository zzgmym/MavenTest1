package service;

import domain.Empleave;
import domain.Employee;
import vo.ReturnType;


import java.util.List;

public interface EmployeeService {
     Employee findById(String i)throws Exception;
     List<Empleave> findByName(String s) throws Exception;
     ReturnType add(Employee e) throws Exception;
     int editPassByIds(String id,String auditor,String checkreason)throws Exception;
     int editUnpassByIds(String id,String auditor,String checkreason)throws Exception;
     List<Empleave> findUncheckByAuditor(String auditor)throws Exception;
     List<Empleave> findCheckedByAuditor(String auditor)throws Exception;
     List<Empleave> findUncheckByName(String uname)throws Exception;
     List<Empleave> findCheckedByName(String uname)throws Exception;
     List<Employee> findEmpByDate2(String udate, String dept)throws Exception;
//     int findCountByDate(String udate,String dept)throws Exception;
     int delEmpById(String id)throws Exception;
     int editRevokeById(String id)throws Exception;
     int findUncheckCount(String auditor)throws Exception;
     List<String> findEveryName()throws Exception;
     List<Employee> findEmpByDate(String udate, String dept)throws Exception;
     int editEmp(Employee e)throws Exception;
     List<Employee> findEveryEmp(String dept)throws Exception;
     List<String> findNameByDate(String udate,String dept)throws Exception;//查询某个日期内不重复的姓名
}
