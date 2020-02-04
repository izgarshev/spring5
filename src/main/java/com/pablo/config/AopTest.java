package com.pablo.config;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.ProxyFactory;

@Aspect
public class AopTest implements MethodInterceptor, ThrowsAdvice {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.print("James ");
        Object retVal = invocation.proceed();
        System.out.println("!");
        return retVal;
    }

    @AllArgsConstructor
    @ToString
    static class Agent implements AgentIF{
        private  String name;

        @Override
        public void speak() {
            System.out.println(name);
        }
    }

    interface AgentIF {
        void speak();
    }

    static class Main {
        public static void main(String[] args) {
            AgentIF agent = new Agent("Bond");
            ProxyFactory proxyFactory = new ProxyFactory(agent);
            AopTest aopTest = new AopTest();
            proxyFactory.addAdvice(aopTest);
            AgentIF proxy = (AgentIF) proxyFactory.getProxy();
            agent.speak();
            proxy.speak();
            System.out.println(proxyFactory.adviceIncluded(aopTest));
            proxyFactory.removeAdvice(aopTest);
            System.out.println(proxyFactory.adviceIncluded(aopTest));
        }
    }
}
