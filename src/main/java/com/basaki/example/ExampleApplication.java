package com.basaki.example;

import com.basaki.example.data.dao.BookDAO;
import com.basaki.example.data.entity.Book;
import com.basaki.example.resource.BookResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class ExampleApplication extends Application<ExampleConfiguration> {

    private final HibernateBundle<ExampleConfiguration> hibernateBundle =
            new HibernateBundle<>(Book.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(true)));

        // Add Swagger
        // http://localhost:8080/swagger
        bootstrap.addBundle(new SwaggerBundle<ExampleConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ExampleConfiguration config) {
                SwaggerBundleConfiguration swaggerConfig = config.getSwagger();
                swaggerConfig.setContact("Example Dropwizard Application");
                swaggerConfig.setContactEmail("indra@gmail.com");
                swaggerConfig.setTitle("Example Dropwizard Service");
                swaggerConfig.setDescription("API for Dropwizard Service");
                swaggerConfig.setVersion("1.0.0");

                return swaggerConfig;
            }
        });

        bootstrap.addBundle(new AssetsBundle());

        bootstrap.addBundle(new MigrationsBundle<ExampleConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(ExampleConfiguration exampleConfiguration, Environment environment) throws Exception {
        final BookDAO dao = new BookDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new BookResource(dao));
    }

    @Override
    public String getName() {
        return "Example Dropwizard Service";
    }

    public static void main(String[] args) throws Exception {
        new ExampleApplication().run(args);
    }
}
