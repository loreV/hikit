package org.hikit.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.log4j.BasicConfigurator;
import org.hikit.common.configuration.ConfiguratorModule;
import org.hikit.web.configuration.ConfigurationManager;

import static org.apache.logging.log4j.LogManager.getLogger;

public class Main {

    final static String logo = "\n  _     _ _    _ _   \n" +
            " | |__ (_| | _(_| |_ \n" +
            " | '_ \\| | |/ | | __|\n" +
            " | | | | |   <| | |_ \n" +
            " |_| |_|_|_|\\_|_|\\__| v1\n" +
            "                     ";

    public static void main(String[] args) {
        BasicConfigurator.configure();
        getLogger(Main.class).info(logo);
        /*
         * Guice.createInjector() takes your Modules, and returns a new Injector
         * instance. Most applications will call this method exactly once, in their
         * main() method.
         */
        final Injector injector = Guice.createInjector(new ConfiguratorModule(args));

        /*
         * Now that we've got the injector, we can build objects.
         */
        final ConfigurationManager configurationManager = injector.getInstance(ConfigurationManager.class);
        configurationManager.init();
    }
}
