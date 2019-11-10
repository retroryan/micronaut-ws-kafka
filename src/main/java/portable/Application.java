package portable;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import io.micronaut.websocket.RxWebSocketClient;
import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;


@Controller()
public class Application implements ApplicationEventListener<ServerStartupEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private final WsClientConfig wsClientConfig;

    public Application(WsClientConfig wsClientConfig) {
        this.wsClientConfig = wsClientConfig;
    }

    @Inject
    @Client("${portable.ws_server_url}")
    private RxWebSocketClient webSocketClient;


    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {

    }

    @EventListener
    public void onStartup(ServerStartupEvent event) {
        // The connection excpets the URL to be in the form of:
        // ws://bikeweather-to-ws.default.35.224.5.101.nip.io/ws
        String ws_server_url_full = "ws://" + wsClientConfig.getWs_server_url() + "/ws";

        LOG.info("Connecting to ws client = " + ws_server_url_full);
        Flowable<WeatherWSClient> connect = webSocketClient.connect(WeatherWSClient.class, ws_server_url_full);
        connect.blockingForEach(weatherWSClient -> LOG.info("Connected to weatherWSClient => " + weatherWSClient.getSession().getId()));
    }
}


