/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;

public class myWordsUtilsTests {
    //variables for whitespaced capitalization
    private static String typicalSentence;
    private static String[] whitespaceSelective;
    private static String[] expectedWhitespaceSelective;

    //variables for delimited capitalization
    private static String delimiters;
    private static String vowels;
    private static String delimitedSentence;
    private static String[] expectedDelimitedSentences;
    private static String[] expectedVoweledSentences;

    private static String sentenceForAbreviation;

    @BeforeAll
    public static void setUp(){
        typicalSentence = "The quick brown fox jumps over the lazy dog";
        delimiters = "!@#$%^&*(";
        vowels= "aeiou";
        delimitedSentence = "!the @quick #brown $fox %jumps ^over &the *lazy (dog";
        whitespaceSelective = new String[]{" thequickbrownfoxjumpsoverthelazydog", "the quickbrownfoxjumpsoverthelazydog", "thequick brownfoxjumpsoverthelazydog", "thequickbrown foxjumpsoverthelazydog", "thequickbrownfox jumpsoverthelazydog", "thequickbrownfoxjumps overthelazydog", "thequickbrownfoxjumpsover thelazydog", "thequickbrownfoxjumpsoverthe lazydog", "thequickbrownfoxjumpsoverthelazy dog", "thequickbrownfoxjumpsoverthelazydog "};
        expectedWhitespaceSelective = new String[]{" Thequickbrownfoxjumpsoverthelazydog", "The Quickbrownfoxjumpsoverthelazydog", "Thequick Brownfoxjumpsoverthelazydog", "Thequickbrown Foxjumpsoverthelazydog", "Thequickbrownfox Jumpsoverthelazydog", "Thequickbrownfoxjumps Overthelazydog", "Thequickbrownfoxjumpsover Thelazydog", "Thequickbrownfoxjumpsoverthe Lazydog", "Thequickbrownfoxjumpsoverthelazy Dog", "Thequickbrownfoxjumpsoverthelazydog "};
        expectedDelimitedSentences = new String[]{"!The @quick #brown $fox %jumps ^over &the *lazy (dog", "!the @Quick #brown $fox %jumps ^over &the *lazy (dog", "!the @quick #Brown $fox %jumps ^over &the *lazy (dog", "!the @quick #brown $Fox %jumps ^over &the *lazy (dog", "!the @quick #brown $fox %Jumps ^over &the *lazy (dog", "!the @quick #brown $fox %jumps ^Over &the *lazy (dog", "!the @quick #brown $fox %jumps ^over &The *lazy (dog", "!the @quick #brown $fox %jumps ^over &the *Lazy (dog", "!the @quick #brown $fox %jumps ^over &the *lazy (Dog"};
        expectedVoweledSentences = new String[]{"!the @quick #brown $fox %jumps ^over &the *laZy (dog", "!the @quick #brown $fox %jumps ^oveR &the *lazy (dog", "!the @quiCk #brown $fox %jumps ^over &the *lazy (dog", "!the @quick #broWn $foX %jumps ^oVer &the *lazy (doG", "!the @quIck #brown $fox %juMps ^over &the *lazy (dog"};
        sentenceForAbreviation = "One day work will end, play is forever! One day play will end, work is forever!";
    }

    //partiton testing
    @Test
    public void whitespaceCaptializeTestNoLetters() { assertEquals("!@#$%^&*()_+", WordUtils.capitalize("!@#$%^&*()_+")); }

    @Test
    public void whitespaceCapitalizeTestLettersPrefixedWithSymbols(){ assertEquals("!the @quick #brown $fox %jumps ^over &the *lazy (dog", WordUtils.capitalize(delimitedSentence)); }

    @Test
    public void whitespaceCapitalizeTestTypical(){ assertEquals("The Quick Brown Fox Jumps Over The Lazy Dog", WordUtils.capitalize(typicalSentence)); }

    @Test
    public void delimiterCapitalizeTestWithNoDelimiters(){ assertEquals("!the @quick #brown $fox %jumps ^over &the *lazy (dog", WordUtils.capitalize(delimitedSentence, null)); }


    @Test
    public void delimiterCapitalizeTestWithSymbolDelimiters(){
        for (int x = 0; x < delimiters.length()-1; x++) {
            assertEquals(expectedDelimitedSentences[x], WordUtils.capitalize(delimitedSentence, delimiters.charAt(x)));
        }
    }

    @Test
    public void delimiterCapitalizeTestWithVowelDelimiters(){
        for (int x = 0; x < vowels.length()-1; x++) {
            assertEquals(expectedVoweledSentences[x], WordUtils.capitalize(delimitedSentence, vowels.charAt(x)));
        }
    }

    @Test
    public void abreviateTest(){
        //robust worst case boundary testing
        // lower: min to min+1, upper: min-1
        assertEquals("One... I'll find some way to end this.", WordUtils.abbreviate(sentenceForAbreviation, -1, -1, "... I'll find some way to end this." ));
        assertEquals("One...You will relive every key mistake you've ever made in your life.", WordUtils.abbreviate(sentenceForAbreviation, 0, -1, "...You will relive every key mistake you've ever made in your life."));
        assertEquals("One...We are indeed close.", WordUtils.abbreviate(sentenceForAbreviation, 1, -1, "...We are indeed close."));

        // lower: min-1 to min+1, upper: min
        assertEquals("...Where did the sentence go?", WordUtils.abbreviate(sentenceForAbreviation, -1, 0, "...Where did the sentence go?" ));
        assertEquals("...There's nothing there.", WordUtils.abbreviate(sentenceForAbreviation, 0, 0, "...There's nothing there."));
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, 1, 0, "...Oops, that's an exception."));

        // lower: min-1 to min+1, upper: min+1
        assertEquals("O...Oh!", WordUtils.abbreviate(sentenceForAbreviation, -1, 1, "...Oh!" ));
        assertEquals("O, Oh...", WordUtils.abbreviate(sentenceForAbreviation, 0, 1, ", Oh..."));
        assertEquals("O...what about U?", WordUtils.abbreviate(sentenceForAbreviation, 1, 1, "...what about U?"));

        // lower: min-1 to min+1, upper: typical
        assertEquals("One...huh?", WordUtils.abbreviate(sentenceForAbreviation, -1, 20, "...huh?" ));
        assertEquals("One...what?", WordUtils.abbreviate(sentenceForAbreviation, 0, 'Z', "...what?"));
        assertEquals("One...why?", WordUtils.abbreviate(sentenceForAbreviation, 1, 'z', "...why?"));

        // lower: min-1 to min+1, upper: max-1
        assertEquals("One", WordUtils.abbreviate(sentenceForAbreviation, -1, Integer.MAX_VALUE-1, "" ));
        assertEquals("One", WordUtils.abbreviate(sentenceForAbreviation, 0, Integer.MAX_VALUE-1, ""));
        assertEquals("One", WordUtils.abbreviate(sentenceForAbreviation, 1, Integer.MAX_VALUE-1, ""));

        // lower: min-1 to min+1, upper: max
        assertEquals("One", WordUtils.abbreviate(sentenceForAbreviation, -1, Integer.MAX_VALUE, "" ));
        assertEquals("One", WordUtils.abbreviate(sentenceForAbreviation, 0, Integer.MAX_VALUE, ""));
        assertEquals("One", WordUtils.abbreviate(sentenceForAbreviation, 1, Integer.MAX_VALUE, ""));

        // lower: min-1 to min+1, upper: max+1
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, -1, Integer.MAX_VALUE+1, "" ));
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, 0, Integer.MAX_VALUE+1, ""));
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, 1, Integer.MAX_VALUE+1, ""));

        // lower: typical, upper: min-1 to min+1
        assertEquals("One day work will end, play is forever!... I'll find some way to end this.", WordUtils.abbreviate(sentenceForAbreviation, '!', -1, "... I'll find some way to end this." ));
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, 'A', 0, "#-%" ));
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, 'a', 1, "Nothing seemed to be abbreviated here." ));

        // lower: typical, upper: typical
        assertEquals("One day work will end, play is forever! One day play will end, work#-%", WordUtils.abbreviate(sentenceForAbreviation, 'A', 'Z', "#-%" ));

        // lower: typical, upper: max-1 to max+1
        assertEquals("One day work will end, play is forever! One day play will end, work is forever!", WordUtils.abbreviate(sentenceForAbreviation, 'a', Integer.MAX_VALUE-1, " Nothing seemed to be abbreviated here." ));
        assertEquals("One day work will end, play is forever! One day play will end, work is forever!", WordUtils.abbreviate(sentenceForAbreviation, 'a', Integer.MAX_VALUE, " Nothing seemed to be abbreviated here." ));
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, 'a', Integer.MAX_VALUE+1, "Nothing seemed to be abbreviated here." ));

        // lower: max-1 to max+1, upper: min-1
        assertEquals("One day work will end, play is forever! One day play will end, work is forever!", WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE-1, -1, "" ));
        assertEquals("One day work will end, play is forever! One day play will end, work is forever!", WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE, -1, ""));
        assertEquals("One...We are indeed close.", WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE+1, -1, "...We are indeed close."));

        // lower: max-1 to max+1, upper: min
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE-1, 0, "" ));
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE, 0, ""));
        assertEquals("...We are indeed close.", WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE+1, 0, "...We are indeed close."));

        // lower: max-1 to max+1, upper: typical
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE-1, 20, "" ));
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE, 20, ""));
        assertEquals("One...We are indeed close.", WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE+1, 20, "...We are indeed close."));

        // lower: max-1 to max+1, upper: max-1
        assertEquals("One day work will end, play is forever! One day play will end, work is forever!", WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE-1, Integer.MAX_VALUE-1, "" ));
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE, Integer.MAX_VALUE-1, ""));
        assertEquals("One...We are indeed close.", WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE+1, Integer.MAX_VALUE-1, "...We are indeed close."));

        // lower: max-1 to max+1, upper: max
        assertEquals("One day work will end, play is forever! One day play will end, work is forever!", WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE-1, Integer.MAX_VALUE, "" ));
        assertEquals("One day work will end, play is forever! One day play will end, work is forever!",WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE, Integer.MAX_VALUE, ""));
        assertEquals("One...We are indeed close.", WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE+1, Integer.MAX_VALUE, "...We are indeed close."));

        // lower: max-1 to max+1, upper: max+1
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE-1, Integer.MAX_VALUE+1, "" ));
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE, Integer.MAX_VALUE+1, ""));
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, Integer.MAX_VALUE+1, Integer.MAX_VALUE+1, "...We are indeed close."));


    }


    



}
