package com.general.tools.mail;
import java.io.*;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SendMail {
    //发件人地址
    public static String senderAddress = "xukun3@asiainfo.com";
    //收件人地址
    //  public static String recipientAddress = "itellyours@163.com";
    //发件人账户名
    public static String senderAccount = "xukun3@asiainfo.com";
    //发件人账户密码
    public static String senderPassword = "4rfv$RFV";

    public void sendToMail (String sendContent , String recipientAddress) throws Exception {
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "223.71.56.118");
        //   System.out.println(props.getProperty(key));
        //mail.asiainfo.com
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session ,  recipientAddress);
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(senderAccount, senderPassword);
        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg,msg.getAllRecipients());
        //5、关闭邮件连接
        transport.close();
    }

    public static void main(String[] args) throws Exception  {
        SendMail x = new SendMail();
        x.sendToMail("xx", "itellyours@163.com");
    }

    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public static MimeMessage getMimeMessage(Session session ,  String recipientAddress) throws Exception{
        //1.创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //2.设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
        //4.设置邮件主题
        msg.setSubject("[云问卷]中移在线服务有限公司采购项目","UTF-8");

        //下面是设置邮件正文
        //msg.setContent("简单的纯文本邮件！", "text/html;charset=UTF-8");



        // 6. 创建文本"节点"
        MimeBodyPart text = new MimeBodyPart();
        String qnrId = "EXQQUhBCEt1" ;
        String qnrRecId = "GQ9NEhArmtxu4";
        String url = "您好！<br/>中移在线服务有限公司采购项目反馈邮件请确认。<br/><a href = 'http://ywj.10085.cn/look.html?qnrId="+qnrId+"&qnrRecId="+qnrRecId+"'>点击查看内容</a><br/><br/>祝您使用愉快！";
        // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
        text.setContent(url, "text/html;charset=UTF-8");



        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);


        // 9. 创建附件"节点"
        MimeBodyPart attachment = new MimeBodyPart();
        // 读取本地文件
        // DataHandler dh2 = new DataHandler(new FileDataSource("src\\bag.saz"));
        // String fileName  = "D:\\bag.saz";
        InputStream is = new FileInputStream("D:\\a.docx");
        // InputStream is = null ;
        // File iss = new  File("D:\\a.docx");
        String x = "aaa.docx";
        File file = null ;
        file = 	new File(x) ;
        OutputStream os = new FileOutputStream(file);
        try {
            //os = new FileOutputStream(file);
            int len = 0;
            byte[] buffer = new byte[8192];

            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        } finally {
            os.close();
            is.close();
        }


        //  InputStream is = null;
        if (file != null) {
            //DataSource  dh2 = new ByteArrayDataSource(is,"application/octet-stream");
            DataSource dh2 = new FileDataSource(file);
            DataHandler dataHandler = new DataHandler(dh2);
            // 将附件数据添加到"节点"
            attachment.setDataHandler(dataHandler);
            // 设置附件的文件名（需要编码）
            attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
            mm.addBodyPart(attachment);
        }


        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        // 如果有多个附件，可以创建多个多次添加
        //   mm.setSubType("mixed");         // 混合关系

        // 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
        msg.setContent(mm);
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }
}
