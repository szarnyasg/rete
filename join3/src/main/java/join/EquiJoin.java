package join;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

public class EquiJoin extends AbstractJoin {

	public EquiJoin(final List<Integer> primaryMask, final List<Integer> secondaryMask) {
		super(primaryMask, secondaryMask);
	}

	@Override
	public Collection<Tuple> join(final Collection<Tuple> primaryInput, final Collection<Tuple> secondaryInput) {
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

		return joinedTuples;
	}
}
