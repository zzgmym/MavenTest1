package service;

import dao.Leavemapper;
import dao.Usermapper;
import domain.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import utils.IdGenertor;
import utils.WxTemplate;
import vo.ReturnType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LeaveServiceimpl implements LeaveService {
//    @Value("#{prop.appId}")
//    private String appId;
//    @Value("#{prop.appSecret}")
//    private String appSecret;
//    @Value("#{prop.grantType}")
//    private String grantType;
    @Autowired
    private Leavemapper leavemapper;
    @Autowired
    private Usermapper usermapper;

    @Override
    public Leave findById(String id)throws Exception{
        return leavemapper.findById(id);
    }
    @Override
    public ReturnType add(Leave l) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        l.setSubmittime(sdf.format(new Date()));
        ReturnType te=new ReturnType();
        String date1=l.getStartdate();
        String date2=l.getEnddate();
        l.setStartdate(date1.replace("-","/"));
        l.setEnddate(date2.replace("-","/"));
        l.setId("L"+ IdGenertor.genOrdernum());
//        String totaltime=l.getTotaltime();
//        int index1=totaltime.indexOf("天");
//        int index2=totaltime.indexOf("时");
//        int day=Integer.parseInt(totaltime.substring(0,index1));
//        double hour=Double.parseDouble(totaltime.substring(index1+1,index2));
//        double time=day*7.5+hour;
        if(leavemapper.add(l)==1) {
            te.setStatus(1); //成功
            te.setId(l.getId());
            String openid=usermapper.findOpenid2ByName(l.getAuditor());//获得审核人openid2
            WxTemplate.sendUncheckMsg(openid,l.getId(),"请假",l.getUname(),l.getStartdate()+"--"+l.getEnddate(),l.getReason());//向审核人发送模板消息
            String openid22=usermapper.findOpenid2ByName(l.getUname());//获得申请人openid2
            Map<String,String> userInfo= WxTemplate.getUserinfo(openid22);//获得申请人关于公众号的详细信息
            String subscribe=String.valueOf(userInfo.get("subscribe"));//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号
            if(subscribe.equals("0")){te.setStatus(2);te.setReason("申请人未订阅公众号");}
        }//返回id
        else{te.setStatus(0);}//添加失败
        return te;
    }
    @Override
    public List<Leave> listByName(String uname)throws Exception{
        return leavemapper.listByName(uname);
    }
    @Override
    public List<Leave> listUncheck(String auditors)throws Exception{
        return leavemapper.listUncheck(auditors);
    }
    @Override
    public List<Leave> listChecked(String auditor)throws Exception{
        return leavemapper.listUncheck(auditor);
    }
    @Override
    public List<String> isDateRepeat(String uname, String startDate, String endDate)throws Exception{
        return leavemapper.isDateRepeat(uname,startDate,endDate);
    }
    @Override
    public int delLeaveById(String id)throws Exception{
        return leavemapper.delLeaveById(id);
    }
    @Override
    public int editPassByIds(String id,String auditor,String checkreason)throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String checktime=sdf.format(new Date());
        Leave l=leavemapper.findById(id);
        String openid=usermapper.findOpenid2ByName(l.getUname());
            if(leavemapper.passById(id,auditor,checktime,checkreason)==1){
                WxTemplate.sendPassMsg(openid,id,"请假",checktime);
                return 1;}//update成功
        else {return 0;}//失败
    }
    @Override
    public int editUnpassByIds(String id,String auditor,String checkreason)throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String checktime=sdf.format(new Date());
        Leave l=leavemapper.findById(id);
        String openid=usermapper.findOpenid2ByName(l.getUname());
            if(leavemapper.unpassById(id,auditor,checktime,checkreason)==1){
                WxTemplate.sendUnpassMsg(openid,id,"请假",checkreason);
                return 1;}//update失败
            else {return 0;}//失败
    }
    @Override
    public int editRevokeById(String id)throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String revokeTime=sdf.format(new Date());
        Leave l=leavemapper.findById(id);
//        String openid=usermapper.findOpenid2ByName(l.getAuditor());
//        WxTemplate.sendRevokeMsg(openid);
        return leavemapper.revokeById(id,revokeTime);
    }
}
//        long minutes;//请假时间分钟数
//        String a=l.getStarttime();
//        String b=l.getEndtime();
//        String[] c=a.split(":");
//        String[] d=b.split(":");
//        int m=Integer.valueOf(c[0]);
//        int n=Integer.valueOf(c[1]);
//        int x=Integer.valueOf(d[0]);
//        int y=Integer.valueOf(d[1]);
//        if(l.getStartdate().equals(l.getEnddate())){//请假时间在一天内
//             minutes=((x*60)+y)-((m*60)+n);//请假分钟数
//        }
//        else{//请假时间不同天
//            int days = (int) ((sd.parse(l.getEnddate()).getTime() - sd.parse(l.getStartdate()).getTime()) / (1000*3600*24));//相隔天数
//            minutes=((19*60)-((m*60)+n))+ (days-1)*24*60+(((x*60)+y)-(10*60));//请假分钟数
//        }
//        float t =(float) ((minutes/60)+(minutes%60)/30*0.5);
//        l.setTotaltime(t);//set请假时长