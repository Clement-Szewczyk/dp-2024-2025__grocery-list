package com.fges;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Pour tester le mode web, décommente la ligne suivante :
        //runWebServer();

        // Pour le mode CLI, laisse la ligne suivante :
        System.exit(exec(args));
    }

    public static int exec(String[] args) throws IOException {
        return CLI.exec(args);
    }

}