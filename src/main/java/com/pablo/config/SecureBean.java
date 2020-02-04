package com.pablo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

@Slf4j
public class SecureBean {


    public void writeSecureMessage() {
        System.out.println("its secure message");
    }

    static class UserInfo {
        private String userName;
        private String password;

        public UserInfo(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
    }

    static class SecurityManager {
        private ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();

        public void login(String username, String password) {
            threadLocal.set(new UserInfo(username, password));
        }

        public void logout() {
            threadLocal.set(null);
        }

        public UserInfo getLoggedOnUser() {
            return threadLocal.get();
        }
    }

    static class SecurityAdvice implements MethodBeforeAdvice {
        private SecurityManager securityManager;

        public SecurityAdvice() {
            this.securityManager = new SecurityManager();
        }

        @Override
        public void before(Method method, Object[] objects, Object o) throws Throwable {
            UserInfo userInfo = securityManager.getLoggedOnUser();
            if (userInfo == null) {
                System.out.println("no user auth");
                throw new SecurityException("You must login");
            } else if ("John".equals(userInfo.getUserName())) {
                System.out.println("OKAY");
            } else {
                System.out.println("Logged user is: " + userInfo.getUserName() + ", it's not OKAY!");
            }
        }
    }

    public static void main(String[] args) {
        SecurityManager manager = new SecurityManager();
        SecureBean bean = getSecureBean();

        manager.login("John", "pwd");
        bean.writeSecureMessage();
        manager.logout();

        try {
            manager.login("invalid user", "pwd");
            bean.writeSecureMessage();
        } catch (SecurityException ex) {
            System.out.println(ex.getMessage());
        } finally {
            manager.logout();
        }

        try {
            bean.writeSecureMessage();
        } catch (SecurityException ex) {
            System.out.println("Exc2" + ex.getMessage());
        }
    }

    private static SecureBean getSecureBean() {
        SecureBean target = new SecureBean();
        SecurityAdvice advice = new SecurityAdvice();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(advice);

        SecureBean proxy = (SecureBean) proxyFactory.getProxy();
        return proxy;
    }


}
