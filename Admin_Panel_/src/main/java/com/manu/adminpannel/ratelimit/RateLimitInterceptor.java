package com.manu.adminpannel.ratelimit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final Map<String, Long> requestTimes = new ConcurrentHashMap<>();
    private final long RATE_LIMIT_INTERVAL = 500; 

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
    	String ip = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        Long lastRequestTime = requestTimes.get(ip);
        if (lastRequestTime != null && (currentTime - lastRequestTime) < RATE_LIMIT_INTERVAL) {
            response.setStatus(429);
            response.getWriter().write("Too Many Requests - Try again later.");
            return false;
        }

        requestTimes.put(ip, currentTime);
        
        return true;
        
    }
}
