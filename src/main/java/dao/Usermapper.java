package dao;

import domain.User;

import java.util.List;

public interface Usermapper {
     User login(String userid, String password);
     int EditPassword(String userid,String password1,String password2);
     int EditTelephone(String userid,String telephone);
     List<User> findAllAuditor(String dept);
     List<String> findAllAuditorName(String dept);
     void InsertOpenidUnionid(String userid,String openid,String unionid);
     String findOpenid2ByName(String uname);
     String findOpenidByUserid(String userid);
     void addOpenid2ByUnionid(String openid2,String unionid);
}
