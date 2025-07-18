package org.sppd.otomatis.configuration;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class VirtualThreadConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatVirtualThreadExecutor() {
        return factory -> factory.addProtocolHandlerCustomizers(
                protocolHandler -> {
                    if (protocolHandler instanceof AbstractHttp11Protocol<?> http11Protocol) {
                        http11Protocol.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
                    }
                });
    }
}

