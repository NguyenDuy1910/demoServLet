package com.example.demo112.utils;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {
    public static HttpServletRequest getCurrentRequest() {
        return (HttpServletRequest) ThreadLocalHolder.getRequest();
    }

    private static class ThreadLocalHolder {
        private static final ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<>();

        public static void setRequest(HttpServletRequest request) {
            REQUEST_THREAD_LOCAL.set(request);
        }

        public static HttpServletRequest getRequest() {
            return REQUEST_THREAD_LOCAL.get();
        }

        public static void clearRequest() {
            REQUEST_THREAD_LOCAL.remove();
        }
    }
}