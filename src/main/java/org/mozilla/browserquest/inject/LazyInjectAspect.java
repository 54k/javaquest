package org.mozilla.browserquest.inject;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.FieldSignature;

import java.lang.reflect.Field;

@Aspect
public class LazyInjectAspect {

    @Inject
    private Injector injector;

    @Pointcut("get(* *..*)")
    private void getterPointcut() {
    }

    @Pointcut("set(* *..*)")
    private void setterPointcut() {
    }

    @Pointcut("@annotation(org.mozilla.browserquest.inject.LazyInject)")
    private void hasAnnotation() {
    }

    @Around("getterPointcut() && hasAnnotation()")
    public synchronized Object aroundLazyInject(ProceedingJoinPoint pjp) throws Throwable {
        Field field = ((FieldSignature) pjp.getSignature()).getField();
        field.setAccessible(true);

        Object target = pjp.getTarget();
        Object service = field.get(target);

        if (service == null) {
            service = injector.getInstance(field.getType());
            field.set(target, service);
        }

        return service;
    }

    @Before("setterPointcut() && hasAnnotation()")
    public void beforeSet() throws Throwable {
        throw new UnsupportedOperationException();
    }
}
