package join.test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import join.HashMapJoinPrototype;
import join.Indexer;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

public class JoinTest3 {

	private static final int MIN = 1;
	private static final int MAX = 1024;

	@BeforeClass
	public static void init() {
		System.out.println("modelSize\tresultSize\tloadTime\tjoinTime");
	}

	@Test
	public void t1() throws IOException {
		for (int modelSize = MIN; modelSize <= MAX; modelSize *= 2) {

			final List<Integer> primaryMask = ImmutableList.of(0);
			final List<Integer> secondaryMask = ImmutableList.of(1);

			final HashMapJoinPrototype proto = new HashMapJoinPrototype(primaryMask, secondaryMask);

			final Stopwatch stopWatch = Stopwatch.createStarted();
			final Indexer indexer = new Indexer();
			indexer.load(modelSize);
			final long loadTime = stopWatch.elapsed(TimeUnit.MILLISECONDS);

			final Stopwatch stopWatch2 = Stopwatch.createStarted();
			final long resultSize = proto.join(indexer.getSwitches(), indexer.getFollowss());
			final long joinTime = stopWatch2.elapsed(TimeUnit.MILLISECONDS);

			System.out.println(modelSize + "\t" + resultSize + "\t" + loadTime + "\t" + joinTime);
		}
	}
}
