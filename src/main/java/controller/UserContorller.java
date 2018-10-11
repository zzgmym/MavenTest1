package controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.UserService;
import utils.CheckUtil;
import utils.HttpUtil;
import utils.JWT;


import utils.WxTemplate;
import vo.ReturnType;
import vo.Usertoken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserContorller {
    @Value("#{prop.appId}")
    private String appId;

    @Value("#{prop.appSecret}")
    private String appSecret;

    @Value("#{prop.grantType}")
    private String grantType;
    // wx.grantType=authorization_code

    @Value("#{prop.requestUrl}")
    private String requestUrl;
    @Autowired
    private UserService loginService;

    @RequestMapping(method=RequestMethod.POST,value="/login")//登录
    public @ResponseBody
    Usertoken login(HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String userid="";
        String password="";
        String token="";
        if(map.containsKey("userid")){
            userid=map.get("userid");
        }
        if(map.containsKey("password")){
            password=map.get("password");
        }
        User user=loginService.login(userid,password);
        if(user!=null){
            token = JWT.sign(user, 30L * 24L * 3600L * 1000L);
            Map<String, String> sessionMap=getSessionByCode(map.get("code"));
            String openid=sessionMap.get("openid");
            String unionid=sessionMap.get("unionid");
            if(openid!=null){loginService.InsertOpenidUnionid(userid,openid,unionid);}
            Usertoken u=new Usertoken(user,token);
        return u;}
        else {return null;}
    }
    @RequestMapping(method=RequestMethod.POST,value="/editpassword")//修改密码
    public @ResponseBody int EditPassword(HttpServletResponse response,@RequestBody Map<String, String> map)throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
       String userid=map.get("userid");
       String password1=map.get("oldPwd");
       String password2=map.get("newPwd");
       return   loginService.EditPassword(userid,password1,password2);
    }
    @RequestMapping(method=RequestMethod.POST,value="/editTelephone")//修改电话
    public @ResponseBody
    ReturnType EditTelephone(HttpServletResponse response, @RequestBody Map<String, String> map)throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return loginService.EditTelephone(map.get("username"),map.get("telephone"));
    }

    @RequestMapping(method=RequestMethod.POST,value="/autologin")//自动登录
    public @ResponseBody String getInfo(HttpServletResponse response,@RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String token = map.get("token");
		User user = JWT.unsign(token, User.class);
		if (user==null){return "0";}
		User user1=loginService.login(user.getUserid(),user.getPassword());
		if (user1!= null) {
		    return "1";
        }
        else {
            return "0";
        }

    }
    @RequestMapping(value="/listAuditor")//返回所有管理员
    public @ResponseBody List<User> findAllAuditor(HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return loginService.findAllAuditor(map.get("dept"));
    }
    @RequestMapping(value="/listAuditorName")//返回所有管理员姓名
    public @ResponseBody List<String> findAllAuditorName(HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
      return loginService.findAllAuditorName(map.get("dept"));
    }
    @SuppressWarnings("unchecked")//获取小程序用户的openid,unionid
    private Map<String, String> getSessionByCode(String code) {
        String url = this.requestUrl + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type="
                + grantType;
        // 发送请求
        String data = HttpUtil.get(url);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> json = null;
        try {
            json = mapper.readValue(data, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 形如{"session_key":"6w7Br3JsRQzBiGZwvlZAiA==","openid":"oQO565cXXXXXEvc4Q_YChUE8PqB60Y"}的字符串
        return json;
    }
    @SuppressWarnings("unchecked")
    private Map<String, String> getOpenidByCode(String code) {//获取公众号openid
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code="+code+"&grant_type=authorization_code";
        // 发送请求
        String data = HttpUtil.get(url);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> json = null;
        try {
            json = mapper.readValue(data, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 形如{"session_key":"6w7Br3JsRQzBiGZwvlZAiA==","openid":"oQO565cXXXXXEvc4Q_YChUE8PqB60Y"}的字符串
        return json;
    }
    @RequestMapping(value="/getOpenid2")//获得公众号openid
    public @ResponseBody void getOpenid(HttpServletRequest request,HttpServletResponse response) throws Exception{
    response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String code=request.getParameter("code");
        String openid2=getOpenidByCode(code).get("openid");
        Map<String,String> userinfo=WxTemplate.getUserinfo(openid2);
        String unionid=userinfo.get("unionid");
        loginService.addOpenid2ByUnionid(openid2,unionid);
    }
    @RequestMapping("/tokenCheck")
    public @ResponseBody void tokenCheck(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        PrintWriter out = resp.getWriter();
        if(CheckUtil.checkSignature(signature, timestamp, nonce)){
            out.print(echostr);
        }
    }
}

