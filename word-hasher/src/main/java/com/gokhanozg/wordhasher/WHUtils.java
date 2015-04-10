package com.gokhanozg.wordhasher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

class WHUtils {

	protected static <E> List<E> convertSetToList(Set<E> set) {
		if (set == null)
			return null;
		List<E> list = new ArrayList<E>();
		for (E e : set) {
			list.add(e);
		}
		return list;
	}

	protected static <E> Set<E> convertListToSet(List<E> list) {
		if (list == null)
			return null;
		Set<E> set = new HashSet<E>();
		for (E e : list) {
			set.add(e);
		}
		return set;
	}

	protected static <E> List<List<E>> createSubLists(List<E> mainList, int divisionAmount) {
		if (divisionAmount == 0 || mainList == null)
			return null;
		int regularListSize = mainList.size() / divisionAmount;
		List<List<E>> sublistslist = new ArrayList<List<E>>();
		for (int i = 0; i < divisionAmount; i++) {
			int startIndex = regularListSize * i;
			if (i == divisionAmount - 1) {
				sublistslist.add(mainList.subList(startIndex, mainList.size()));
			} else {
				int endIndex = startIndex + regularListSize;
				sublistslist.add(mainList.subList(startIndex, endIndex));
			}
		}
		return sublistslist;
	}

	protected static <E> void waitFutureList(List<Future<E>> futuresToWaitFor) {
		if (futuresToWaitFor != null && futuresToWaitFor.size() > 0) {
			int index = 0;
			while (index < futuresToWaitFor.size()) {
				Future<E> f = futuresToWaitFor.get(index);
				if (f.isDone())
					index++;
			}
		}
	}
}
