package com.gokhanozg.wordhasher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

class WordDivider implements Callable<Set<String>> {

	private final List<String> keywordList;
	private final int hashLimit;

	protected WordDivider(List<String> keywordList, int hashLimit) {
		this.keywordList = keywordList;
		this.hashLimit = hashLimit;
	}

	public Set<String> call() throws Exception {
		return divideKeyword();
	}

	private Set<String> divideKeyword() {
		Set<String> dividedKeyWords = new HashSet<String>();
		if (keywordList != null) {
			for (String keyword : keywordList) {
				if (keyword != null && (keyword.length() != 0)) {
					int startIndex = 0;
					while (startIndex <= keyword.length() - 1) {
						int pointerIndex = startIndex + 1;
						while (pointerIndex <= keyword.length() && (pointerIndex - startIndex <= hashLimit)) {
							String searchKeyWord = keyword.substring(startIndex, pointerIndex);
							dividedKeyWords.add(searchKeyWord);
							pointerIndex++;
						}
						startIndex++;
					}
				}
			}
		}
		return dividedKeyWords;
	}

}
