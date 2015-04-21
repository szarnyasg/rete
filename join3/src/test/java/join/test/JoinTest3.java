package join.test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import join.AntiJoin;
import join.EquiJoin;
import join.Indexer;
import join.Tuple;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

public class JoinTest3 extends ReteTest {

	@BeforeClass
	public static void init() {
		System.out.println("modelSize\tresultSize\tloadTime\tjoinTime");
	}

	@Test
	public void t1() throws IOException, ClassNotFoundException {
		for (int modelSize = MIN; modelSize <= MAX; modelSize *= 2) {

			final Stopwatch stopWatch = Stopwatch.createStarted();
			final Indexer indexer = new Indexer();
			indexer.reload(modelSize);
			final long loadTime = stopWatch.elapsed(TimeUnit.MILLISECONDS);

			final List<Integer> join1primaryMask = ImmutableList.of(0);
			final List<Integer> join1secondaryMask = ImmutableList.of(1);
			final EquiJoin join1 = new EquiJoin(join1primaryMask, join1secondaryMask);

			final List<Integer> join2primaryMask = ImmutableList.of(1);
			final List<Integer> join2secondaryMask = ImmutableList.of(0);
			final EquiJoin join2 = new EquiJoin(join2primaryMask, join2secondaryMask);

			final List<Integer> antiJoinPrimaryMask = ImmutableList.of(2, 3);
			final List<Integer> antiJoinSecondaryMask = ImmutableList.of(0, 1);
			final AntiJoin antiJoin = new AntiJoin(antiJoinPrimaryMask, antiJoinSecondaryMask);

			final Stopwatch stopWatch2 = Stopwatch.createStarted();
			final Collection<Tuple> join1result = join1.join(indexer.getSwitches(), indexer.getFollowss());

			final Collection<Tuple> join2result = join2.join(join1result, indexer.getSensors());
			final Collection<Tuple> result = antiJoin.join(join2result, indexer.getDefinedBys());
			final long joinTime = stopWatch2.elapsed(TimeUnit.MILLISECONDS);

			System.out.println(modelSize + "\t" + result.size() + "\t" + loadTime + "\t" + joinTime);
		}
	}
}
