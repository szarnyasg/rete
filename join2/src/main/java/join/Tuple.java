package join;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class Tuple {

	final List<Long> tuple;

	public Tuple(final Long... elements) {
		this.tuple = ImmutableList.copyOf(elements);
	}

	Tuple(final List<Long> tuple) {
		this.tuple = tuple;
	}

	public static Tuple createTuple(final Long... elements) {
		return new Tuple(elements);
	}

	public static Tuple createTuple(final List<Long> tuple) {
		return new Tuple(tuple);
	}

	public Long get(final int i) {
		return tuple.get(i);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder("<");
		if (tuple.size() > 0) {
			builder.append(tuple.get(0));
			for (int i = 1; i < tuple.size(); i++) {
				builder.append(", ");
				builder.append(tuple.get(i));
			}
		}
		builder.append(">");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tuple == null) ? 0 : tuple.hashCode());
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
		if (tuple == null) {
			if (other.tuple != null)
				return false;
		} else if (!tuple.equals(other.tuple))
			return false;
		return true;
	}

	public Tuple extract(final List<Integer> mask) {
		final List<Long> list = new ArrayList<>(mask.size());
		for (final Integer m : mask) {
			list.add(tuple.get(m));
		}
		return new Tuple(list);
	}
}
