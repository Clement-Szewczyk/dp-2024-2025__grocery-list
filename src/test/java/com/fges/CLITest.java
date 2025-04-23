package com.fges;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CLITest {

    @Test
    void should_always_pass() {
        assertThat(true).isTrue();
    }

    @Test
    void should_pass_with_valid_command_options() {
        String[] args = {"-f", "csv", "-c", "Fruits", "add", "Pomme", "2"};
        int result = CLI.exec(args);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void should_not_pass_with_invalid_format(){
        String[] args = {"-f", "txt", "-c", "Fruits", "add", "Pomme", "2"};
        int result = CLI.exec(args);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void should_not_pass_with_missing_command() {
        String[] args = {"-f", "csv", "-c", "Fruits"};
        int result = CLI.exec(args);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void should_not_pass_with_unknown_command() {
        String[] args = {"-f", "csv", "-c", "Fruits", "unknown"};
        int result = CLI.exec(args);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void should_not_pass_with_invalid_quantity() {
        String[] args = {"-f", "csv", "-c", "Fruits", "add", "Pomme", "not-a-number"};
        int result = CLI.exec(args);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void should_pass_with_default_options() {
        String[] args = {"list"};
        int result = CLI.exec(args);
        assertThat(result).isEqualTo(0);
    }
}