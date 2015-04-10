package com.gokhanozg.wordhasher;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class WordHasherTest {

	@Test
	public void testWordHasherListOfString() {
		List<String> stringz = new ArrayList<String>();
		stringz.add("Gokhan");
		stringz.add("Fahri");
		stringz.add("Muhittin");
		WordHasher wh = new WordHasher(stringz);
	}

}
