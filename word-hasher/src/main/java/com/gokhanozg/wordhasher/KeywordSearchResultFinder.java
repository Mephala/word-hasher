package com.gokhanozg.wordhasher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

class KeywordSearchResultFinder implements Callable<Map<String, Set<String>>> {

	private final List<String> allWords;
	private final List<String> keywords;

	public Map<String, Set<String>> call() throws Exception {
		Map<String, Set<String>> keywordToSearchResultMap = new HashMap<String, Set<String>>();
		try {
			if (allWords == null || keywords == null)
				return keywordToSearchResultMap;

			for (String keyword : keywords) {
				Set<String> searchResults = new HashSet<String>();
				for (String word : allWords) {
					if (WHUtils.isSearchResult(keyword, word))
						searchResults.add(word);
				}
				if (searchResults.size() > 0)
					keywordToSearchResultMap.put(keyword, searchResults);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keywordToSearchResultMap;
	}

	public KeywordSearchResultFinder(List<String> allWords, List<String> keywords) {
		super();
		this.allWords = allWords;
		this.keywords = keywords;
	}

}
