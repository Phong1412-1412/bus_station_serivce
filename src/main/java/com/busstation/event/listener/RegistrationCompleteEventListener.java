package com.busstation.event.listener;

import com.busstation.entities.User;
import com.busstation.event.RegistrationCompleteEvent;
import com.busstation.services.AuthService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final AuthService authService;
    private final JavaMailSender javaMailSender;
    private User theUser;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //1. Get the newly registered user
        theUser = event.getUser();
        //2. Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();
        //3. Save the verification token for the user
        authService.saveUseVerificationToken(theUser, verificationToken);
        //4. Build the verification url to be sent to the user
        String url = event.getApplicationUrl()+"/api/v1/auth/verifyEmail?token="+verificationToken;
        //5. Sen the email
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration :  {}", url);
    }
    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "User Registration Transportation Company Management Website";
        String mailContent = "<html><body>" +
                "<h2>Hi " + theUser.getFullName() + ",</h2>" +
                "<p>Thank you for registering with us. Please verify your email address to activate your account by clicking the button below:</p>" +
                "<div style=\"text-align: center;\">" +
                "<a href=\"" + url + "\" style=\"background-color: #4CAF50; border: none; color: white; padding: 15px 32px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin: 4px 2px; cursor: pointer;\">Verify Email</a>" +
                "</div>" +
                "<p>If you did not register with us, please ignore this email.</p>" +
                "<p>Thank you for using our service.</p>" +
                "</body></html>";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("usnguyen@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}
