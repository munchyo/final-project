package com.goodday.proj.api.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    @Value(value = "${spring.mail.username}")
    private String setFrom;

    private final JavaMailSenderImpl mailSender;

    public int makeRandomNumber() {
        Random r = new Random();
        return r.nextInt(888888) + 111111;
    }

    @Async("threadPoolTaskExecutor")
    public void sendMail(String setFrom, String setTo, String title, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        helper.setFrom(setFrom);
        helper.setTo(setTo);
        helper.setSubject(title);
        helper.setText(content, true);

        mailSender.send(message);
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<String> registerAuthentication(String email) throws MessagingException {
        int authNumber = makeRandomNumber();
        String setTo = email;
        String title = "GoodDay에 오신것을 환영합니다!";
        String content = "<tbody><tr><td><table align='center' width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0;background-color:#eb5a59;background-image:url(https://d1oco4z2z1fhwp.cloudfront.net/templates/default/3966/Fondo_Rayos.png);background-position:center top;background-repeat:no-repeat'><tbody><tr><td><table align='center' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0;color:#000;width:650px;margin:0 auto' width='650'><tbody><tr><td width='100%' style='mso-table-lspace:0;mso-table-rspace:0;text-align:left;font-weight:400;padding-bottom:35px;padding-left:20px;padding-right:20px;padding-top:20px;vertical-align:top;border-top:0;border-right:0;border-bottom:0;border-left:0'><table width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='text-align:center;width:100%'><h3 style='margin:0;color:#fff;direction:ltr;font-family:Shrikhand,Impact,Charcoal,sans-serif;font-size:24px;font-weight:400;letter-spacing:2px;line-height:120%;text-align:center;margin-top:0;margin-bottom:0'><span>WELCOME</span></h3></td></tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='padding-bottom:5px;padding-left:10px;padding-right:10px;padding-top:10px'><div align='center'><table border='0' cellpadding='0' cellspacing='0' width='90%' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='font-size:1px;line-height:1px;border-top:3px dashed #fff'><span> </span></td></tr></tbody></table></div></td></tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='text-align:center;width:100%'><h1 style='margin:0;color:#fff;direction:ltr;font-family:Shrikhand,Impact,Charcoal,sans-serif;font-size:34px;font-weight:400;letter-spacing:2px;line-height:120%;text-align:center;margin-top:0;margin-bottom:0'><strong>GOOD DAY</strong></h1></td></tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='padding-bottom:10px;padding-left:10px;padding-right:10px;padding-top:5px'><div align='center'><table border='0' cellpadding='0' cellspacing='0' width='90%' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='font-size:1px;line-height:1px;border-top:3px dashed #fff'><span> </span></td></tr></tbody></table></div></td></tr></tbody></table><div style='height:20px;line-height:20px;font-size:1px'> </div><table width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='width:100%'><div align='center' style='line-height:10px'><img src='https://d1oco4z2z1fhwp.cloudfront.net/templates/default/3966/Emoji_animation.gif' style='height:auto;display:block;border:0;max-width:340px;width:100%' width='340' alt='Emoji Animation' title='Emoji Animation' loading='lazy'></div></td></tr></tbody></table><div style='height:30px;line-height:30px;font-size:1px'> </div><table width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='text-align:center;width:100%'><h1 style='margin:0;color:#fff;direction:ltr;font-family:Shrikhand,Impact,Charcoal,sans-serif;font-size:38px;font-weight:400;letter-spacing:2px;line-height:120%;text-align:center;margin-top:0;margin-bottom:0'><span>" + authNumber + "</span></h1></td></tr></tbody></table><div style='height:30px;line-height:30px;font-size:1px'> </div><table width='100%' border='0' cellpadding='10' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td><div align='center'>\n" +
                "  <!--[if mso]><xroundrect href='https://www.example.com' style='height:45px;width:146px;v-text-anchor:middle;'><xanchorlock></xanchorlock><xtextbox><center style='color:#ffffff; font-family:sans-serif; font-size:18px'><![endif]-->\n" +
                "  <a href='http://192.168.20.217:3000/' target='_blank' style='text-decoration:none;display:inline-block;color:#ffffff;background-color:#feac04;border-radius:4px;:auto;border-top:2px solid #ffffff;font-weight:undefined;border-right:2px solid #ffffff;border-bottom:2px solid #ffffff;border-left:2px solid #ffffff;padding-top:10px;padding-bottom:10px;font-family:varela round, trebuchet ms, helvetica, sans-serif;font-size:18px;text-align:center;' rel='noreferrer noopener'><span style='padding-left:30px;padding-right:30px;font-size:18px;display:inline-block;letter-spacing:normal;'><span style='word-break:break-word;'><span style='line-height: 21.6px;'><strong>GO HOME</strong></span></span></span></a><!--[if mso]></center></xtextbox></xroundrect><![endif]--></div></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table>";
        sendMail(setFrom, setTo, title, content);
        return CompletableFuture.completedFuture(Integer.toString(authNumber));
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<String> findAuthentication(String email, String find) throws MessagingException {
        int authNumber = makeRandomNumber();
        String setTo = email;
        String title = "GoodDay " + find + " 찾기 인증메일 입니다.";
        String content = "<tbody><tr><td><table align='center' width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0;background-color:#eb5a59;background-image:url(https://d1oco4z2z1fhwp.cloudfront.net/templates/default/3966/Fondo_Rayos.png);background-position:center top;background-repeat:no-repeat'><tbody><tr><td><table align='center' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0;color:#000;width:650px;margin:0 auto' width='650'><tbody><tr><td width='100%' style='mso-table-lspace:0;mso-table-rspace:0;text-align:left;font-weight:400;padding-bottom:35px;padding-left:20px;padding-right:20px;padding-top:20px;vertical-align:top;border-top:0;border-right:0;border-bottom:0;border-left:0'><table width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='text-align:center;width:100%'><h3 style='margin:0;color:#fff;direction:ltr;font-family:Shrikhand,Impact,Charcoal,sans-serif;font-size:24px;font-weight:400;letter-spacing:2px;line-height:120%;text-align:center;margin-top:0;margin-bottom:0'><span>WELCOME</span></h3></td></tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='padding-bottom:5px;padding-left:10px;padding-right:10px;padding-top:10px'><div align='center'><table border='0' cellpadding='0' cellspacing='0' width='90%' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='font-size:1px;line-height:1px;border-top:3px dashed #fff'><span> </span></td></tr></tbody></table></div></td></tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='text-align:center;width:100%'><h1 style='margin:0;color:#fff;direction:ltr;font-family:Shrikhand,Impact,Charcoal,sans-serif;font-size:34px;font-weight:400;letter-spacing:2px;line-height:120%;text-align:center;margin-top:0;margin-bottom:0'><strong>GOOD DAY</strong></h1></td></tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='padding-bottom:10px;padding-left:10px;padding-right:10px;padding-top:5px'><div align='center'><table border='0' cellpadding='0' cellspacing='0' width='90%' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='font-size:1px;line-height:1px;border-top:3px dashed #fff'><span> </span></td></tr></tbody></table></div></td></tr></tbody></table><div style='height:20px;line-height:20px;font-size:1px'> </div><table width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='width:100%'><div align='center' style='line-height:10px'><img src='https://d1oco4z2z1fhwp.cloudfront.net/templates/default/3966/Emoji_animation.gif' style='height:auto;display:block;border:0;max-width:340px;width:100%' width='340' alt='Emoji Animation' title='Emoji Animation' loading='lazy'></div></td></tr></tbody></table><div style='height:30px;line-height:30px;font-size:1px'> </div><table width='100%' border='0' cellpadding='0' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td style='text-align:center;width:100%'><h1 style='margin:0;color:#fff;direction:ltr;font-family:Shrikhand,Impact,Charcoal,sans-serif;font-size:38px;font-weight:400;letter-spacing:2px;line-height:120%;text-align:center;margin-top:0;margin-bottom:0'><span>" + authNumber + "</span></h1></td></tr></tbody></table><div style='height:30px;line-height:30px;font-size:1px'> </div><table width='100%' border='0' cellpadding='10' cellspacing='0' style='mso-table-lspace:0;mso-table-rspace:0'><tbody><tr><td><div align='center'>\n" +
                "  <!--[if mso]><xroundrect href='https://www.example.com' style='height:45px;width:146px;v-text-anchor:middle;'><xanchorlock></xanchorlock><xtextbox><center style='color:#ffffff; font-family:sans-serif; font-size:18px'><![endif]-->\n" +
                "  <a href='http://192.168.20.217:3000/' target='_blank' style='text-decoration:none;display:inline-block;color:#ffffff;background-color:#feac04;border-radius:4px;:auto;border-top:2px solid #ffffff;font-weight:undefined;border-right:2px solid #ffffff;border-bottom:2px solid #ffffff;border-left:2px solid #ffffff;padding-top:10px;padding-bottom:10px;font-family:varela round, trebuchet ms, helvetica, sans-serif;font-size:18px;text-align:center;' rel='noreferrer noopener'><span style='padding-left:30px;padding-right:30px;font-size:18px;display:inline-block;letter-spacing:normal;'><span style='word-break:break-word;'><span style='line-height: 21.6px;'><strong>GO HOME</strong></span></span></span></a><!--[if mso]></center></xtextbox></xroundrect><![endif]--></div></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table>";
        sendMail(setFrom, setTo, title, content);
        return CompletableFuture.completedFuture(Integer.toString(authNumber));
    }

}