package utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import vo.WxMssVo;

import java.util.HashMap;
import java.util.Map;

public class WxTemplate {
    @SuppressWarnings("unchecked")
    public static Map<String,String> getUserinfo(String openid2) {//获得用户关于公众号的详细信息
        String access_token=getAccess_token();
        String userInfo=HttpUtil.get("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid2+"&lang=zh_CN");
        ObjectMapper mapper2 = new ObjectMapper();
        Map<String, String> json2=null;
        try {
        json2 = mapper2.readValue(userInfo, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json2;
    }
    @SuppressWarnings("unchecked")
    public static void sendPassMsg(String openid2,String id,String type,String checkTime) {
        WxMssVo wx=new WxMssVo();
        wx.setTouser(openid2);
        wx.setTemplate_id("1FePKYxjLfISyDLvuzDXjOmIk3QmG3EMbusfNQD76Lk");
        Map<String,String> miniprogram=new HashMap<>();
        miniprogram.put("appid","appid");
        miniprogram.put("pagepath","pages/checked/checked?id="+id);
        Map<String,String> first=new HashMap<>();
        Map<String,String> keyword1=new HashMap<>();
        Map<String,String> keyword2=new HashMap<>();
        Map<String,String> remark=new HashMap<>();
        first.put("value","您的"+type+"申请已通过审核");
        first.put("color","#173177");
        keyword1.put("value","申请通过");
        keyword1.put("color","#173177");
        keyword2.put("value",checkTime);
        keyword2.put("color","#173177");
        remark.put("value","点击查看详情");
        remark.put("color","#173177");
        Map<String, Map<String,String>> map= new HashMap<>();
        map.put("first",first);
        map.put("keyword1",keyword1);
        map.put("keyword2",keyword2);
        map.put("remark",remark);
        wx.setData(map);
        String jsonString = JSON.toJSONString(wx);
        String access_token=getAccess_token();
        String data= HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,jsonString);
        System.out.println(data);
    }
    @SuppressWarnings("unchecked")
    public static void sendUnpassMsg(String openid2,String id,String type,String checkReason) {
        WxMssVo wx=new WxMssVo();
        wx.setTouser(openid2);
        wx.setTemplate_id("c9W0XZLOTPtV0hI8gMehiZ4MdmZumXHqJNNEjERnFuE");
        Map<String,String> miniprogram=new HashMap<>();
        miniprogram.put("appid","appid");
        miniprogram.put("pagepath","pages/checked/checked?id="+id);
        Map<String,String> first=new HashMap<>();
        Map<String,String> keyword1=new HashMap<>();
        Map<String,String> keyword2=new HashMap<>();
        Map<String,String> remark=new HashMap<>();
        first.put("value","您的"+type+"申请未能通过审核");
        first.put("color","#173177");
        keyword1.put("value","未通过");
        keyword1.put("color","#173177");
        keyword2.put("value",checkReason);
        keyword2.put("color","#173177");
        remark.put("value","点击查看详情");
        remark.put("color","#173177");
        Map<String, Map<String,String>> map= new HashMap<>();
        map.put("first",first);
        map.put("keyword1",keyword1);
        map.put("keyword2",keyword2);
        map.put("remark",remark);
        wx.setData(map);
        String jsonString = JSON.toJSONString(wx);
        String access_token=getAccess_token();
        String data= HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,jsonString);
        System.out.println(data);
    }
    @SuppressWarnings("unchecked")
    public static void sendUncheckMsg(String openid2,String id,String type,String uname,String date,String reason) {
        WxMssVo wx=new WxMssVo();
        wx.setTouser(openid2);
        wx.setTemplate_id("OlRlpRH4-Ll3pCAYZNKQSfQxLG1FXeYI7GcT5dvpJAk");
        Map<String,String> miniprogram=new HashMap<>();
        miniprogram.put("appid","appid");
        miniprogram.put("pagepath","pages/checked/checked?id="+id);
        Map<String,String> first=new HashMap<>();
        first.put("value","有一条新的"+type+"申请待您审核");
        first.put("color","#173177");
        Map<String,String> keyword1=new HashMap<>();
        Map<String,String> keyword2=new HashMap<>();
        Map<String,String> keyword3=new HashMap<>();
        Map<String,String> remark=new HashMap<>();
        keyword1.put("value",uname);
        keyword1.put("color","#173177");
        keyword2.put("value",type+"登记");
        keyword2.put("color","#173177");
        keyword3.put("value",date);
        keyword3.put("color","#173177");
        remark.put("value",type+"原因: "+reason);
        remark.put("color","#173177");
        Map<String, Map<String,String>> map= new HashMap<>();
        map.put("first",first);
        map.put("keyword1",keyword1);
        map.put("keyword2",keyword2);
        map.put("keyword3",keyword3);
        map.put("remark",remark);
        wx.setData(map);
        String jsonString = JSON.toJSONString(wx);
        String access_token=getAccess_token();
        String data= HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,jsonString);
        System.out.println(data);
    }
//    @SuppressWarnings("unchecked")
//    public static void sendRevokeMsg(String openid2) {
//        WxMssVo wx=new WxMssVo();
//        wx.setTouser(openid2);
//        wx.setTemplate_id("luuhWuSFWjve_AZ9oJmwuCPAhzrZWzfMUEFFsKl6CMo");
//        String jsonString = JSON.toJSONString(wx);
//        String access_token=CacheManager.get("access_token");
//        if(access_token==null){
//            access_token=HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxf94501534eca9f66&secret=5a8a0bd3be7314e21e7f718222199c84");
//            ObjectMapper mapper = new ObjectMapper();
//            Map<String, String> json;
//            try {
//                json = mapper.readValue(access_token, Map.class);
//                access_token=json.get("access_token");
//                CacheManager.set("access_token",access_token,7200*1000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        String data= HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,jsonString);
//        System.out.println(data);
//    }
@SuppressWarnings("unchecked")
public static String getAccess_token() {//获得公众号access_token
    String access_token=CacheManager.get("access_token");
    if(access_token==null){
        access_token=HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> json;
        try {
            json = mapper.readValue(access_token, Map.class);
            access_token=json.get("access_token");
            CacheManager.set("access_token",access_token,7200*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return access_token;
}
}
