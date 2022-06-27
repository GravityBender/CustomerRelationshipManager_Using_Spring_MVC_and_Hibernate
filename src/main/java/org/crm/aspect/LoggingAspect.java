package org.crm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* org.crm.controllers.*.*(..))")
    private void pointcutForControllersPackage(){

    }

    @Pointcut("execution(* org.crm.dao.*.*(..))")
    private void pointcutForDaoPackage(){

    }

    @Pointcut("execution(* org.crm.service.*.*(..))")
    private void pointcutForServicePackage(){

    }

    @Pointcut("pointcutForControllersPackage()||pointcutForDaoPackage()||pointcutForServicePackage()")
    private void pointcutForAppFlow(){

    }

    @Before("pointcutForAppFlow()")
    public void before(JoinPoint theJoinPoint){
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("--> in @Before: calling method: "+ theMethod);

        Object[] args = theJoinPoint.getArgs();

        for (Object tempArgs : args) {
            myLogger.info("-->Argument: "+tempArgs);
        }
    }

    @AfterReturning(
            pointcut = "pointcutForAppFlow()",
            returning = "theResult"
    )
    public void afterReturning(JoinPoint theJoinPoint, Object theResult){
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("--> in @AfterReturning: from method: "+ theMethod);

        myLogger.info("--> result: "+theResult);
    }

}
