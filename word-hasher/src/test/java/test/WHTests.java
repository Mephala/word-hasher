package test;

import java.util.*;

import mockit.integration.junit4.JMockit;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.gokhanozg.wordhasher.WordHasher;

import static org.junit.Assert.assertTrue;

@RunWith(JMockit.class)
public class WHTests {

    @Test
    public void testConstruction() {

        int wordCount = 10000;
        List<String> wordz = new ArrayList<String>();
        for (int i = 0; i < wordCount; i++) {
            wordz.add(UUID.randomUUID().toString().substring(0, 5));
        }
        long start = System.currentTimeMillis();
        WordHasher wh = new WordHasher(wordz);
        System.err.println("Construction took " + (System.currentTimeMillis() - start) + " ms.");
    }

    @Test
    public void testSearching1() throws InterruptedException {
        List<String> searchKeywords = new ArrayList<String>();
        McaProjectEAR[] ears = McaProjectEAR.values();
        for (McaProjectEAR ear : ears) {
            searchKeywords.add(ear.toString());
        }
        WordHasher wordHasher = new WordHasher(searchKeywords);
        assertTrue(wordHasher.search("gen") != null && wordHasher.search("gen").size() > 0);
    }

    @Test
    public void testSearching2() throws InterruptedException {
        List<String> searchKeywords = new ArrayList<String>();
        searchKeywords.add("gokhan");
        searchKeywords.add("gokhan ozgozen");
        searchKeywords.add("gokhan ozgozen hudavendigar");
        searchKeywords.add("gokhan ozgozen hudavendigar merkezi");
        searchKeywords.add("gokhan ozgozen hudavendigar merkezi yalitim");
        searchKeywords.add("gokhan ozgozen hudavendigar merkezi yalitim bu kapagin");
        searchKeywords.add("gokhan ozgozen hudavendigar merkezi yalitim bu kapagin altinda");
        searchKeywords.add("gokhan ozgozen hudavendigar merkezi yalitim bu kapagin altinda iyi bankadir");
        searchKeywords.add("gokhan ozgozen hudavendigar merkezi yalitim bu kapagin altinda iyi bankadir yoksa");
        searchKeywords.add("gokhan ozgozen hudavendigar merkezi yalitim bu kapagin altinda iyi bankadir yoksa baska");
        searchKeywords.add("gokhan ozgozen hudavendigar merkezi yalitim bu kapagin altinda iyi bankadir yoksa baska bir");
        searchKeywords.add("gokhan ozgozen hudavendigar merkezi yalitim bu kapagin altinda iyi bankadir yoksa baska bir arzunuz");
        WordHasher wordHasher = new WordHasher(searchKeywords,40,new Locale("tr-TR"));
        Set<String> retval = wordHasher.search("DİG");
        System.out.println(retval);
    }
}
