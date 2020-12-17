/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fon.p1.text_manipulation.WordListManager;
import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author pepper
 */
public class WordListManagerTest {

    private String absolutePath;

    public WordListManagerTest() {
        String path = "src/test/resources";

        File file = new File(path);
        this.absolutePath = file.getAbsolutePath();
    }

    @BeforeAll
    public static void setUpClass() {

    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testWordListOpensTxt() throws FileNotFoundException {
        // src/test/resources/test.txt dosyasını açacak. Herhangi bir hata olmaması beklenir.        
        assertDoesNotThrow(() -> new WordListManager(this.absolutePath + "/words.txt"));
    }

    @Test
    public void testWordListThrowsFileNotFound() {
        assertThrows(FileNotFoundException.class, () -> {
            new WordListManager(this.absolutePath + "/unknown.txt");
        });
    }

    @Test
    public void testFindsWord() throws FileNotFoundException {
        // src/test/resources/test.txt dosyasını açacak
        WordListManager WLManager = new WordListManager(this.absolutePath + "/words.txt");

        int shouldFind = WLManager.find("abacuses");
        int shouldNotFind = WLManager.find("Fatih");

        assertAll("Should return 'abacuses' for word that contained by wordlist and -1 for not founded word",
                () -> assertEquals("abacuses", WLManager.getStrList().get(shouldFind)),
                () -> assertEquals(-1, shouldNotFind));
    }

    @Test
    public void testForSingleTransposition() throws FileNotFoundException {
        // src/test/resources/test.txt dosyasını açacak
        WordListManager WLManager = new WordListManager(this.absolutePath + "/words.txt");

        // we know that test.txt and wordlist includes 'aalii'
        String word = "aalii";
        int shouldFind = WLManager.find(word);

        String singleTransposition = "alaii";

        // should return "aalii";
        String singleTranspositionResponse = WLManager.getTrueFormForSingleTransposition(singleTransposition);

        String notSingleTransposition = "aaxii";

        // should return self, because it has not signle transposition;
        String notSingleTranspositionResponse = WLManager.getTrueFormForSingleTransposition(notSingleTransposition);

        // should return self, because it has not signle transposition;
        // the calculation of this edge case:
        // aalii - alwii = 0xx00 this means single transposition pair distance should equal
        // and should be opposite sign. eg. a-l = x, l-a=-x;
        String notSingleTransposition2 = "alwii";

        String notSingleTranspositionResponse2 = WLManager.getTrueFormForSingleTransposition(notSingleTransposition2);

        String singleTransposition2 = "manum";

        // should return "aalii";
        String singleTranspositionResponse2 = WLManager.getTrueFormForSingleTransposition(singleTransposition2);

        assertAll("Should find 'aalii', and get from list, look for single transposition and look for a not single transposition responses",
                () -> assertEquals(word, WLManager.getStrList().get(shouldFind)),
                () -> assertEquals(word, singleTranspositionResponse),
                () -> assertNotEquals("manos", singleTranspositionResponse2),
                () -> assertNotEquals(word, notSingleTranspositionResponse),
                () -> assertNotEquals(word, notSingleTranspositionResponse2),
                () -> assertEquals(notSingleTransposition, notSingleTranspositionResponse)
        );

    }

}
