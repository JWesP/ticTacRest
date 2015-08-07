package tictacrest;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import tictacrest.resources.TicTacRestResource;

public class TicTacRestApplication extends Application<TicTacRestConfiguration> {
	
	public static void main(String[] args) throws Exception {
        new TicTacRestApplication().run(args);
    }

    @Override
    public String getName() {
        return "tic-tac-rest";
    }

    @Override
    public void initialize(Bootstrap<TicTacRestConfiguration> bootstrap) {
        // nothing to do yet
    }

	@Override
    public void run(TicTacRestConfiguration configuration,
                    Environment environment) {
		final TicTacRestResource resource = new TicTacRestResource();
		environment.jersey().register(resource);
	}

}
