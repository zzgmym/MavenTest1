package dao;

import domain.Leave;

import java.util.List;

public interface Leavemapper {
    int add(Leave e);
    Leave findById(String id);
    List<Leave> listByName(String uname);
    List<Leave> listUncheck(String auditor);
    List<Leave> listChecked(String auditor);
    List<String> isDateRepeat(String uname,String startdate,String enddate);
    int delLeaveById(String id);
    int passById(String id,String auditor,String checktime,String checkreason);
    int unpassById(String id,String auditor,String checktime,String checkreason);
    int revokeById(String id,String revokeTime);
    int findUncheckCount(String auditor);
}
