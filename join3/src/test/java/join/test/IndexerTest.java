package join.test;

import java.io.IOException;

import join.Indexer;

import org.junit.Test;

public class IndexerTest extends ReteTest {

	@Test
	public void populate() throws IOException {
		for (int modelSize = MIN; modelSize <= MAX; modelSize *= 2) {
			final Indexer indexer = new Indexer();
			indexer.load(modelSize);
		}

	}
}
