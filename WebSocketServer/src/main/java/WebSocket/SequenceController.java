package WebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Controller
public class SequenceController {

    public static final long FIXED_RATE = 10000;

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    TaskScheduler taskScheduler;
    private boolean schedulerActive = false;

    ScheduledFuture<?> scheduledFuture;

    @MessageMapping("/set-sequences-length")
    public void setLength(SequenceLengthMessage message) throws Exception {
        SequencesHolder sequencesHolders = SequencesHolder.getInstance();
        sequencesHolders.setLength(message.getLength());
    }

    @MessageMapping("/request-sequences")
    @SendTo("/topic/sequences")
    public List<List<Integer>> notifyClient() throws Exception {
        return getNotifyMessage();
    }

    @MessageMapping("/switch-auto-generation")
    public void switchAutoGenerator() {
        if (this.schedulerActive) {
            scheduledFuture.cancel(false);
        } else {
            scheduledFuture = taskScheduler.scheduleAtFixedRate(autoNotifyClient(), FIXED_RATE);
        }
        this.schedulerActive = !schedulerActive;
    }

    private Runnable autoNotifyClient() {
        return () -> template.convertAndSend("/topic/sequences", getNotifyMessage());
    }

    private List<List<Integer>> getNotifyMessage() {
        SequencesHolder sequencesHolder = SequencesHolder.getInstance();
        sequencesHolder.generateSequences();
        return sequencesHolder.getSequences();
    }
}