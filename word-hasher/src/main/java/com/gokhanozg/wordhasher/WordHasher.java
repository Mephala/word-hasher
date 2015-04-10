package com.gokhanozg.wordhasher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Hashes list of strings for all possible search keywords. High memory usage, fastest possible string search for low threshold constructions.
 * 
 * @author Gokhanozg
 *
 */
public class WordHasher {

	private final Set<String> searchSet;
	private final Map<String, Set<String>> keywordToSearchResultMap;
	private final Set<String> allDistinctPossibleSearches;
	private final static int DEFAULT_HASH_LIMIT = 5;
	private final int hashLimit;
	private final ExecutorService hasherExecutor;
	private final int HASHER_WORKER_THREADS = 8;
	private final int KEYWORD_SUBLIST_AMOUNT = HASHER_WORKER_THREADS;

	/**
	 * Constructs hasher for given list. Expensive operation. For instance ( List = {"Gokhan Ozgozen" , "Sabanci University"} , threshold = 2 ) construction hashes all keywords with 2 character at
	 * most. Like : "Go" , "ok", "ha" , "an"
	 * 
	 * @param wordList
	 *            List of strings containing words in which you want to search through later.
	 * @param threshold
	 *            Number of characters in which you want to look through all strings if it contains or not.
	 * 
	 * 
	 */
	public WordHasher(List<String> wordList, int hashLimit) {
		super();
		this.hashLimit = hashLimit;
		this.keywordToSearchResultMap = new HashMap<String, Set<String>>();
		this.allDistinctPossibleSearches = new HashSet<String>();
		this.searchSet = new HashSet<String>();
		this.hasherExecutor = Executors.newFixedThreadPool(HASHER_WORKER_THREADS);
		if (wordList != null) {
			for (String word : wordList) {
				searchSet.add(word);
			}
			startPresearch();
		}
	}

	/**
	 * Call {@link #WordHasher(wordList,hashLimit) constructor with hashlimit } if you need to configure its search speed. Be careful, this class is very expensive in terms of memory usage.
	 */
	public WordHasher(List<String> wordList) {
		this(wordList, DEFAULT_HASH_LIMIT);
	}

	private void startPresearch() {
		prepareKeywords();
		completeSearchResults();
	}

	private void completeSearchResults() {
		for (String keyword : allDistinctPossibleSearches) {
			Set<String> resultStringSetForKeyword = new HashSet<String>();
			for (String possibleSearchSet : searchSet) {
				// if (possibleSearchSet != null && WHUtils.containsIgnoreCase(possibleSearchSet, keyword))
				// resultStringSetForKeyword.add(possibleSearchSet);
				if (possibleSearchSet != null && possibleSearchSet.contains(keyword))
					resultStringSetForKeyword.add(possibleSearchSet);
			}
			keywordToSearchResultMap.put(keyword, resultStringSetForKeyword);
		}
	}

	private void prepareKeywords() {
		List<String> searchList = WHUtils.convertSetToList(searchSet);
		List<List<String>> dividedSearchList = WHUtils.createSubLists(searchList, KEYWORD_SUBLIST_AMOUNT);
		List<Future<Set<String>>> dividerFutures = new ArrayList<Future<Set<String>>>();
		for (List<String> list : dividedSearchList) {
			WordDivider wd = new WordDivider(list, hashLimit);
			dividerFutures.add(hasherExecutor.submit(wd));
		}
		WHUtils.waitFutureList(dividerFutures);
		for (Future<Set<String>> future : dividerFutures) {
			try {
				allDistinctPossibleSearches.addAll(future.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	public Set<String> search(String keyword) {
		Set<String> clonedSet = new HashSet<String>();
		Set<String> answerSet = keywordToSearchResultMap.get(keyword);
		if (answerSet != null) {
			for (String answer : answerSet) {
				if (answer != null)
					clonedSet.add(answer);
			}
		}
		return clonedSet;
	}

}
