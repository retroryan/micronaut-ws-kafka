package portable;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("portable")
public class WsClientConfig {

    private String ws_server_url;

    public String getWs_server_url() {
        return ws_server_url;
    }

    public void setWs_server_url(String ws_server_url) {
        this.ws_server_url = ws_server_url;
    }
}
