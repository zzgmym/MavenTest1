package service;

import domain.User;
import vo.ReturnType;

import java.util.List;

public interface UserService {
     User login(String userid, String password)throws Exception;
     int EditPassword(String userid,String password1,String password2)throws Exception;
     List<User> findAllAuditor(String dept)throws Exception;
     List<String> findAllAuditorName(String dept)throws Exception;
     ReturnType EditTelephone(String userid, String telephone)throws Exception;
     void InsertOpenidUnionid(String userid,String openid,String unionid)throws Exception;
     String findOpenidByUserid(String userid)throws Exception;
     void addOpenid2ByUnionid(String openid2,String unionid)throws Exception;
}
