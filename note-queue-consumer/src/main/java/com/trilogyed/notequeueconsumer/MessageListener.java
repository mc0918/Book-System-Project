package com.trilogyed.notequeueconsumer;

import com.trilogyed.notequeueconsumer.util.feign.NoteClient;
import com.trilogyed.notequeueconsumer.util.messages.NoteEntry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MessageListener {

    @Autowired
    private NoteClient noteClient; //NoteClient interface's methods are never defined so how does it know what to do?

    public MessageListener(NoteClient noteClient) {
        this.noteClient = noteClient;
    }

    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
    public void receiveMessage(NoteEntry msg) {
        System.out.println(msg.toString());

        boolean isNewNote = true;

        List<NoteEntry> notes = noteClient.getAllNotes();
        for (NoteEntry note : notes) {
            if (msg.getNote_id() == note.getNote_id()) {
                isNewNote = false;
                break;
            }
        }

        if (isNewNote) {
            noteClient.saveNoteToDb(msg);
            System.out.println(msg + " saved to database!");
        } else {
            noteClient.updateNoteinDb(msg);
            System.out.println("note with id " + msg.getNote_id() + " updated");
            isNewNote = true;
        }
    }


//        noteClient.saveNoteToDb(msg); //will this work???
//        System.out.println("But, like, basically, I am " + msg.toString());

}
