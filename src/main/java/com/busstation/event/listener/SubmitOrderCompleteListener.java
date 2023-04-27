package com.busstation.event.listener;

import com.busstation.entities.Order;
import com.busstation.entities.OrderDetail;
import com.busstation.entities.Ticket;
import com.busstation.entities.User;
import com.busstation.event.SubmitOrderCompleteEvent;
import com.busstation.repositories.OrderDetailRepository;
import com.busstation.repositories.TicketRepository;
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
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private final TicketRepository ticketRepository;
    private final OrderDetailRepository orderDetailRepository;
    @Override
    public void onApplicationEvent(SubmitOrderCompleteEvent event) {
        theUser = event.getUser();
        theOrder = event.getOrder();
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderID(theOrder.getOrderID());
        ticket = orderDetails.get(0).getTicket();
        for (OrderDetail orderDetail: orderDetails) {
            BigDecimal ticketPrice = orderDetail.getTicket().getPrice();
            totalPrice = totalPrice.add(ticketPrice);
        }

        try {
            sendOderEmail();
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Nhận event thành công");
    }
    public void sendOderEmail() throws MessagingException, UnsupportedEncodingException {
        String subject = "[Bus Station] Thank you for ordering transportation at our Website, Order number: "+ theOrder.getOrderID() ;
        String senderName = "Do not reply this email";
        String mailContent = "<html>" +
                "<head>" +
                "<style>" +
                "table { " +
                "  border-collapse: collapse; " +
                "  width: 100%; " +
                "} " +
                "th, td { " +
                "  text-align: left; " +
                "  padding: 8px; " +
                "} " +
                "th { " +
                "  background-color: #4CAF50; " +
                "  color: white; " +
                "} " +
                "</style>" +
                "</head>" +
                "<body>" +
                "<table>" +
                "<tr>" +
                "<th>Order ID</th>" +
                "<th>ADDRESS START</th>" +
                "<th>ADDRESS AND</th>" +
                "<th>Name User</th>" +
                "<th>Create Date</th>" +
                "<th>Ticket ID</th>" +
                "<th>price</th>" +
                "</tr>" +
                "<tr>" +
                "<td>" + theOrder.getOrderID() + "</td>" +
                "<td>" + ticket.getAddressStart()+ "</td>" +
                "<td>" + ticket.getAddressEnd()+ "</td>" +
                "<td>" + theUser.getFullName() + "</td>" +
                "<td>" + theOrder.getCreateAt() + "</td>" +
                "<td>" + ticket.getTicketId()+ "</td>" +
                "<td>" +totalPrice+ "</td>" +
                "</tr>" +
                "</table>" +
                "</body>" +
                "</html>";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("khanhvo270423@gmail.com", senderName);
        //messageHelper.setTo("vok67667@gmail.com");
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}
