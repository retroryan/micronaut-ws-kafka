package portable;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@KafkaListener(groupId = "portable_weather")
public class KafkaClientListener {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaClientListener.class);

    @Inject
    private WsServer wsServer;

    @Topic("${portable.topic}")
    public void receive(String message) {
        LOG.info("kafka msg => " + message);
        wsServer.broadcastMessage(message);
    }
}
