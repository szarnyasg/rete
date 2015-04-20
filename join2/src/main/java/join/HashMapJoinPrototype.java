package join;

public class HashMapJoinPrototype extends AbstractJoinPrototype {

	public long join() {
		long n = 0;
		for (final Tuple follows : followss) {
			final Long swP = follows.get(1);

			for (final Tuple sw : switches) {
				final Long swP2 = sw.get(0);
				if (swP == swP2) {
					n++;
				}
			}
		}
		return n;
	}
}
