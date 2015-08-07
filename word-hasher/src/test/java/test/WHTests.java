package test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mockit.integration.junit4.JMockit;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.gokhanozg.wordhasher.WordHasher;

@RunWith(JMockit.class)
public class WHTests {

	@Test
	public void testConstruction() {

		int wordCount = 1000;
		List<String> wordz = new ArrayList<String>();
		for (int i = 0; i < wordCount; i++) {
			wordz.add(UUID.randomUUID().toString().substring(0, 5));
		}
		long start = System.currentTimeMillis();
		WordHasher wh = new WordHasher(wordz, 10);
		System.err.println("Construction took " + (System.currentTimeMillis() - start) + " ms.");
	}
}
