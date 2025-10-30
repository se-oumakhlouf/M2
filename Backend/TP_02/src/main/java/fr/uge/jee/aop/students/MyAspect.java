package fr.uge.jee.aop.students;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class MyAspect {

  private final List<Long> saveToDB = new ArrayList<>();
  private final List<Integer> loadFromDB = new ArrayList<>();

  @Around(value="execution(* fr.uge.jee.aop.students.RegistrationService.create*(..))")
  public Object around(ProceedingJoinPoint pjp) throws Throwable {
    System.out.println("Before " + pjp.getSignature().getName() + " with arguments " + Arrays.toString(pjp.getArgs()));
    var ret = pjp.proceed();
    System.out.println("Return id " + ret + " by " + pjp.getSignature().getName());
    return ret;
  }


  @Around(value="execution(* fr.uge.jee.aop.students.RegistrationService.saveToDB(..))")
  public void beforeSaveToDB(ProceedingJoinPoint pjp) throws Throwable {
    LocalDateTime before = LocalDateTime.now();
    pjp.proceed();
    LocalDateTime after = LocalDateTime.now();
    saveToDB.add(Duration.between(before, after).toNanos());
  }

  @After("execution(* fr.uge.jee.aop.students.RegistrationService.printReport(..))")
  public void afterReport(JoinPoint pjp) {
    System.out.println("Measured save to db : " + saveToDB);
  }
}
