package com.uday.oauthsecuritysystem.config;

import io.github.bucket4j.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
public class RateLimitFilter
        implements Filter {

    private final Bucket bucket;

    public RateLimitFilter() {

        Bandwidth limit =
                Bandwidth.classic(
                        100,
                        Refill.greedy(
                                100,
                                Duration.ofMinutes(1)
                        )
                );

        bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException,
            ServletException {

        if(bucket.tryConsume(1)) {

            chain.doFilter(
                    request,
                    response);

        } else {

            HttpServletResponse resp =
                    (HttpServletResponse)
                            response;

            resp.setStatus(429);

            resp.getWriter().write(
                    "Too Many Requests");
        }
    }
}