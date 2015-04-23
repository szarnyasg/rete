package join;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.LinkedList;

import join.test.MyCollector;

import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.turtle.TurtleParser;

public class Indexer {

	private static final String INDEXER_DIR = "/home/szarnyasg/index/";
	private static final String SWITCH = "switch";
	private static final String DEFINEDBY = "definedBy";
	private static final String SENSOR_EDGE = "sensor";
	private static final String FOLLOWS = "follows";
	protected static final String MODEL_PREFIX = "/home/szarnyasg/git/trainbenchmark/models/railway-repair-";
	protected static final String MODEL_EXTENSION = ".ttl";

	protected static final String BASE_PREFIX = "http://www.semanticweb.org/ontologies/2015/trainbenchmark#";
	protected static final String FOLLOWS_TYPE = BASE_PREFIX + FOLLOWS;
	protected static final String SENSOR_EDGE_TYPE = BASE_PREFIX + SENSOR_EDGE;
	protected static final String DEFINEDBY_TYPE = BASE_PREFIX + DEFINEDBY;
	protected static final String SWITCH_TYPE = BASE_PREFIX + SWITCH;

	protected Collection<Tuple> switches = new LinkedList<>();
	protected Collection<Tuple> followss = new LinkedList<>();
	protected Collection<Tuple> sensors = new LinkedList<>();
	protected Collection<Tuple> definedBys = new LinkedList<>();

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

		final String sizePrefix = size + "-";
		serialize(sizePrefix + SWITCH, switches);
		serialize(sizePrefix + FOLLOWS, followss);
		serialize(sizePrefix + SENSOR_EDGE, sensors);
		serialize(sizePrefix + DEFINEDBY, definedBys);
	}

	public Collection<Tuple> getSwitches() {
		return switches;
	}

	public Collection<Tuple> getFollowss() {
		return followss;
	}

	public Collection<Tuple> getSensors() {
		return sensors;
	}

	public Collection<Tuple> getDefinedBys() {
		return definedBys;
	}

	public void serialize(final String fileName, final Object collection) throws IOException {
		final String filePath = INDEXER_DIR + fileName;
		final FileOutputStream fileOut = new FileOutputStream(filePath);
		final ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(collection);
		out.close();
		fileOut.close();
	}

	public Collection<Tuple> deserialize(final String fileName) throws IOException, ClassNotFoundException {
		final String filePath = INDEXER_DIR + fileName;
		final FileInputStream fileIn = new FileInputStream(filePath);
		final ObjectInputStream in = new ObjectInputStream(fileIn);
		final Object collection = in.readObject();
		in.close();
		fileIn.close();
		return (Collection<Tuple>) collection;
	}

	public void reload(final int size) throws IOException, ClassNotFoundException {
		final String sizePrefix = size + "-";
		switches = deserialize(sizePrefix + SWITCH);
		followss = deserialize(sizePrefix + FOLLOWS);
		sensors = deserialize(sizePrefix + SENSOR_EDGE);
		definedBys = deserialize(sizePrefix + DEFINEDBY);
	}

}
