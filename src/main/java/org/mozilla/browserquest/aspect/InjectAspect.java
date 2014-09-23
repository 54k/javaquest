package org.mozilla.browserquest.aspect;

import com.google.inject.Injector;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.FieldSignature;

@Aspect
public class InjectAspect {

    public static Injector injector;

    @Around("get(* org.mozilla.browserquest.model.controller..*) && @annotation(com.google.inject.Inject)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        FieldSignature signature = (FieldSignature) pjp.getSignature();
        Class serviceType = signature.getField().getType();
        System.out.println(String.format("Injecting %s to %s.", serviceType.getCanonicalName(), signature.getField().getName()));
        return injector.getInstance(serviceType);
    }
}
