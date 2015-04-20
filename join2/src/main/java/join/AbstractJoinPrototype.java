package join;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;

import join.test.MyCollector;

import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.turtle.TurtleParser;

public class AbstractJoinPrototype {

	protected static final String MODEL_PREFIX = "/home/szarnyasg/git/trainbenchmark/models/railway-repair-";
	protected static final String MODEL_EXTENSION = ".ttl";

	protected static final String BASE_PREFIX = "http://www.semanticweb.org/ontologies/2015/trainbenchmark#";
	protected static final String FOLLOWS_TYPE = BASE_PREFIX + "follows";
	protected static final String SENSOR_EDGE_TYPE = BASE_PREFIX + "sensor";
	protected static final String DEFINEDBY_TYPE = BASE_PREFIX + "definedBy";
	protected static final String SWITCH_TYPE = BASE_PREFIX + "switch";

	protected Collection<Tuple> switches = new LinkedList<>();
	protected Collection<Tuple> followss = new LinkedList<>();
	protected Collection<Tuple> sensors = new LinkedList<>();
	protected Collection<Tuple> definedBys = new LinkedList<>();

	// protected final StatementCollector collector = new StatementCollector();
	protected final MyCollector collector = new MyCollector();
	protected final ValueFactory f = new ValueFactoryImpl();

	public void load(final int size) throws IOException {
		final File modelFile = new File(MODEL_PREFIX + size + MODEL_EXTENSION);

		final TurtleParser parser = new TurtleParser();
		final InputStream inputStream = new FileInputStream(modelFile);

		collector.registerCollection(f, SWITCH_TYPE, switches);
		collector.registerCollection(f, FOLLOWS_TYPE, followss);
		collector.registerCollection(f, SENSOR_EDGE_TYPE, sensors);
		collector.registerCollection(f, DEFINEDBY_TYPE, definedBys);

		try {
			parser.setRDFHandler(collector);
			parser.setValueFactory(f);
			parser.parse(inputStream, "");
		} catch (RDFParseException | IOException | RDFHandlerException e) {
			throw new IOException(e);
		}
	}

}
