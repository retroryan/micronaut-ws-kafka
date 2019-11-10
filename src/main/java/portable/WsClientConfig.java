package portable;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("portable")
public class WsClientConfig {

    private String ws_server_url_full;
    private String topic;

    public String getWs_server_url_full() {
        return ws_server_url_full;
    }

    public void setWs_server_url_full(String ws_server_url_full) {
        this.ws_server_url_full = ws_server_url_full;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
