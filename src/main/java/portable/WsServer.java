package portable;


import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

@ServerWebSocket("/ws")
public class WsServer {

    private static final Logger LOG = LoggerFactory.getLogger(WsServer.class);

    private WebSocketBroadcaster broadcaster;

    public WsServer(WebSocketBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @OnOpen
    public void onOpen(WebSocketSession session) {
        LOG.info("Opened new WS session => " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, WebSocketSession session) {
        LOG.info("onMessage => " + message + " from session: => " + session.getId());
    }

    @OnClose
    public void onClose(
            WebSocketSession session) {
        LOG.info("close WS session => " + session.getId());
    }

    public void broadcastMessage(String message) {
        Publisher<String> broadcast = broadcaster.broadcast(message);
        broadcast.subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription subscription) {
            }
            @Override
            public void onNext(String s) {
            }
            @Override
            public void onError(Throwable throwable) {
                System.out.println("throwable = " + throwable);
            }
            @Override
            public void onComplete() {
                System.out.println("broadcast = " + broadcast);
            }
        });
    }
}
