package com.ikolay.rabbitmq.consumer;


import com.ikolay.rabbitmq.model.MailModel;
import com.ikolay.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailConsumer {

    private final MailSenderService mailSenderService;

    @RabbitListener(queues = "${rabbitmq.mail-queue}")
    public void sendMail(MailModel model) throws MessagingException {
        log.info("model => {}",model);
        mailSenderService.sendMail(model);
    }
}
