package fr.uge.jee.printers;

public class SlowConstructionMessagePrinter implements MessagePrinter {


    public SlowConstructionMessagePrinter() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printMessage() {
        System.out.println("Hello World after 5 seconds!");
    }
}
