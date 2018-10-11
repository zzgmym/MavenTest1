package service;

import dao.Employeemapper;
import dao.Leavemapper;
import dao.Usermapper;
import domain.Empleave;
import domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.IdGenertor;
import utils.WxTemplate;
import vo.ReturnType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceimpl implements EmployeeService {
//    @Value("#{prop.appId}")
//    private String appId;
//    @Value("#{prop.appSecret}")
//    private String appSecret;
//    @Value("#{prop.grantType}")
//    private String grantType;
    @Autowired
    private Employeemapper employeeMapper;
    @Autowired
    private Leavemapper leavemapper;
    @Autowired
    private Usermapper usermapper;

    @Override
    public Employee findById(String i)throws Exception {
        Employee emp=employeeMapper.findById(i);
        return emp;
    }

    @Override
        public List<Empleave> findByName(String s) throws Exception {
            List<Empleave> list = employeeMapper.findByName(s);
            return list;
    }

    @Override
    public ReturnType add(Employee e) throws Exception {
        ReturnType te=new ReturnType();
        //判断当天是否提交过
        List<String> list=employeeMapper.isDateRepeat(e.getUdate(),e.getUname());
        if(!list.isEmpty()){
            te.setStatus(-1);//当天已提交过加班申请
            te.setReason("当天已提交过加班申请");
            return te;
        }
        String a=e.getStarttime();
        String b=e.getEndtime();
        String[] c=a.split(":");
        String[] d=b.split(":");
        int m=Integer.valueOf(c[0]);//开始时间小时数
        int n=Integer.valueOf(c[1]);//开始时间分钟数
        int x=Integer.valueOf(d[0]);//结束时间小时数
        int y=Integer.valueOf(d[1]);//结束时间分钟数
        int minutes=((x*60)+y)-((m*60)+n);//加班时长分钟数
        float t =(float) ((minutes/60)+(minutes%60)/30*0.5);//时长，单位为0.5h
        e.setTotaltime(t);
        e.setId("E"+IdGenertor.genOrdernum());
        if(employeeMapper.add(e)==1) {
            te.setStatus(1);//提交成功
            te.setId(e.getId());
            String openid2=usermapper.findOpenid2ByName(e.getAuditor());//获得审核人openid2
            WxTemplate.sendUncheckMsg(openid2,e.getId(),"加班",e.getUname(),e.getUdate(),e.getReason());//向审核人发送模板消息
            String openid22=usermapper.findOpenid2ByName(e.getUname());//获得申请人openid2
            Map<String,String> userInfo= WxTemplate.getUserinfo(openid22);//获得申请人关于公众号的详细信息
            String subscribe=String.valueOf(userInfo.get("subscribe"));//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号
            System.out.println("subscribe="+subscribe);
            if(subscribe.equals("0")){te.setStatus(2);te.setReason("申请人未订阅公众号");}
        }
       else{ te.setStatus(0);
       te.setReason("提交失败");}//提交失败
       return te;
    }
    @Override
    public int editPassByIds(String id,String auditor,String checkreason)throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String checktime=sdf.format(new Date());
        Employee e=employeeMapper.findById(id);
        String openid=usermapper.findOpenid2ByName(e.getUname());
        if(employeeMapper.passById(id,auditor,checktime,checkreason)==1){
            WxTemplate.sendPassMsg(openid,id,"加班",checktime);
            return 1;
        }
        else return 0;
    }
    @Override
    public int editUnpassByIds(String id,String auditor,String checkreason)throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String checktime=sdf.format(new Date());
        Employee e=employeeMapper.findById(id);
        String openid=usermapper.findOpenid2ByName(e.getUname());
        if(employeeMapper.unpassById(id,auditor,checktime,checkreason)==1) {
            WxTemplate.sendUnpassMsg(openid,id,"加班",checkreason);
        return 1;
        }
        else {return 0;}
    }
    @Override
    public List<Empleave> findUncheckByAuditor(String auditor)throws Exception{
        List<Empleave> list= employeeMapper.findUncheckByAuditor(auditor);
        return  list;
    }
    @Override
    public List<Empleave> findCheckedByAuditor(String auditor)throws Exception{
        List<Empleave> list= employeeMapper.findCheckedByAuditor(auditor);
        return  list;
    }
    @Override
    public List<Empleave> findUncheckByName(String uname)throws Exception{
        return employeeMapper.findUncheckByName(uname);
    }
    @Override
    public List<Empleave> findCheckedByName(String uname)throws Exception{
        return employeeMapper.findCheckedByName(uname);
    }
    @Override
    public List<String> findEveryName()throws Exception{
        List<String> list= employeeMapper.findEveryName();
        return list;
    }
    public List<Employee> findEmpByDate(String udate, String dept){
        List<Employee> list=employeeMapper.findEmpByDate(udate,dept);
        return list;
    }
    public List<Employee> findEmpByDate2(String udate, String dept)throws Exception{
        return employeeMapper.findEmpByDate2(udate,dept);
    }
    public int editEmp(Employee e)throws Exception{
        String p=e.getUdate();
        e.setUdate(p.replace("-","/"));
        String a=e.getStarttime();
        String b=e.getEndtime();
        String[] c=a.split(":");
        String[] d=b.split(":");
        int m=Integer.valueOf(c[0]);
        int n=Integer.valueOf(c[1]);
        int x=Integer.valueOf(d[0]);
        int y=Integer.valueOf(d[1]);
        int minutes=((x*60)+y)-((m*60)+n);
        float t =(float) ((minutes/60)+(minutes%60)/30*0.5);
        e.setTotaltime(t);
        return employeeMapper.editEmp(e);
    }
    @Override
    public List<Employee> findEveryEmp(String dept)throws Exception{
        return employeeMapper.findEveryEmp(dept);
    }
//    @Override
//    public int findCountByDate(String udate,String dept)throws Exception{
//        return employeeMapper.findCountByDate(udate,dept);
//    }
    @Override
    public int delEmpById(String id)throws Exception{
        return employeeMapper.delEmpById(id);
    }
    @Override
    public int editRevokeById(String id)throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String revokeTime=sdf.format(new Date());
        Employee e=employeeMapper.findById(id);
//        String openid=usermapper.findOpenid2ByName(e.getAuditor());
//        WxTemplate.sendRevokeMsg(openid);
        return employeeMapper.revokeById(id,revokeTime);
    }
    @Override
    public int findUncheckCount(String auditor)throws Exception{
        return employeeMapper.findUncheckCount(auditor)+leavemapper.findUncheckCount(auditor);
    }
    @Override
    public List<String> findNameByDate(String udate,String dept)throws Exception{
        return employeeMapper.findNameByDate(udate,dept);
    }
}
