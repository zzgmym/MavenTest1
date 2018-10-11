package service;

import dao.Usermapper;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.IdGenertor;
import vo.ReturnType;

import java.util.List;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private Usermapper usermapper;
    @Override
    public User login(String userid, String password) throws Exception {
        return usermapper.login(userid, password);
    }
    @Override
    public int EditPassword(String userid,String password1,String password2)throws Exception{
        return usermapper.EditPassword(userid,password1,password2);
    }
    @Override
    public List<User> findAllAuditor(String dept)throws Exception{
        return usermapper.findAllAuditor(dept);
    }
    @Override
    public List<String> findAllAuditorName(String dept)throws Exception{
        return usermapper.findAllAuditorName(dept);
    }
    @Override
    public ReturnType EditTelephone(String userid, String telephone)throws Exception{
        ReturnType re=new ReturnType();
        if(!IdGenertor.isNumeric(telephone)){
            re.setStatus(-1);
            re.setReason("号码格式不正确");
            return re;
        }
        if(telephone.length()!=11){
            re.setStatus(-1);
            re.setReason("号码长度不正确");
            return re;
        }
        re.setStatus(usermapper.EditTelephone(userid,telephone));
        if (re.getStatus()==0){re.setReason("系统出错，修改失败");}
        if (re.getStatus()==1){re.setReason("修改成功");}
        return re;
    }
    @Override
    public void InsertOpenidUnionid(String userid,String openid,String unionid)throws Exception{
         usermapper.InsertOpenidUnionid(userid,openid,unionid);
    }
    @Override
    public String findOpenidByUserid(String userid)throws Exception{
        return usermapper.findOpenidByUserid(userid);
    }
    @Override
    public void addOpenid2ByUnionid(String openid2,String unionid)throws Exception{
         usermapper.addOpenid2ByUnionid(openid2,unionid);
    }
}