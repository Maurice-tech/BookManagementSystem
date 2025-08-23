package com.booksystem.bookManagement.utils.events.listeners;
import com.booksystem.bookManagement.security.JWTGenerator;
import com.booksystem.bookManagement.service.EmailSenderService;
import com.booksystem.bookManagement.utils.SecurityConstants;
import com.booksystem.bookManagement.utils.events.event.ForgotPasswordEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;


@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class ForgotPasswordEventListener implements ApplicationListener<ForgotPasswordEvent> {
    private final EmailSenderService emailSenderService;
    private final JWTGenerator tokenProvider;
    @Value("${app.url}")
    private String appUrl;

    @Override
    public void onApplicationEvent(ForgotPasswordEvent event) {
        // Create a verification token for the user
        String verificationToken = tokenProvider.generateSignUpVerificationToken(event.getEmail(), SecurityConstants.JWT_REFRESH_TOKEN_EXPIRATION);

        // Build the verification url to be sent to the user
        String url = appUrl + "/reset-password?token=" + verificationToken + "&email=" + event.getEmail();

        // Send the email to the user
        emailSenderService.sendForgotPasswordEmailVerification(url, event);

        log.info("Click the link to verify your email and change ur password : {}", url);
    }
}
