package usyd.mingyi.springcloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SimpleGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String requestPath = exchange.getRequest().getPath().toString();

        // 如果请求路径是以 /uaa/ 开头的，直接放行
        if (requestPath.startsWith("/oauth/token")||requestPath.startsWith("/socket.io")) {
            return chain.filter(exchange);
        }

        HttpHeaders headers = exchange.getRequest().getHeaders();

        // 从请求头中获取 "Authorization" 字段的值
        String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // 提取 Bearer Token
            String bearerToken = authorizationHeader.substring(7); // 去掉 "Bearer " 前缀

            // 验证 JWT Token 合法性
            boolean isValidToken = JwtUtils.validateJwt(bearerToken);

            if (isValidToken) {
                String username = JwtUtils.extractUserName(bearerToken);
                String userId = JwtUtils.extractUserId(bearerToken);
                // 将用户信息添加到请求头
                exchange = exchange.mutate()
                        .request(exchange.getRequest()
                                .mutate()
                                .header("X-Username", username)
                                .header("X-UserId", userId)
                                .build())
                        .build();
                // Token 合法，继续请求链
                return chain.filter(exchange);
            } else {
                // Token 不合法，返回 401 错误
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } else {
            // 没有 Authorization 头，返回 401 错误
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
