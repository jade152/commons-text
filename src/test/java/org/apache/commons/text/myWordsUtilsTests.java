package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;
public class myWordsUtilsTests {
    //variables for whitespaced capitalization
    private static String typicalSentence = "The quick brown fox jumps over the lazy dog";
    private static String[] whitespaceSelective = {" thequickbrownfoxjumpsoverthelazydog", "the quickbrownfoxjumpsoverthelazydog", "thequick brownfoxjumpsoverthelazydog", "thequickbrown foxjumpsoverthelazydog", "thequickbrownfox jumpsoverthelazydog", "thequickbrownfoxjumps overthelazydog", "thequickbrownfoxjumpsover thelazydog", "thequickbrownfoxjumpsoverthe lazydog", "thequickbrownfoxjumpsoverthelazy dog", "thequickbrownfoxjumpsoverthelazydog "};
    private static String[] expectedWhitespaceSelective = {" Thequickbrownfoxjumpsoverthelazydog", "The Quickbrownfoxjumpsoverthelazydog", "Thequick Brownfoxjumpsoverthelazydog", "Thequickbrown Foxjumpsoverthelazydog", "Thequickbrownfox Jumpsoverthelazydog", "Thequickbrownfoxjumps Overthelazydog", "Thequickbrownfoxjumpsover Thelazydog", "Thequickbrownfoxjumpsoverthe Lazydog", "Thequickbrownfoxjumpsoverthelazy Dog", "Thequickbrownfoxjumpsoverthelazydog "};

    //variables for delimited capitalization
    private static String delimiters = "!@#$%^&*(";
    private static String vowels= "aeiou";
    private static String delimitedSentence = "!the @quick #brown $fox %jumps ^over &the *lazy (dog";
    private static String[] expectedDelimitedSentences = {"!The @quick #brown $fox %jumps ^over &the *lazy (dog", "!the @Quick #brown $fox %jumps ^over &the *lazy (dog", "!the @quick #Brown $fox %jumps ^over &the *lazy (dog", "!the @quick #brown $Fox %jumps ^over &the *lazy (dog", "!the @quick #brown $fox %Jumps ^over &the *lazy (dog", "!the @quick #brown $fox %jumps ^Over &the *lazy (dog", "!the @quick #brown $fox %jumps ^over &The *lazy (dog", "!the @quick #brown $fox %jumps ^over &the *Lazy (dog", "!the @quick #brown $fox %jumps ^over &the *lazy (Dog"};
    private static String[] expectedVoweledSentences = {"!the @quick #brown $fox %jumps ^over &the *laZy (dog", "!the @quick #brown $fox %jumps ^oveR &the *lazy (dog", "!the @quiCk #brown $fox %jumps ^over &the *lazy (dog", "!the @quick #broWn $foX %jumps ^oVer &the *lazy (doG", "!the @quIck #brown $fox %juMps ^over &the *lazy (dog"};

    @Test
    public void whitespaceCaptializeTest() {
        assertEquals("!@#$%^&*()_+", WordUtils.capitalize("!@#$%^&*()_+"));
        assertEquals("!the @quick #brown $fox %jumps ^over &the *lazy (dog", WordUtils.capitalize(delimitedSentence));
        assertEquals("The Quick Brown Fox Jumps Over The Lazy Dog", WordUtils.capitalize(typicalSentence));
        for (int x = 0; x < whitespaceSelective.length - 1; x++) {
            assertEquals(expectedWhitespaceSelective[x], WordUtils.capitalize(whitespaceSelective[x]));
        }
    }


}
