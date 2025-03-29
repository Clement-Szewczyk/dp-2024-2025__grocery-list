package com.fges;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.exit(exec(args));
    }

    public static int exec(String[] args) throws IOException {
        return CLI.exec(args);
    }
}
