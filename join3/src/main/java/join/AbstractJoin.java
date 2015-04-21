package join;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public abstract class AbstractJoin {

	protected Multimap<Tuple, Tuple> primaryIndexer = HashMultimap.create();
	protected Multimap<Tuple, Tuple> secondaryIndexer = HashMultimap.create();
	protected final List<Integer> primaryMask;
	protected final List<Integer> secondaryMask;

	public AbstractJoin(final List<Integer> primaryMask, final List<Integer> secondaryMask) {
		this.primaryMask = primaryMask;
		this.secondaryMask = secondaryMask;
	}

	protected void index(final Multimap<Tuple, Tuple> indexer, final List<Integer> mask, final Collection<Tuple> tuples) {
		for (final Tuple tuple : tuples) {
			final Tuple extracted = tuple.extract(mask);
			indexer.put(extracted, tuple);
		}
	}

	public abstract Collection<Tuple> join(final Collection<Tuple> primaryInput, final Collection<Tuple> secondaryInput);

}