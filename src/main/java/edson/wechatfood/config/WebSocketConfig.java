package edson.wechatfood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpoint;
import javax.xml.ws.WebEndpoint;

/**
 * websocket配置类
 */
@Component
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter WebServEndpoint(){
        return new ServerEndpointExporter();
    }
}
