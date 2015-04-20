package join.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import join.HashMapJoinPrototype;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Stopwatch;

public class JoinTest1 {

	private static final int MIN = 1;
	private static final int MAX = 2048;

	@BeforeClass
	public static void init() {
		System.out.println("modelSize\tresultSize\tloadTime\tjoinTime");
	}

	@Test
	public void t1() throws IOException {
		for (int modelSize = MIN; modelSize <= MAX; modelSize *= 2) {
			final HashMapJoinPrototype proto = new HashMapJoinPrototype();

			final Stopwatch stopWatch = Stopwatch.createStarted();
			proto.load(modelSize);
			final long loadTime = stopWatch.elapsed(TimeUnit.MILLISECONDS);

			final Stopwatch stopWatch2 = Stopwatch.createStarted();
			final long resultSize = proto.join();
			final long joinTime = stopWatch2.elapsed(TimeUnit.MILLISECONDS);

			System.out.println(modelSize + "\t" + resultSize + "\t" + loadTime + "\t" + joinTime);
		}
	}
}
