package com.busstation.config;


import com.busstation.entities.*;
import com.busstation.exception.DataNotFoundException;
import com.busstation.repositories.OrderRepository;
import com.busstation.repositories.TripRepository;
import com.busstation.repositories.TripUserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;


@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {

    private final JavaMailSender javaMailSender;
    private final TripRepository tripRepository;
    private final OrderRepository orderRepository;
    private final TripUserRepository tripUserRepository;
    private List<Order> orders;
    private User user;
    private Car car;
    private User employee;
    private Trip trip;

    @Scheduled(fixedDelay =1000 * 60 * 60 * 2)
    public void sendEmailScheduler(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrowStart = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth() + 1, 0, 0, 0);
        LocalDateTime tomorrowEnd = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth() + 1, 23, 59, 59);
        orders = orderRepository.findOrderByTripTime(tomorrowStart, tomorrowEnd);
        for (Order order : orders) {
            user = order.getUser();
            if(!order.isSendMail()) {
                trip = tripRepository.findById(order.getTrip().getTripId()).orElse(null);

                car = orderRepository.findByOrderIdAndUserId(order.getOrderID(), user.getUserId());

                employee = tripUserRepository.findInforEmployeeByCarId(car.getCarId()).orElseThrow(() -> new DataNotFoundException("Driver is null"));

                try {
                    sendOderEmail(order, user, car, employee, trip);
                } catch (MessagingException | UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                order.setSendMail(true);
                orderRepository.save(order);
            }
            else{
                System.out.println("Reminder email has been sent to user with name: "+ user.getFullName());
            }
        }
    }

    public void sendOderEmail(Order order, User user, Car car, User employee, Trip trip)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "[Bus Station] Announcement you have a bus tomorrow, Order number: "+ order.getOrderID() ;
        String senderName = "Do not reply this email";
        String mailContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Bus Service Confirmation</title>\n" +
                "    <style>\n" +
                "      @media only screen and (max-width: 600px) {\n" +
                "        table {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "        th {\n" +
                "          font-size: 18px !important;\n" +
                "        }\n" +
                "        td {\n" +
                "          font-size: 16px !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body style=\"font-family: Arial, sans-serif; font-size: 18px; line-height: 1.6;\">\n" +
                "    <table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse; border: 1px solid #ddd; width: 600px; margin: 0 auto;\">\n" +
                "      <tr>\n" +
                "        <th style=\"background-color: #4CAF50; color: #fff; text-align: left; padding: 10px;\">Date and Time</th>\n" +
                "        <td style=\"padding: 10px;\">["+trip.getTimeStart()+"]</td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <th style=\"background-color: #4CAF50; color: #fff; text-align: left; padding: 10px;\">Route</th>\n" +
                "        <td style=\"padding: 10px;\">["+trip.getProvinceStart()+"] - ["+trip.getProvinceEnd()+"]</td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <th style=\"background-color: #4CAF50; color: #fff; text-align: left; padding: 10px;\">Bus Number</th>\n" +
                "        <td style=\"padding: 10px;\">["+car.getCarNumber()+"]</td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <th style=\"background-color: #4CAF50; color: #fff; text-align: left; padding: 10px;\">Driver</th>\n" +
                "        <td style=\"padding: 10px;\">["+employee.getFullName()+"] - ["+employee.getPhoneNumber()+"]</td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <th style=\"background-color: #4CAF50; color: #fff; text-align: left; padding: 10px;\">Payment Method</th>\n" +
                "        <td style=\"padding: 10px;\">[At station]</td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "    <p style=\"margin-bottom: 10px;\">Dear ["+user.getFullName()+"],</p>\n" +
                "    <p style=\"margin-bottom: 10px;\">Thank you for booking our coach trip on ["+trip.getTimeStart()+"]. We are thrilled to have the opportunity to serve you and hope that your journey will be smooth and memorable.</p>\n" +
                "    <p style=\"margin-bottom: 10px;\">Please arrive at the departure point ["+trip.getProvinceStart()+"] at least 30 minutes before the scheduled departure time to ensure that you do not miss the coach.</p>\n" +
                "    <p style=\"margin-bottom: 10px;\">If you have any questions or special requests, please do not hesitate to contact us via phone at [0123456789] or email at [busservice@gmail.com]. We are committed to meeting your needs and providing the best possible service.</p>\n" +
                "    <p style=\"margin-bottom: 10px;\">Thank you for choosing our service. We wish you a wonderful trip!</p>\n" +
                "    <p style=\"margin-bottom: 10px;\">Best regards,</p>\n" +
                "    <p style=\"margin-bottom: 10px;\">[Bus service]</p>\n" +
                "  </body>\n" +
                "</html>";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("phongbuibsp3@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}
