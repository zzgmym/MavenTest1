package service;

import domain.Leave;
import vo.ReturnType;


import java.util.List;

public interface LeaveService {
    Leave findById(String id)throws Exception;
    ReturnType add(Leave leave)throws Exception;
    List<Leave> listByName(String uname)throws Exception;
    List<Leave> listUncheck(String auditors)throws Exception;
    List<Leave> listChecked(String auditor)throws Exception;
    List<String> isDateRepeat(String uname,String startDate,String endDate)throws Exception;
    int delLeaveById(String id)throws Exception;
    int editPassByIds(String id,String auditor,String checkreason)throws Exception;
    int editUnpassByIds(String ids,String auditor,String checkreason)throws Exception;
    int editRevokeById(String id)throws Exception;
}
