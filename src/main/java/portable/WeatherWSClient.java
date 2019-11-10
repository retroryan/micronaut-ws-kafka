package portable;

import io.micronaut.http.HttpRequest;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.ClientWebSocket;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.UUID;

@ClientWebSocket
public class WeatherWSClient implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private WebSocketSession session;
    private HttpRequest request;


    @Inject
    KafkaProducerClient kafkaProducerClient;

    @OnOpen
    public void onOpen(WebSocketSession session, HttpRequest request) {
        this.session = session;
        this.request = request;
    }


    public WebSocketSession getSession() {
        return session;
    }

    public HttpRequest getRequest() {
        return request;
    }

    @OnMessage
    public void onMessage(
            String message) {
        LOG.info("sending to kafka msg =>" + message);

        kafkaProducerClient.sendProduct(UUID.randomUUID().toString(), message);
    }

    @Override
    public void close() throws Exception {
        System.out.println("WeatherWSClient.close");
    }
}
