package com.example.demo112.responses;

import com.example.demo112.components.MailgunConfig;
import com.example.demo112.models.Order;
import com.example.demo112.models.OrderDetail;
import com.example.demo112.models.Product;
import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;
import org.springframework.beans.BeansException;

import java.util.List;


public class MailResponse {
    public ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
    public MailgunConfig mailgunConfig = context.getBean(MailgunConfig.class);

    public String API_KEY = mailgunConfig.getApiKey();
    public String DOMAIN = mailgunConfig.getDomain();

    public MessageResponse sendOrderConfirmation(Order order) {
        try {

            MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(API_KEY)
                    .createApi(MailgunMessagesApi.class);

            StringBuilder emailContent = new StringBuilder();
            emailContent.append("Thông tin đặt hàng:\n")
                    .append("- Họ và tên: ").append(order.getFullName()).append("\n")
                    .append("- Email: ").append(order.getEmail()).append("\n")
                    .append("- Số điện thoại: ").append(order.getPhoneNumber()).append("\n")
                    .append("- Địa chỉ: ").append(order.getAddress()).append("\n")
                    .append("- Ghi chú: ").append(order.getNote()).append("\n")
                    .append("- Trạng thái: ").append(order.getStatus()).append("\n")
                    .append("- Tổng tiền: ").append(order.getTotalMoney()).append("\n")
                    .append("- Phương thức vận chuyển: ").append(order.getShippingMethod()).append("\n")
                    .append("- Phương thức thanh toán: ").append(order.getPaymentMethod()).append("\n\n");

            emailContent.append("Chi tiết đơn hàng:\n");
            List<OrderDetail> cartItems = order.getOrderDetails();
            for (int i = 0; i < cartItems.size(); i++) {
                OrderDetail cartItem = cartItems.get(i);
                Product product = cartItem.getProduct();
                emailContent.append("- Sản phẩm ").append(i + 1).append(":\n")
                        .append("  - Tên sản phẩm: ").append(product.getName()).append("\n")
                        .append("  - Số lượng: ").append(cartItem.getNumberOfProducts()).append("\n")
                        .append("  - Giá: ").append(product.getPrice()).append("\n\n");
            }

            Message message = Message.builder()
                    .from("PhoneShop <USER@YOURDOMAIN.COM>")
                    .to(order.getEmail())
                    .subject("Xác nhận đơn hàng")
                    .text(emailContent.toString())
                    .build();

            return mailgunMessagesApi.sendMessage(DOMAIN, message);

        } catch (BeansException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MessageResponse sendStatusOrder(Order order) {
        try {
            MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(API_KEY)
                    .createApi(MailgunMessagesApi.class);
            StringBuilder emailStatus = new StringBuilder();
            emailStatus.append("Đơn hàng của bạn:\n")
                    .append("- ID đơn hàng: ").append(order.getId()).append("\n")
                    .append("- Trạng thái đơn hàng: ").append(order.getStatus()).append("\n")
                    .append("- Tổng Thanh Toán: ").append(order.getTotalMoney()).append("\n")
//                .append("- Shipping Status: ").append(order.getShippingStatus()).append("\n")
                    .append("- Other status information...").append("\n");
            Message message = Message.builder()
                    .from("PhoneShop <USER@YOURDOMAIN.COM>")
                    .to(order.getEmail())
                    .subject("Xác nhận đơn hàng")
                    .text(emailStatus.toString())
                    .build();

            return mailgunMessagesApi.sendMessage(DOMAIN, message);
        } catch (BeansException e) {
            e.printStackTrace();
        }


        return null;
    }
}