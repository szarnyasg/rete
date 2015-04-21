package join;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class HashMapJoinPrototype {

	protected Multimap<Tuple, Tuple> primaryIndexer = HashMultimap.create();
	protected Multimap<Tuple, Tuple> secondaryIndexer = HashMultimap.create();
	private final List<Integer> primaryMask;
	private final List<Integer> secondaryMask;

	public HashMapJoinPrototype(final List<Integer> primaryMask, final List<Integer> secondaryMask) {
		super();
		this.primaryMask = primaryMask;
		this.secondaryMask = secondaryMask;
	}

	public long join(final Collection<Tuple> primaryInput, final Collection<Tuple> secondaryInput) {
		final long n = 0;

		index(primaryIndexer, primaryMask, primaryInput);
		index(secondaryIndexer, secondaryMask, secondaryInput);

		final Collection<Tuple> joinedTuples = new HashSet<>();

		for (final Entry<Tuple, Tuple> entry : primaryIndexer.entries()) {
			final Tuple primaryTuple = entry.getValue();
			final Tuple primaryTupleJoinAttributes = entry.getKey();
			final Collection<Tuple> secondaryTuples = secondaryIndexer.get(primaryTupleJoinAttributes);

			for (final Tuple secondaryTuple : secondaryTuples) {
				final Tuple joinedTuple = Tuple.join(primaryTuple, secondaryTuple, primaryMask, secondaryMask);
				joinedTuples.add(joinedTuple);
			}

		}

		return joinedTuples.size();
	}

	private void index(final Multimap<Tuple, Tuple> indexer, final List<Integer> mask, final Collection<Tuple> tuples) {
		for (final Tuple tuple : tuples) {
			final Tuple extracted = tuple.extract(mask);
			indexer.put(extracted, tuple);
		}
	}
}
