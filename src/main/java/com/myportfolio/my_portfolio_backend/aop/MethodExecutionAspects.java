package com.myportfolio.my_portfolio_backend.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodExecutionAspects {

//    @Before("execution(* com.myportfolio.my_portfolio_backend.service.*.*(..))")
//    public void beforeMethod(JoinPoint joinPoint){
//        System.out.println("BEFORE: Se va a ejecutar el método " + joinPoint.getSignature().getName());
//    }

//    @After("execution(* com.myportfolio.my_portfolio_backend.service.*.save(..))")
//    public void afterMethod(JoinPoint joinPoint){
//        System.out.println("AFTER: Se ejecutado correctamente el método " + joinPoint.getSignature().getName());
//    }

//    @AfterThrowing("execution(* com.myportfolio.my_portfolio_backend.service.*.*(..))")
//    public void aftherThrowingMethod(JoinPoint joinPoint){
//        System.out.println("ERROR: No se ha ejecutado el método " + joinPoint.getSignature().getName() +", párametros erróneos");
//    }

//    @AfterReturning("execution(* com.myportfolio.my_portfolio_backend.service.*.*(..))")
//    public void afterReturningMethod(JoinPoint joinPoint){
//        System.out.println("AFTER: Se ejecutado correctamente el método " + joinPoint.getSignature().getName());
//    }

    @Around("execution(* com.myportfolio.my_portfolio_backend.service.*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String metodo = joinPoint.getSignature().getName() ;
        Object result = null ;

        try{
            System.out.println("BEFORE: Se va a ejecutar el método " +metodo);
            result = joinPoint.proceed() ;
            System.out.println("AFTER: Se ha ejecutado correctamente");

            return result ;
        }catch (Throwable e){
            System.out.println("AFTER: Hubo un error en la ejecución del método " +metodo);
            throw  e ;
        }

    }

}
