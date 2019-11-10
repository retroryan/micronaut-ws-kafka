package portable;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.UUID;


@Controller()
public class Application implements ApplicationEventListener<ServerStartupEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private final WsClientConfig wsClientConfig;

    public Application(WsClientConfig wsClientConfig) {
        this.wsClientConfig = wsClientConfig;
    }

    @Inject
    private WsServer wsServer;


    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }

    @Get()
    public String index() {
        return "hello, world";
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {

    }

    @EventListener
    public void onStartup(ServerStartupEvent event) {
        LOG.info("Connecting with ws client config = " + wsClientConfig);
        LOG.info("bootstrap servers = " + wsClientConfig.getBootstrap_servers());
        LOG.info("topics = " + wsClientConfig.getTopic());

    }
}


