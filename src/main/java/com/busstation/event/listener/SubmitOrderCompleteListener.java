package com.busstation.event.listener;

import com.busstation.entities.*;
import com.busstation.event.SubmitOrderCompleteEvent;
import com.busstation.exception.DataNotFoundException;
import com.busstation.repositories.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class SubmitOrderCompleteListener implements ApplicationListener<SubmitOrderCompleteEvent> {
    private final JavaMailSender javaMailSender;
    private User theUser;
    private Order theOrder;
    private OrderDetail orderDetail;
    private Ticket ticket;
    private Trip trip;

    private Car car;
    private User employee;

    private String htmlString;

    private BigDecimal totalPrice = BigDecimal.ZERO;
    private final TicketRepository ticketRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final TripRepository tripRepository;
    private final TripUserRepository tripUserRepository;
    private final OrderRepository orderRepository;
    @Override
    public void onApplicationEvent(SubmitOrderCompleteEvent event) {
        theUser = event.getUser();
        theOrder = event.getOrder();
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderID(theOrder.getOrderID());
        ticket = orderDetails.get(0).getTicket();
        for (OrderDetail orderDetail: orderDetails) {
            BigDecimal ticketPrice = orderDetail.getTicket().getPrice();
            totalPrice = totalPrice.add(ticketPrice);
            System.out.println(totalPrice);
        }
        trip = tripRepository.findById(theOrder.getTrip().getTripId()).orElse(null);

        car = orderRepository.findByOrderIdAndUserId(theOrder.getOrderID(), theUser.getUserId());

        employee = tripUserRepository.findEmployeeByOderAndCarAndUser(theOrder.getOrderID()).orElseThrow(() -> new DataNotFoundException("Driver is null"));

        StringBuilder sb = new StringBuilder();

        for (OrderDetail orderDetail : orderDetails) {
            sb.append(orderDetail.getChair().getChairNumber()).append("/");
        }

         htmlString = sb.toString();

        try {
            sendOderEmail();
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        totalPrice = BigDecimal.ZERO;
    }
    public void sendOderEmail() throws MessagingException, UnsupportedEncodingException {
        String subject = "[Bus Station] Thank you for ordering transportation at our Website, Order number: "+ theOrder.getOrderID() ;
        String senderName = "Do not reply this email";
        String mailContent =  "<html>" +
                "<style>" +
                " .container {\n" +
                "            display: flex;\n" +
                "            flex-direction: column;\n" +
                "        }\n" +
                "\n" +
                "        .box {\n" +
                "            border: 2px solid red;\n" +
                "            width: 100%;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .box-down {\n" +
                "            width: 100%;\n" +
                "            margin: 5px;\n" +
                "            border-bottom: 2px dotted #666363;\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            text-align: center;\n" +
                "            font-size: 25px;\n" +
                "            color: blue;\n" +
                "        }\n" +
                "\n" +
                "        .box-content {\n" +
                "            display: block;\n" +
                "            padding: 20px;\n" +
                "            background-color: #FF9966;\n" +
                "            width: 90%;\n" +
                "        }\n" +
                "\n" +
                "        .box-content p {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .box-detail-service {\n" +
                "            margin-top: 10px;\n" +
                "            border: 0.5px solid rgb(144, 98, 230);\n" +
                "            width: 100%;\n" +
                "            display: flex;\n" +
                "            margin-bottom: 10px;\n" +
                "            flex-direction: column;\n" +
                "        }\n" +
                "\n" +
                "        .box-detail-service p {\n" +
                "            text-align: end;\n" +
                "            color: #d6ee00;\n" +
                "            font-size: 30px;\n" +
                "            font-weight: bold;\n" +
                "            padding-right: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .box-detail-service .table {\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .table .table-title {\n" +
                "            padding-right: 300px;\n" +
                "            padding-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .table .table-content {\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        \n" +
                "        .table .note .table-title {\n" +
                "            padding-right: 300px;\n" +
                "            padding-bottom: 20px;\n" +
                "            font-size: 18px;\n" +
                "            color: blue;\n" +
                "        }\n" +
                "\n" +
                "        .table .note .table-content{\n" +
                "            font-weight: bold;\n" +
                "            font-size: 20px;\n" +
                "            color: #c0f10b;\n" +
                "        }\n"
                 +
                ".box-detail-service p {text-align: end; color: #d6ee00; font-size: 30px; font-weight: bold; padding-right: 20px;}" +
                ".box-detail-service .table {padding: 20px;}" +
                ".table .table-title {padding-right: 300px; padding-bottom: 20px;}" +
                ".table .table-content {font-weight: bold;}" +
                ".table .note .table-title {padding-right: 300px; padding-bottom: 20px; font-size: 20; color: blue;}" +
                ".table .note .table-content {font-weight: bold; font-size: 20px; color: #c0f10b;}" +
                "</style>"+
                "<body>" +
                " <div class=\"box\">\n" +
                "                <p class=\"box-content\">\n" +
                "                   Please do not respond to this email address as we will not receive it. To send " +
                "                   feedback, please email cs@bustation.com. busStation only receives and processes requests from your " +
                "                   calls and emails during business hours. Thank you!\n" +
                "                </p>\n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"box-down\">\n" +
                "                <h1>\n" +
                "                    Thank you customer: "+theUser.getFullName()+"!\n" +
                "                </h1>\n" +
                "                <p>Thank you for using busStation's services</p>\n" +
                "            </div>\n" +
                "               <div class=\"box-down\">\n" +
                "                <h1>\n" +
                "                    Payment Guide!\n" +
                "                </h1>\n" +
                "                <p>1. Please pay the amount of "+totalPrice+"VND for the service " +
                "                      code "+theOrder.getOrderID()+" to the driver when boarding." +
                "                </p>\n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"box-detail-service\">\n" +
                "                <div>\n" +
                "                    <h1>Information Services</h1>\n" +
                "                </div>\n" +
                "                <div>\n" +
                "                    <p>ORDER ID: "+theOrder.getOrderID()+"</p>\n" +
                "                </div>\n" +
                "                <div class=\"table\">\n" +
                "                    <table>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Ticket ID:</td>\n" +
                "                            <td class=\"table-content\">"+ticket.getTicketId()+"</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Address start:</td>\n" +
                "                            <td class=\"table-content\"> "+ticket.getAddressStart()+"</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Time start:</td>\n" +
                "                            <td class=\"table-content\">"+trip.getTimeStart()+"</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Address end:</td>\n" +
                "                            <td class=\"table-content\"> "+ticket.getAddressEnd()+" " +
                "                                </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Chair number:</td>" +
                "                            <td class=\"table-content\"> "+htmlString+" " +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Trip:</td>\n" +
                "                            <td class=\"table-content\">"+trip.getProvinceStart()+ " - "+trip.getProvinceEnd()+ " ("+trip.getTimeStart()+") </td>\n" +
                "                        </tr>\n" +
                "                        <tr class=\"note\">\n" +
                "                            <td class=\"table-title\">Total price:</td>\n" +
                "                            <td class=\"table-content\">"+totalPrice+" VND</td>\n" +
                "                        </tr>\n" +
                "                </table>\n" +
                "                </div>    \n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"box-detail-service\">\n" +
                "                <div>\n" +
                "                    <h1>Contact Information</h1>\n" +
                "                </div>\n" +
                "                <div>\n" +
                "                </div>\n" +
                "                <div class=\"table\">\n" +
                "                    <table>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Car number:</td>\n" +
                "                            <td class=\"table-content\">"+car.getCarNumber()+"</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Driver's phone number:</td>\n" +
                "                            <td class=\"table-content\">"+employee.getPhoneNumber()+"</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Office phone number:</td>\n" +
                "                            <td class=\"table-content\">(0262) 2.48.48.48 </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Support Hotline phone number:</td>\n" +
                "                            <td class=\"table-content\">(0262) 2.48.48.48 </td>\n" +
                "                        </tr>\n" +
                "                </table>\n" +
                "                </div>    \n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"box-detail-service\">\n" +
                "                <div>\n" +
                "                    <h1>Customer information</h1>\n" +
                "                </div>\n" +
                "                <div>\n" +
                "                    <p>USER ID: "+theUser.getUserId()+"</p>\n" +
                "                </div>\n" +
                "                <div class=\"table\">\n" +
                "                    <table>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Full name:</td>\n" +
                "                            <td class=\"table-content\">"+theUser.getFullName()+"</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Phone number:</td>\n" +
                "                            <td class=\"table-content\">"+theUser.getPhoneNumber()+"</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Payments:</td>\n" +
                "                            <td class=\"table-content\">At the station</td>\n" +
                "                        </tr>\n" +
                "                </table>\n" +
                "                </div>    \n" +
                "            </div>\n" +
                "            <div class=\"box-down\">\n" +
                "                <h1>\n" +
                "                   Thank you customer!\n" +
                "                </h1>\n" +
                "                <p>BusStation would like to thank you and look forward to serving all your ticketing " +
                "                    needs in the future. Please read BusStaion's regulations carefully here. In case you" +
                "                    need direct support on ticket cancellation, " +
                "                    ticket change, flight cancellation,... please contact our customer service:\n" +
                "                </p>\n" +
                "            </div>\n" +
                "        </div>" +
                "</body>"+
                "</html>";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("phongbuibsp3@gmail.com", senderName);
//        messageHelper.setTo("phongbuibsp3@gmail.com");
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}
