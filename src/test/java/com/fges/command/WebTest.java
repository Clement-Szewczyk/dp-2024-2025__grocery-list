package com.fges.command;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import com.fges.GroceryListManager;

class WebTest {

    @Test
    void should_start_server_on_default_port() {

        CommandOption.getInstance().setCommandArgs(new String[]{"web"});
        Web web = new Web();

        int port = web.configureServerPort();

        assertThat(port).isEqualTo(8080);
    }

    @Test
    void should_start_server_on_custom_port() {

        CommandOption.getInstance().setCommandArgs(new String[]{"web ", "1080"});
        Web web = new Web();

        int port = web.configureServerPort();

        assertThat(port).isEqualTo(1080);
    }

    @Test
    void should_return_error_for_invalid_port() {
        // Crée un manager avec DAO à null (à adapter selon ton code)
        GroceryListManager manager = new GroceryListManager(null);

        // Configure les arguments pour forcer un port invalide
        CommandOption options = CommandOption.getInstance();
        options.setCommandArgs(new String[]{"web", "invalidPort"});

        Web webCommand = new Web();

        int result = webCommand.execute(manager);

        assertThat(result).isEqualTo(1);
    }
}
