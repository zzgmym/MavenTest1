package controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import domain.Employee;
import org.apache.poi.hssf.usermodel.*;

import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.EmployeeService;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/email")
public class PoiController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/email")
    public  @ResponseBody int getAttachment(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map)throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if(!map.containsKey("udate")||map.get("udate").isEmpty()){
            return 0;//"参数不正确"
        }
        String udate=map.get("udate");
        String[] date=udate.split(",");
        List<InputStream> inputStreamList= new ArrayList<>();
        for(String s:date){
        List<Employee> list=employeeService.findEmpByDate2(s.replace('-','/'),map.get("dept"));
        if(list.isEmpty()){return -1;}//"不存在可导出数据"
        else{
            // 获取模板
            String path = request.getSession().getServletContext().getRealPath("WEB-INF/excel/加班.xls");
            FileInputStream template = new FileInputStream(path);
            // 如果是xlsx，2007，用XSSF,如果是xls，2003，用HSSF
            // 一个Excel文件的层次：Excel文件-> 工作表-> 行-> 单元格 对应到POI中，为：workbook-> sheet-> row-> cell
            HSSFWorkbook workBook=new HSSFWorkbook(template);
            // 获取第一个sheet页
            HSSFSheet sheet=workBook.getSheetAt(0);

             //创建剧中样式
            HSSFCellStyle cellStyle = workBook.createCellStyle();
            // 垂直居中
            cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
            // 水平居中
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 创建字体
//            HSSFFont cellFont = workBook.createFont();
//            cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//            cellFont.setFontHeight((short) 300);
//            cellStyle.setFont(cellFont);
//            cellTitle.setCellStyle(cellStyle);
            //设置部门，加班日期
            HSSFRow row3=sheet.createRow(3);
            row3.createCell(0).setCellValue("部门："+list.get(0).getDept()+"                                       "+"加班日期："+list.get(0).getUdate());

            /**********************遍历加班，并将加班列表显示到模板中，作为邮件的附件***********************/
            // 行号下标，从0开始
            int rowIndex =4;
            for(int j=0;j<list.size();j++){
                rowIndex++;
                // 创建行
                HSSFRow row=sheet.createRow(rowIndex);
                // 根据下标创建单元格
                // 设置物料序号
//                row.createCell(0).setCellValue(j+1);
                // 设置姓名
                if(null != list.get(j).getUname()){
                    HSSFCell cell=row.createCell(0);
                   cell .setCellValue(list.get(j).getUname());
                   cell.setCellStyle(cellStyle);
                }else {
                    row.createCell(0).setCellValue("");
                }

                // 设置加班开始时间
                if(null != list.get(j).getStarttime()){
                    HSSFCell cell=row.createCell(1);
                    cell .setCellValue(list.get(j).getStarttime());
                    cell.setCellStyle(cellStyle);
                }else {
                    row.createCell(1).setCellValue("");
                }

                // 设置加班结束时间
                if(null != list.get(j).getEndtime()){
                    HSSFCell cell=row.createCell(2);
                    cell .setCellValue(list.get(j).getEndtime());
                    cell.setCellStyle(cellStyle);
                }else {
                    row.createCell(2).setCellValue("");
                }

                // 设置加班时长
                if(0 != list.get(j).getTotaltime()){
                    HSSFCell cell=row.createCell(4);
                    cell .setCellValue(list.get(j).getTotaltime());
                    cell.setCellStyle(cellStyle);
                }else {
                    row.createCell(4).setCellValue(0);
                }

                // 设置加班原因
                if(null != list.get(j).getReason()){
                    HSSFCell cell=row.createCell(6);
                    cell .setCellValue(list.get(j).getReason());
                    cell.setCellStyle(cellStyle);
                }else {
                    row.createCell(6).setCellValue("");
                }

                //设置审核人
                if(null != list.get(j).getReason()){
                    HSSFCell cell=row.createCell(11);
                    cell .setCellValue(list.get(j).getAuditor());
                    cell.setCellStyle(cellStyle);
                }else {
                    row.createCell(11).setCellValue("");
                }
          }
            sheet.addMergedRegion(new CellRangeAddress(rowIndex+1,rowIndex+1,0,11));
            HSSFRow lastrow=sheet.createRow(rowIndex+1);
            HSSFCell cell=lastrow.createCell(0);
            cell .setCellValue("考勤核实:____________ 部门负责人:____________ 主管领导:____________ 人事部确认:____________");

            /***********************准备邮件并发送**********************/
//                    workBook.write(out);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workBook.write(baos);
            baos.flush();
            byte[] bt = baos.toByteArray();
            InputStream is = new ByteArrayInputStream(bt, 0, bt.length);
            baos.close();
            inputStreamList.add(is);
       }}

        String email = map.get("email");
        String tempContent="新证财经加班明细";
        Boolean b= sendMail("新证财经加班明细", email, tempContent , inputStreamList,date);
          return (b?1:-2);//1:发送成功   -2:发送失败
    }
    public static boolean sendMail(String subject, String toMail, String content, List<InputStream> isList,String[] date) {
        boolean isFlag = false;
        try {
            String smtpFromMail = "919680186@qq.com";  //账号
            String pwd = "dpnkqiddcgbqbcca"; //密码
            int port = 587; //端口
            String host = "smtp.qq.com"; //服务器

            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props);
            session.setDebug(false);

            MimeMessage message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress(smtpFromMail, "新证财经加班申请表"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
                message.setSubject(subject);
                message.addHeader("charset", "UTF-8");

                /*添加正文内容*/
                Multipart multipart = new MimeMultipart();
                BodyPart contentPart = new MimeBodyPart();
                contentPart.setText(content);
                // 中文乱码问题
                contentPart.setHeader("Content-Type", "text/html; charset=gb2312");
                multipart.addBodyPart(contentPart);

                /*添加附件*/
                int index=0;
                for (InputStream is:isList){
                MimeBodyPart fileBody = new MimeBodyPart();
                DataSource source = new ByteArrayDataSource(is, "application/msexcel");
                fileBody.setDataHandler(new DataHandler(source));
                String fileName  = date[index]+"加班记录.xls";
                fileBody.setFileName(MimeUtility.encodeText(fileName));
                multipart.addBodyPart(fileBody);
                index++;}

                message.setContent(multipart);
                message.setSentDate(new Date());
                message.saveChanges();
                Transport transport = session.getTransport("smtp");

                transport.connect(host, port, smtpFromMail, pwd);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                isFlag = true;
            } catch (Exception e) {
                e.printStackTrace();
                isFlag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isFlag;
    }
}



