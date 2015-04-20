package join;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

public class HashMapJoinPrototype extends AbstractJoinPrototype {

	protected Multimap<Tuple, Tuple> primaryIndexer = HashMultimap.create();
	protected Multimap<Tuple, Tuple> secondaryIndexer = HashMultimap.create();

	protected List<Integer> primaryMask = ImmutableList.of(1);
	protected List<Integer> secondaryMask = ImmutableList.of(0);

	public long join() {
		long n = 0;

		index(primaryIndexer, primaryMask, followss);
		index(secondaryIndexer, secondaryMask, switches);

		for (final Entry<Tuple, Tuple> entry : primaryIndexer.entries()) {
			final Tuple joinAttributes = entry.getKey();
			final Collection<Tuple> joinedTuples = secondaryIndexer.get(joinAttributes);
			n += joinedTuples.size();
		}

		return n;
	}

	private void index(final Multimap<Tuple, Tuple> indexer, final List<Integer> mask, final Collection<Tuple> tuples) {
		for (final Tuple tuple : tuples) {
			final Tuple extracted = tuple.extract(mask);
			indexer.put(extracted, tuple);
		}
	}
}
