package com.trilogyed.notequeueconsumer;

import com.trilogyed.notequeueconsumer.util.messages.NoteEntry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {
    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
    public void receiveMessage(NoteEntry msg) {
        System.out.println(msg.toString());
    }
}
