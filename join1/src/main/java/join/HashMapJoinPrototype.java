package join;

import java.util.Collection;
import java.util.Map.Entry;

public class HashMapJoinPrototype extends AbstractJoinPrototype {

	public long join() {
		long n = 0;
		for (final Entry<Long, Long> follows : followss.entries()) {
			final Long swP = follows.getValue();

			final Collection<Long> matches = switches.get(swP);
			// System.out.println(matches);
			n += matches.size();
		}
		return n;
	}

}
