package org.ltrails.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.ltrails.common.configuration.ConfiguratorModule;
import org.ltrails.web.configuration.ConfigurationManager;

public class Main {


    public static void main(String[] args) {
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
