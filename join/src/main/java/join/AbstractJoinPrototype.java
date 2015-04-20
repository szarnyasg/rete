package join;

import java.io.File;
import java.io.IOException;

import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.base.RepositoryBase;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.memory.MemoryStore;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class AbstractJoinPrototype {

	protected static final String MODEL_PREFIX = "/home/szarnyasg/git/trainbenchmark/models/railway-repair-";
	protected static final String MODEL_EXTENSION = ".ttl";

	protected static final String BASE_PREFIX = "http://www.semanticweb.org/ontologies/2015/trainbenchmark#";
	protected static final String FOLLOWS_TYPE = BASE_PREFIX + "follows";
	protected static final String SENSOR_EDGE_TYPE = BASE_PREFIX + "sensor";
	protected static final String DEFINEDBY_TYPE = BASE_PREFIX + "definedBy";
	protected static final String SWITCH_TYPE = BASE_PREFIX + "switch";

	protected Multimap<Long, Long> switches;
	protected Multimap<Long, Long> followss;
	protected Multimap<Long, Long> sensors;
	protected Multimap<Long, Long> definedBys;

	protected RepositoryConnection con;
	protected ValueFactory f;

	public void load(final int size) throws IOException {
		final RepositoryBase repository = new SailRepository(new MemoryStore());
		final File modelFile = new File(MODEL_PREFIX + size + MODEL_EXTENSION);

		try {
			repository.initialize();
			con = repository.getConnection();
			con.add(modelFile, BASE_PREFIX, RDFFormat.TURTLE);

			f = repository.getValueFactory();

			switches = collectEdges(SWITCH_TYPE);
			followss = collectEdges(FOLLOWS_TYPE);
			sensors = collectEdges(SENSOR_EDGE_TYPE);
			definedBys = collectEdges(DEFINEDBY_TYPE);

			con.close();
		} catch (RDFParseException | RepositoryException | IOException e) {
			throw new IOException(e);
		}
	}

	private Multimap<Long, Long> collectEdges(final String edgeType) throws RepositoryException {
		final Multimap<Long, Long> edges = HashMultimap.create();

		final URI edgeTypeURI = f.createURI(edgeType);
		final RepositoryResult<Statement> statements = con.getStatements(null, edgeTypeURI, null, true);
		while (statements.hasNext()) {
			final Statement s = statements.next();
			final long subject = RDFHelper.extractId(s.getSubject().stringValue());
			final long object = RDFHelper.extractId(s.getObject().stringValue());

			edges.put(subject, object);
		}

		return edges;
	}

}
