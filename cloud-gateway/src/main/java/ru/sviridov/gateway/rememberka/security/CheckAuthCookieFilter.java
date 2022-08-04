package ru.sviridov.gateway.rememberka.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class CheckAuthCookieFilter implements WebFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        for (var cook:exchange.getRequest().getCookies().toSingleValueMap().entrySet()) {
            if(cook.getValue().getName().equals("access_token")) {
                exchange.mutate().request(
                        exchange.getRequest().mutate()
                                .header(HttpHeaders.AUTHORIZATION,"Bearer " + cook.getValue().getValue())
                                .build()
                );
                logger.debug("Authorization attempt via Cookie" + exchange.getRequest().getId());
            }
            return chain.filter(exchange);
        }

        if (exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            logger.debug("Authorization attempt via Headers" + exchange.getRequest().getId());
        }
        return chain.filter(exchange);
    }
}
