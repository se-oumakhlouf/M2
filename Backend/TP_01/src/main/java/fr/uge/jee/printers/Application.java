package fr.uge.jee.printers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Beans sont instanciés au moment de la création de l'ApplicationContext

// L'inconvéniant de différer l'instansation des Beans est dans le cas ou une class dépend du bean,
// et que celui-ci n'est pas encore instancié

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config-printers.xml");

        MessagePrinter frPrinter = context.getBean("fr-printer", MessagePrinter.class);
        MessagePrinter enPrinter = context.getBean("en-printer", MessagePrinter.class);
        MessagePrinter cuPrinter = context.getBean("cu-printer", MessagePrinter.class);
        MessagePrinter countPrinter = context.getBean("count-printer", MessagePrinter.class);

        frPrinter.printMessage();
        enPrinter.printMessage();
        cuPrinter.printMessage();

        for (int i = 0; i < 5; i++) {
            countPrinter.printMessage();
        }

        // Singleton
        MessagePrinter countPrinter2 = context.getBean("count-printer", CountMessagePrinter.class);
        for (int i = 0; i < 5; i++) {
            countPrinter2.printMessage();
        }

        MessagePrinter slowPrinter = context.getBean("slow-printer", SlowConstructionMessagePrinter.class);
        slowPrinter.printMessage();
    }
}