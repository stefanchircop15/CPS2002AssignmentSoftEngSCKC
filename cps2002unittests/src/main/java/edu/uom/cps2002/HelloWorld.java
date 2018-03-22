package edu.uom.cps2002;

public class HelloWorld {
    public String getMessage() {
        return "Hello World!!";
    }

    public String getMessageByName(String name) {
        if(name == null) {
            return getMessage();
        }

        if(name.equals("William")) {
            return "Hello Your Majesty!!";
        }

        return "Hello " + name + "!!";
    }
}
