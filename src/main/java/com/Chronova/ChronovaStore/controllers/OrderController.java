package com.Chronova.ChronovaStore.controllers;

import com.Chronova.ChronovaStore.dataDTO.OrderRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.UserRequestDTO;
import com.Chronova.ChronovaStore.models.Order;
import com.Chronova.ChronovaStore.models.OrderLign;
import com.Chronova.ChronovaStore.models.User;
import com.Chronova.ChronovaStore.services.CartService;
import com.Chronova.ChronovaStore.services.EmailService;
import com.Chronova.ChronovaStore.services.UserService;
import jakarta.mail.MessagingException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    private final UserService userService;
    private final CartService cartService;
    private final EmailService emailService;

    public OrderController(UserService userService, CartService cartService, EmailService emailService) {
        this.userService = userService;
        this.cartService = cartService;
        this.emailService = emailService;
    }

    @PreAuthorize("hasRole('ADMIN') or #user_id == authentication.principal.id")
    @GetMapping("/order/confirm/{user_id}")
    public OrderRequestDTO ConfirmCart(@PathVariable("user_id") Integer user_id) {
        Order order = cartService.confirmCart(user_id);
        OrderRequestDTO orderDTO = cartService.orderTOOrderRequestDTO(order);

        // 🚨 Clear cart after confirming order
        cartService.clearCart(user_id);

        UserRequestDTO user = userService.getUserById(user_id);
        if (user.email() != null && !user.email().isEmpty()) {
            try {
                String receiptHtml = buildReceiptEmailHtml(order, user);
                emailService.sendHtmlEmail(user.email(), "Your Chronovia Store Order Receipt", receiptHtml);
            } catch (MessagingException e) {
                System.err.println("Failed to send order receipt email: " + e.getMessage());
            }
        }

        return orderDTO;
    }

    private String buildReceiptEmailHtml(Order order, UserRequestDTO user) {
        // Generate order items table
        String itemsHtml = order.getOrderLigns().stream()
                .map(line -> String.format(
                        "<tr>" +
                        "<td style=\"padding: 15px; border-bottom: 1px solid #eaeaea; vertical-align: top;\">" +
                        "<div style=\"font-weight: 600;\">%s %s</div>" +
                        "<div style=\"color: #666; font-size: 14px;\">Ref: %s</div>" +
                        "</td>" +
                        "<td style=\"padding: 15px; border-bottom: 1px solid #eaeaea; text-align: center;\">%d</td>" +
                        "<td style=\"padding: 15px; border-bottom: 1px solid #eaeaea; text-align: right;\">$%.2f</td>" +
                        "<td style=\"padding: 15px; border-bottom: 1px solid #eaeaea; text-align: right;\">$%.2f</td>" +
                        "</tr>",
                        line.getWatch().getBrandName(),
                        line.getWatch().getModelName(),
                        line.getWatch().getReferenceNumber(),
                        line.getQuantity(),
                        line.getWatch().getPrice(),
                        line.getLineTotal()))
                .collect(Collectors.joining());

        return String.format(
                "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: 'Helvetica Neue', Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f9f9f9; margin: 0; padding: 0; }" +
                ".container { max-width: 600px; margin: 30px auto; background: white; border-radius: 8px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05); overflow: hidden; }" +
                ".header { background-color: #1a1a1a; padding: 30px; text-align: center; color: white; }" +
                ".header h1 { margin: 0; font-size: 24px; letter-spacing: 1px; }" +
                ".content { padding: 30px; }" +
                ".section { margin-bottom: 25px; }" +
                ".section-title { font-size: 18px; color: #1a1a1a; border-bottom: 2px solid #f0f0f0; padding-bottom: 8px; margin-bottom: 15px; }" +
                "table { width: 100%%; border-collapse: collapse; }" +
                "th { text-align: left; padding: 12px 15px; background-color: #f5f5f5; font-weight: 600; color: #444; }" +
                ".total-row { font-weight: bold; font-size: 16px; }" +
                ".footer { text-align: center; padding: 20px; background-color: #f5f5f5; color: #666; font-size: 14px; }" +
                ".user-info { display: flex; justify-content: space-between; margin-bottom: 20px; }" +
                ".info-box { flex: 1; padding: 15px; background-color: #f9f9f9; border-radius: 6px; margin-right: 10px; }" +
                ".info-box:last-child { margin-right: 0; }" +
                ".info-box h3 { margin-top: 0; color: #1a1a1a; font-size: 16px; }" +
                ".info-box p { margin: 5px 0; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"container\">" +
                "<div class=\"header\">" +
                "<h1>CHRONOVA STORE</h1>" +
                "</div>" +
                "<div class=\"content\">" +
                "<div class=\"section\">" +
                "<h2 style=\"margin-top: 0; color: #1a1a1a;\">Order Confirmation</h2>" +
                "<p>Thank you for your purchase, %s!</p>" +
                "</div>" +
                "<div class=\"user-info\">" +
                "<div class=\"info-box\">" +
                "<h3>Order Details</h3>" +
                "<p><strong>Order ID:</strong> %s</p>" +
                "<p><strong>Date:</strong> %s</p>" +
                "<p><strong>Status:</strong> Processed</p>" +
                "</div>" +
                "<div class=\"info-box\">" +
                "<h3>Customer Information</h3>" +
                "<p><strong>Name:</strong> %s</p>" +
                "<p><strong>Email:</strong> %s</p>" +
                "<p><strong>Member Since:</strong> %s</p>" +
                "</div>" +
                "</div>" +
                "<div class=\"section\">" +
                "<div class=\"section-title\">Order Summary</div>" +
                "<table>" +
                "<thead>" +
                "<tr>" +
                "<th style=\"width: 40%%;\">Item</th>" +
                "<th style=\"width: 15%%; text-align: center;\">Qty</th>" +
                "<th style=\"width: 20%%; text-align: right;\">Unit Price</th>" +
                "<th style=\"width: 25%%; text-align: right;\">Total</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                "%s" +  // itemsHtml will be inserted here
                "</tbody>" +
                "<tfoot>" +
                "<tr class=\"total-row\">" +
                "<td colspan=\"3\" style=\"text-align: right; padding: 15px; border-top: 2px solid #eaeaea;\">Subtotal</td>" +
                "<td style=\"text-align: right; padding: 15px; border-top: 2px solid #eaeaea;\">$%.2f</td>" +
                "</tr>" +
                "<tr>" +
                "<td colspan=\"3\" style=\"text-align: right; padding: 5px 15px;\">Shipping</td>" +
                "<td style=\"text-align: right; padding: 5px 15px;\">$0.00</td>" +
                "</tr>" +
                "<tr class=\"total-row\">" +
                "<td colspan=\"3\" style=\"text-align: right; padding: 15px; border-top: 2px solid #eaeaea;\">Total</td>" +
                "<td style=\"text-align: right; padding: 15px; border-top: 2px solid #eaeaea;\">$%.2f</td>" +
                "</tr>" +
                "</tfoot>" +
                "</table>" +
                "</div>" +
                "<div class=\"section\">" +
                "<div class=\"section-title\">Shipping Information</div>" +
                "<p>Your order will be shipped within 1-2 business days. You will receive a tracking number once your package is on its way.</p>" +
                "</div>" +
                "</div>" +
                "<div class=\"footer\">" +
                "<p>&copy; 2023 Chronova Store. All rights reserved.</p>" +
                "<p>If you have any questions, please contact us at support@chronovastore.com</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>",
                user.username(),
                order.getOrderId(),
                order.getOrderDate().toString(),
                user.username(),
                user.email(),
                user.createdAt().toString(),
                itemsHtml,
                order.getOrderTotal(),
                order.getOrderTotal()
        );
    }
}
