package com.loopus.loopus_be.service;

import com.loopus.loopus_be.service.IService.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String to, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Mã OTP của bạn - Loopus App");

            String htmlContent = buildOtpTemplate(otp);

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("Could not send OTP email", e);
        }
    }

    @Override
    public void sendOtpEmailForFailed(
            String to, String otp, String transactionId,
            String amount,
            String time,
            String orderCode,
            String paymentType,
            String description,
            String status
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Thanh toán thất bại - Loopus App");

            String htmlContent = buildPaymentFailedTemplate(
                    transactionId,
                    amount,
                    time,
                    orderCode,
                    paymentType,
                    description,
                    status
            );

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("Could not send OTP email", e);
        }
    }

    @Override
    public void sendOtpEmailForSuccess(
            String to, String otp, String transactionId,
            String amount,
            String time,
            String orderCode,
            String paymentType,
            String description,
            String status
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Thanh toán thành công - Loopus App");

            String htmlContent = buildPaymentSuccessTemplate(
                    transactionId,
                    amount,
                    time,
                    orderCode,
                    paymentType,
                    description,
                    status
            );

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("Could not send OTP email", e);
        }
    }

    private String buildOtpTemplate(String otp) {
        return """
                <html>
                  <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                    <div style="max-width: 600px; margin: auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 4px 10px rgba(0,0,0,0.1);">
                      <h2 style="text-align: center; color: #4CAF50;">Loopus Verification</h2>
                      <p style="font-size: 16px; color: #333;">Hello,</p>
                      <p style="font-size: 16px; color: #333;">Your One-Time Password (OTP) is:</p>
                      <div style="text-align: center; margin: 20px 0;">
                        <span style="font-size: 28px; letter-spacing: 5px; font-weight: bold; color: #4CAF50;">%s</span>
                      </div>
                      <p style="font-size: 14px; color: #555;">This code is valid for <b>5 minutes</b>. Please do not share it with anyone.</p>
                      <hr style="margin: 20px 0;"/>
                      <p style="font-size: 12px; color: #999; text-align: center;">© 2025 Loopus App - All rights reserved</p>
                    </div>
                  </body>
                </html>
                """.formatted(otp);
    }

    private String buildPaymentFailedTemplate(
            String transactionId,
            String amount,
            String time,
            String orderCode,
            String paymentType,
            String description,
            String status
    ) {
        return """
        <!DOCTYPE html>
        <html lang="vi">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Thanh toán thất bại</title>
            <style>
                @media only screen and (max-width: 600px) {
                    .container { width: 100%% !important; }
                    .content { padding: 20px !important; }
                    .icon-circle { width: 70px !important; height: 70px !important; }
                    .icon-circle svg { width: 36px !important; height: 36px !important; }
                    h2 { font-size: 22px !important; }
                    p { font-size: 14px !important; }
                    .detail-row { font-size: 13px !important; }
                }
            </style>
        </head>
        <body style="margin: 0; padding: 0; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Arial, sans-serif; background-color: #fef2f2; line-height: 1.6;">
            <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #fef2f2; padding: 20px 0;">
                <tr>
                    <td align="center" style="padding: 20px;">
                        <!-- Main container with email-client-friendly table layout -->
                        <table class="container" width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 15px; box-shadow: 0 6px 18px rgba(0,0,0,0.1); overflow: hidden;">
                            <!-- Header Section -->
                            <tr>
                                <td align="center" style="padding: 30px 20px;">
                                    <!-- Icon Circle -->
                                    <table cellpadding="0" cellspacing="0" style="margin: 0 auto;">
                                        <tr>
                                            <td align="center" style="width: 90px; height: 90px; background: linear-gradient(135deg, #fee2e2 0%%, #fecaca 100%%); border-radius: 50%%; display: flex; align-items: center; justify-content: center;">
                                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="#dc2626" width="48" height="48" style="display: block;">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                                                </svg>
                                            </td>
                                        </tr>
                                    </table>
                                    <!-- Title -->
                                    <h2 style="color: #dc2626; font-size: 26px; margin: 15px 0 10px 0; font-weight: 600;">Thanh toán thất bại!</h2>
                                    <p style="color: #666666; font-size: 16px; margin: 0; max-width: 500px;">Rất tiếc, giao dịch của bạn không thể thực hiện thành công. Vui lòng thử lại hoặc kiểm tra phương thức thanh toán của bạn.</p>
                                </td>
                            </tr>
                            <!-- Details Section -->
                            <tr>
                                <td style="padding: 0 20px;">
                                    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #fef9f9; border: 1px solid #fca5a5; border-radius: 10px; padding: 20px;">
                                        <!-- Using table rows for better email client compatibility -->
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Mã giao dịch:</strong> <span style="color: #666666;">%s</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Số tiền:</strong> <span style="color: #dc2626; font-weight: bold;">%s VND</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Thời gian:</strong> <span style="color: #666666;">%s</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Mã đơn hàng:</strong> <span style="color: #666666;">%s</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Loại thanh toán:</strong> <span style="color: #666666;">%s</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Mô tả:</strong> <span style="color: #666666;">%s</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Trạng thái:</strong> <span style="background-color: #fee2e2; color: #dc2626; padding: 3px 8px; border-radius: 5px; display: inline-block;">%s</span>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <!-- Info Box -->
                            <tr>
                                <td style="padding: 0 20px 20px 20px;">
                                    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #fff1f2; border: 1px solid #fecdd3; border-radius: 8px; padding: 15px;">
                                        <tr>
                                            <td style="font-size: 14px; color: #666666; line-height: 1.6;">
                                                Nếu số tiền đã bị trừ, hệ thống sẽ hoàn lại trong vòng <strong>1–3 ngày làm việc</strong>. Mọi thắc mắc xin vui lòng liên hệ bộ phận hỗ trợ khách hàng.
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <!-- Footer -->
                            <tr>
                                <td style="padding: 20px; border-top: 1px solid #fca5a5; text-align: center;">
                                    <p style="font-size: 12px; color: #999999; margin: 0;">© 2025 Loopus App - All rights reserved</p>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """.formatted(transactionId, amount, time, orderCode, paymentType, description, status);
    }

    private String buildPaymentSuccessTemplate(
            String transactionId,
            String amount,
            String time,
            String orderCode,
            String paymentType,
            String description,
            String status
    ) {
        return """
        <!DOCTYPE html>
        <html lang="vi">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Thanh toán thành công</title>
            <style>
                @media only screen and (max-width: 600px) {
                    .container { width: 100%% !important; }
                    .content { padding: 20px !important; }
                    .icon-circle { width: 70px !important; height: 70px !important; }
                    .icon-circle svg { width: 36px !important; height: 36px !important; }
                    h2 { font-size: 22px !important; }
                    p { font-size: 14px !important; }
                    .detail-row { font-size: 13px !important; }
                }
            </style>
        </head>
        <body style="margin: 0; padding: 0; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Arial, sans-serif; background-color: #f6f8fb; line-height: 1.6;">
            <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f6f8fb; padding: 20px 0;">
                <tr>
                    <td align="center" style="padding: 20px;">
                        <!-- Main container with email-client-friendly table layout -->
                        <table class="container" width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 16px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); overflow: hidden;">
                            <!-- Header Section -->
                            <tr>
                                <td align="center" style="padding: 40px 20px;">
                                    <!-- Icon Circle -->
                                    <table cellpadding="0" cellspacing="0" style="margin: 0 auto;">
                                        <tr>
                                            <td align="center" class="icon-circle" style="width: 90px; height: 90px; background-color: #dcfce7; border-radius: 50%%; display: flex; align-items: center; justify-content: center;">
                                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="#16a34a" style="width: 48px; height: 48px; display: block;">
                                                    <path stroke-linecap="round" stroke-linejoin="round" d="M4.5 12.75l6 6 9-13.5" />
                                                </svg>
                                            </td>
                                        </tr>
                                    </table>
                                    <!-- Title -->
                                    <h2 style="color: #111111; font-size: 26px; margin: 20px 0 10px 0; font-weight: 600;">Thanh toán thành công!</h2>
                                    <p style="color: #555555; font-size: 15px; margin: 0 0 25px 0; max-width: 500px; line-height: 1.6;">
                                        Cảm ơn bạn đã tin tưởng sử dụng dịch vụ của chúng tôi.<br/>
                                        Giao dịch của bạn đã được xử lý thành công.
                                    </p>
                                </td>
                            </tr>
                            <!-- Details Section -->
                            <tr>
                                <td style="padding: 0 20px;">
                                    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #fafbfc; border: 1px solid #e5e7eb; border-radius: 10px; padding: 24px;">
                                        <!-- Using table rows for better email client compatibility -->
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Mã giao dịch:</strong> <span style="color: #666666;">%s</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Số tiền:</strong> <span style="color: #16a34a; font-weight: bold;">%s</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Thời gian:</strong> <span style="color: #666666;">%s</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Mã đơn hàng:</strong> <span style="color: #666666;">%s</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Loại thanh toán:</strong> <span style="color: #666666;">%s</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Mô tả:</strong> <span style="color: #666666;">%s</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 8px 0; font-size: 15px; color: #333333;">
                                                <strong>Trạng thái:</strong> <span style="background-color: #f0fdf4; color: #16a34a; font-weight: bold; padding: 3px 10px; border-radius: 6px; display: inline-block;">%s</span>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <!-- Footer -->
                            <tr>
                                <td style="padding: 30px 20px; text-align: center;">
                                    <p style="font-size: 13px; color: #999999; margin: 0;">© 2025 Loopus App - Thanh toán an toàn & bảo mật</p>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """.formatted(transactionId, amount, time, orderCode, paymentType, description, status);
    }
}
