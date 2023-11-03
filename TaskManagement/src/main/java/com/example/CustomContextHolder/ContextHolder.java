package com.example.CustomContextHolder;

import com.example.Model.User;

public class ContextHolder {
        private static final ThreadLocal<User> contextHolder = new ThreadLocal<User>();

        public static void setContext(User databaseType) {
            contextHolder.set(databaseType);
        }

        public static User getContext() {
            return (User) contextHolder.get();
        }
        public static void clearContext() {
            contextHolder.remove();
        }
    }
