package join.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import join.RDFHelper;
import join.Tuple;

import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.rio.helpers.StatementCollector;

public class MyCollector extends StatementCollector {

	final Map<URI, Collection<Tuple>> collections = new HashMap<>();

	public void registerCollection(final ValueFactory f, final String type, final Collection<Tuple> statements) {
		final URI typeURI = f.createURI(type);
		collections.put(typeURI, statements);
	}

	@Override
	public void handleStatement(final Statement st) {
		final Collection<Tuple> collection = collections.get(st.getPredicate());

		if (collection == null) {
			return;
		}

		final long subject = RDFHelper.extractId(st.getSubject().stringValue());
		final long object = RDFHelper.extractId(st.getObject().stringValue());

		final Tuple tuple = new Tuple(subject, object);
		collection.add(tuple);
	}

}
