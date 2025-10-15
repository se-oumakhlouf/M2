package fr.uge.jee.criteria;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config-criteria.xml");

        Criterium di = context.getBean("dataIntegrity", Criterium.class);
        Criterium ec = context.getBean("escapeChecking", Criterium.class);

        var validator = new Validator(List.of(di, ec));
        boolean isOk = validator.check("Hello World!");
        System.out.println("isOk : " + isOk);
    }
}
