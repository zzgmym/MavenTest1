package controller;


import domain.Leave;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.LeaveService;
import utils.IdGenertor;
import vo.ReturnType;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/leave")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @RequestMapping("/add")//添加请假申请
    public @ResponseBody
    ReturnType add(HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        Leave leave = new Leave();
        leave.setUname(map.get("uname"));
        leave.setDept(map.get("dept"));
        leave.setStartdate(map.get("startdate"));
        leave.setEnddate(map.get("enddate"));
        leave.setStarttime(map.get("starttime"));
        leave.setEndtime(map.get("endtime"));
        leave.setUtype(map.get("utype"));
        leave.setAuditor(map.get("auditor"));
        leave.setReason(map.get("reason"));
        leave.setTotaltime(map.get("totaltime"));
        leave.setStatus("unchecked");
        //判断请假日期是否重复提交
        List<String> list= leaveService.isDateRepeat(leave.getUname(),leave.getStartdate().replace("-","/"),leave.getEnddate().replace("-","/"));
        if(!list.isEmpty()){
            ReturnType te=new ReturnType();
            te.setStatus(-1);//请假日期重合
            return te;
        }
        return leaveService.add(leave);
    }
    @RequestMapping(method = RequestMethod.POST, value = "/add2")
    public @ResponseBody
    ReturnType addLeave(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //判断表单是不是multipart/form-data类型的
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            throw new RuntimeException("The form is not multipart/form-data");
        }
        //解析请求内容
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        List<FileItem> items = new ArrayList<FileItem>();
        try {
            items = sfu.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        Leave l = new Leave();
        for (FileItem item : items) {
            //普通字段：把数据封装到Book对象中
            if (item.isFormField()) {
                processFormFiled(item, l);
            } else {
                List<String> list=leaveService.isDateRepeat(l.getUname(),l.getStartdate().replace("-","/"),l.getEnddate().replace("-","/"));
                if(!list.isEmpty()){
                    ReturnType te=new ReturnType();
                    te.setStatus(-1);
                    return te;
                }
                //上传字段：上传
                processUploadFiled(request, item, l);
            }
        }

        l.setStatus("unchecked");
        return leaveService.add(l);
    }

    //处理文件上传
    private void processUploadFiled(HttpServletRequest request, FileItem item, Leave l) {
        //存放路径：不要放在WEB-INF中
        String storeDirectory = request.getSession().getServletContext().getRealPath("/images");
        File rootDirectory = new File(storeDirectory);
        if (!rootDirectory.exists()) {
            rootDirectory.mkdirs();
        }
        //搞文件名
        String filename = item.getName();//  a.jpg
        if (filename != null) {
            filename = IdGenertor.genGUID() + "." + FilenameUtils.getExtension(filename);//LKDSJFLKSFKS.jpg
            if (l.getImgname() == null) {
                l.setImgname(filename);
            } else {
                l.setImgname(l.getImgname() + "," + filename);
            }
        }
        //设置子路径
        String path = l.getImgpath();
        if (l.getImgpath() == null) {
            l.setImgpath(path);
        }
        //文件上传
        try {
            File file = new File(rootDirectory, path);
            if (!file.exists()) {
                file.mkdirs();
            }
            item.write(new File(rootDirectory, path + "/" + filename));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    //把FileItem中的数据封装到Book中
    private void processFormFiled(FileItem item, Leave l) {
        try {
            String fieldName = item.getFieldName();//name
            String fieldValue = item.getString("UTF-8");//jpm
            BeanUtils.setProperty(l, fieldName, fieldValue);
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }

    }
}
//    @RequestMapping("/listByName")//返回给出名字的所有请假记录
//    public @ResponseBody List<Leave> listByName(HttpServletResponse response,@RequestBody Map<String, String> map)throws Exception{
//        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        return LeaveService.listByName(map.get("uname"));
//    }
//    @RequestMapping("/listUncheck")//返回该审核员未审核的请假记录
//    public @ResponseBody List<Leave> listUncheck(HttpServletResponse response,@RequestBody Map<String, String> map)throws Exception{
//        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        return LeaveService.listUncheck(map.get("auditor"));
//    }
//    @RequestMapping("/listChecked")//返回该审核员已审核的请假记录
//    public @ResponseBody List<Leave> listChecked(HttpServletResponse response,@RequestBody Map<String, String> map)throws Exception{
//        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        return LeaveService.listChecked(map.get("auditor"));
//    }
//    @RequestMapping("/delLeave")//删除给定id请假记录
//    public @ResponseBody int delLeaveById(HttpServletResponse response,@RequestBody Map<String, String> map)throws Exception{
//        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        return LeaveService.delLeaveById(map.get("id"));
//    }
//    @RequestMapping("/passByIds")//通过审核，修改状态及审核人
//    public @ResponseBody int passByIds(HttpServletResponse response,@RequestBody Map<String, String> map) throws Exception{
//        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        return LeaveService.passByIds(map.get("temp1"),map.get("auditor"),map.get("checkreason"));
//    }
//    @RequestMapping("/unpassByIds")//通过审核，修改状态及审核人
//    public @ResponseBody int unpassByIds(HttpServletResponse response,@RequestBody Map<String, String> map) throws Exception{
//        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        return LeaveService.unpassByIds(map.get("temp1"),map.get("auditor"),map.get("checkreason"));
//    }
//}
