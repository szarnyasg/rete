package join;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

public class AntiJoin extends AbstractJoin {

	public AntiJoin(final List<Integer> primaryMask, final List<Integer> secondaryMask) {
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

			if (secondaryIndexer.get(primaryTupleJoinAttributes).isEmpty()) {
				joinedTuples.add(primaryTuple);
			}
		}

		return joinedTuples;
	}
}
