package join;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class Tuple implements Serializable {

	private static final long serialVersionUID = 1L;
	protected final List<Long> list;

	public Tuple() {
		this.list = null;
	}
	
	public Tuple(final Long... elements) {
		this.list = ImmutableList.copyOf(elements);
	}

	Tuple(final List<Long> list) {
		this.list = list;
	}

	public static Tuple createTuple(final Long... elements) {
		return new Tuple(elements);
	}

	public static Tuple createTuple(final List<Long> tuple) {
		return new Tuple(tuple);
	}

	public Long get(final int i) {
		return list.get(i);
	}

	public int size() {
		return list.size();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder("<");
		if (list.size() > 0) {
			builder.append(list.get(0));
			for (int i = 1; i < list.size(); i++) {
				builder.append(", ");
				builder.append(list.get(i));
			}
		}
		builder.append(">");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Tuple other = (Tuple) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		return true;
	}

	public Tuple extract(final List<Integer> mask) {
		final List<Long> extractedList = new ArrayList<>(mask.size());
		for (final Integer m : mask) {
			extractedList.add(list.get(m));
		}
		return new Tuple(extractedList);
	}

	public static Tuple join(final Tuple primaryTuple, final Tuple secondaryTuple, final List<Integer> primaryMask,
			final List<Integer> secondaryMask) {
		final int size = primaryTuple.size() + secondaryTuple.size() - primaryMask.size();
		final List<Long> joinedList = new ArrayList<>(size);
		joinedList.addAll(primaryTuple.list);
		for (int i = 0; i < secondaryTuple.list.size(); i++) {
			if (!secondaryMask.contains(i)) {
				joinedList.add(secondaryTuple.list.get(i));
			}
		}
		return new Tuple(joinedList);
	}
}
