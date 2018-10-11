package controller;


import java.text.SimpleDateFormat;
import java.util.*;


import javax.servlet.http.HttpServletResponse;


import domain.Empleave;
import domain.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import domain.Employee;
import service.EmployeeService;
import service.LeaveService;
import vo.*;

@Controller
@RequestMapping("/list")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LeaveService leaveService;

    @RequestMapping("/listUncheck")//查询该审核人未审核的申请
    public @ResponseBody List<Empleave> findUncheckByAuditor(HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
       return employeeService.findUncheckByAuditor(map.get("auditor"));
    }
    @RequestMapping("/listChecked")//查询该审核人已审核的申请
    public @ResponseBody List<Empleave> findCheckedByAuditor(HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return employeeService.findCheckedByAuditor(map.get("auditor"));
    }
        @RequestMapping("/listUncheckByName")//查询用户未被审核的申请
    public @ResponseBody List<Empleave> findUncheckByName(HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return employeeService.findUncheckByName(map.get("uname"));
    }
    @RequestMapping("/listCheckedByName")//查询用户已被审核的申请
    public @ResponseBody List<Empleave> findCheckedByName(HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return employeeService.findCheckedByName(map.get("uname"));
    }
    @RequestMapping("/listByName")//查询一个人的所有记录
    public @ResponseBody List<Empleave> findByName(HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return employeeService.findByName(map.get("name"));
    }
    @RequestMapping("/passByIds")//通过审核，修改状态及审核人
        public @ResponseBody int passByIds(HttpServletResponse response,@RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String id=map.get("id");
        if(id.charAt(0)=='E'){return employeeService.editPassByIds(id,map.get("auditor"),map.get("checkreason"));}
        else if(id.charAt(0)=='L') {return leaveService.editPassByIds(id,map.get("auditor"),map.get("checkreason"));}
        else return -1;
    }
    @RequestMapping("/unpassByIds")//不通过审核，修改状态及审核人
    public @ResponseBody int unpassByIds(HttpServletResponse response,@RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String id=map.get("id");
        if(id.charAt(0)=='E'){return employeeService.editUnpassByIds(id,map.get("auditor"),map.get("checkreason"));}
        else if(id.charAt(0)=='L'){return leaveService.editUnpassByIds(id,map.get("auditor"),map.get("checkreason"));}
        else return -1;
    }
    @RequestMapping("/findById")//通过id查询申请
    public @ResponseBody
    ReturnDomain findById(HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        ReturnDomain re=new ReturnDomain();
        re.setStatus(0);
        String id=map.get("id");
        if(id.charAt(0)=='E'){
            Employee e=employeeService.findById(id);
            if(e!=null){re.setStatus(1);
            re.setEmployee(e);
            re.setTimeDifference(empTimeDifference(re));
            }
        }
        else if(id.charAt(0)=='L'){
            Leave l=leaveService.findById(id);
        if (l!=null){re.setStatus(2);
            re.setLeave(l);
            re.setTimeDifference(empTimeDifference(re));
        }
        }
        return re;
    }
    @RequestMapping("/revokeById")//撤销未审核的申请
    public @ResponseBody
    ReturnType revokeById(HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        ReturnType re=new ReturnType();
        int status;
        re.setStatus(0);
//        if(!map.containsKey("id")||map.get("id").isEmpty()){
//            re.setStatus(-1);
//            re.setReason("没有所需参数");
//        return re;}
        String id=map.get("id");
        if(id.charAt(0)=='E'){
            status=(employeeService.editRevokeById(id));
            if(status==1){re.setId(id);
            re.setStatus(1);}
            else {re.setReason("服务器出错");}
        }
        else if (id.charAt(0)=='L'){
            status=(leaveService.editRevokeById(id));
            if(status==1){re.setId(id);
                re.setStatus(1);}
            else {re.setReason("服务器出错");}
        }
        return re;
    }
    @RequestMapping("/findUncheckCount")//查询审核人未审核的条数
    public @ResponseBody int  findUncheckCount(HttpServletResponse response,@RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return employeeService.findUncheckCount(map.get("auditor"));
    }

    @RequestMapping("/delEmp")//删除申请信息
    public @ResponseBody int  delEmpById(HttpServletResponse response,@RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String id=map.get("id");
        char i=id.charAt(0);//id首字母
        if(i=='E'){return employeeService.delEmpById(id);}//删除加班
        else if(i=='L'){return leaveService.delLeaveById(id);}//删除请假
        else return -1;
    }
    @RequestMapping(value="/listCountByDate")//查询近一周每天申请数
    public @ResponseBody List<DateCount> findCountByDate(HttpServletResponse response,@RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        List<DateCount> list=new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        for(int i=0;i<7;i++){
            Calendar c1 = Calendar.getInstance();
            c1.add(Calendar.DAY_OF_YEAR, -i);
            Date dateFrom = c1.getTime();
            List<String> names=employeeService.findNameByDate(sdf.format(dateFrom),map.get("dept"));
            if(!names.isEmpty()){
                DateCount dateCount=new DateCount();
                dateCount.setDate(sdf.format(dateFrom));
                dateCount.setNames(names);
                list.add(dateCount);
            } }
        return list;}
        private String empTimeDifference(ReturnDomain re)throws Exception{
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
            long nh = 1000 * 60 * 60;// 一小时的毫秒数
            long nm = 1000 * 60;// 一分钟的毫秒数
            Date date1;
            if (re.getStatus()==1){date1=sdf1.parse(re.getEmployee().getSubmittime());}
            else {date1=sdf1.parse(re.getLeave().getSubmittime());}
            Date date2=new Date();
            String timeDifference="已等待";
            long number = date2.getTime()-date1.getTime();
            long day = number / nd;// 计算差多少天
            long hour = number % nd / nh + day * 24;// 计算差多少小时
            long min = number % nd % nh / nm ;// 计算差多少分钟
            if(hour>24){timeDifference+=day+"天"+number%nd/nh+"时";}
            else if (hour>1){timeDifference+=number%nd/nh+"时"+min+"分";}
            else {timeDifference+=min+"分";}
            return timeDifference;
        }
    @RequestMapping(method= RequestMethod.POST,value="/add")//申请加班
    public  @ResponseBody
    ReturnType add(HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Employee e= new Employee();
        e.setUname(map.get("name"));
        e.setDept(map.get("dept"));
        e.setUdate(map.get("date1").replace("-","/"));
        e.setStarttime(map.get("time1"));
        e.setEndtime(map.get("time2"));
        e.setAuditor(map.get("auditors"));
        e.setReason(map.get("reason"));
        e.setStatus("unchecked");
        e.setSubmittime(sdf.format(new Date()));
        return employeeService.add(e);
    }
}
//    @RequestMapping("/listEveryEmp")//查询一个部门所有加班记录，暂不用
//    public @ResponseBody List<Employee> findEveryEmp(HttpServletResponse response,@RequestBody Map<String, String> map) throws Exception{
//        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        return EmployeeService.findEveryEmp(map.get("dept"));
//    }

//    @RequestMapping("/listByDate")//查询近一周所有未审核加班记录，暂不用
//    public @ResponseBody List<Object> findEmpDate(HttpServletResponse response,@RequestBody Map<String, String> map) throws Exception{
//        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        List<Object> list=new ArrayList<Object>();
//        for(int i=0;i<7;i++){
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//            Calendar cl = Calendar.getInstance();
//            cl.add(Calendar.DAY_OF_YEAR, -i);
//            Date dateFrom = cl.getTime();
//            List<Employee> list1=EmployeeService.findEmpByDate(sdf.format(dateFrom),map.get("dept"));
//            if(list1.size()>0){
//            list.add(list1);}
//        }
//        return list;
//    }

//    @RequestMapping("/list")//按姓名查询加班记录，暂不用
//    public @ResponseBody List<NameEmp> findEveryName(HttpServletResponse response) throws Exception{
//        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        List<String> list=EmployeeService.findEveryName();//查询所有记录姓名(不重复)
//        List<NameEmp> list1=new ArrayList<NameEmp>();
//        for(int i=0;i<list.size();i++){
//            NameEmp n=new NameEmp();
//            n.setUname(list.get(i));
//            n.setList(EmployeeService.findByName(list.get(i)));
//            list1.add(n);
//            System.out.println(n);
//        }
//        return list1;
//    }



//    @RequestMapping("/editEmp")//修改申请信息,已弃用
//    public @ResponseBody int  editEmp(HttpServletResponse response,@RequestBody Map<String, String> map) throws Exception{
//        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//            Employee e= new Employee();
//            e.setId(map.get("id"));
//            e.setUdate(map.get("date1"));
//            e.setStarttime(map.get("time1"));
//            e.setEndtime(map.get("time2"));
//            e.setReason(map.get("reason"));
//        return EmployeeService.editEmp(e);
//
//    }
//    @RequestMapping("/jsonp")
//    public String jsonp(HttpServletRequest request, HttpServletResponse response) throws Exception{
////        System.out.println(map.get("temp1"));
//        return "index";
//    }
//    @RequestMapping("/COR")
//    public String cors(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        return "CORS";
//    }
//}
