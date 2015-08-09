package tictacrest.health;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.codahale.metrics.health.HealthCheck;

public class PersistenceHealthCheck extends HealthCheck {
	private final static String PERSISTENCE_TEST_FILE_NAME = "test.xml";

	@Override
	protected Result check() throws Exception {
		PersistenceTestPojo ptp = new PersistenceTestPojo();
		ptp.setNum(42);
		ptp.setText("All the Dude ever wanted was his rug back...it really tied the room together");
		
		Serializer serializer = new Persister();
		File testFile = new File(PERSISTENCE_TEST_FILE_NAME);
		
		/*
		 * Persist to file
		 */
		serializer.write(ptp, testFile);
		
		/*
		 * Read from file
		 */
		PersistenceTestPojo ptp1 = serializer.read(PersistenceTestPojo.class, testFile);
		
		/*
		 * Cleanup
		 */
		testFile.delete();
		
		/*
		 * Compare before/after
		 */
		if (!ptp.equals(ptp1)){
			return Result.unhealthy("Persisted object not preserved");
		}
		
		return Result.healthy();
	}

}
