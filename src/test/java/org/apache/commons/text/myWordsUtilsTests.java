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
    private static String typicalSentence = "The quick brown fox jumps over the lazy dog";
    private static String[] whitespaceSelective = {" thequickbrownfoxjumpsoverthelazydog", "the quickbrownfoxjumpsoverthelazydog", "thequick brownfoxjumpsoverthelazydog", "thequickbrown foxjumpsoverthelazydog", "thequickbrownfox jumpsoverthelazydog", "thequickbrownfoxjumps overthelazydog", "thequickbrownfoxjumpsover thelazydog", "thequickbrownfoxjumpsoverthe lazydog", "thequickbrownfoxjumpsoverthelazy dog", "thequickbrownfoxjumpsoverthelazydog "};
    private static String[] expectedWhitespaceSelective = {" Thequickbrownfoxjumpsoverthelazydog", "The Quickbrownfoxjumpsoverthelazydog", "Thequick Brownfoxjumpsoverthelazydog", "Thequickbrown Foxjumpsoverthelazydog", "Thequickbrownfox Jumpsoverthelazydog", "Thequickbrownfoxjumps Overthelazydog", "Thequickbrownfoxjumpsover Thelazydog", "Thequickbrownfoxjumpsoverthe Lazydog", "Thequickbrownfoxjumpsoverthelazy Dog", "Thequickbrownfoxjumpsoverthelazydog "};

    //variables for delimited capitalization
    private static String delimiters = "!@#$%^&*(";
    private static String vowels= "aeiou";
    private static String delimitedSentence = "!the @quick #brown $fox %jumps ^over &the *lazy (dog";
    private static String[] expectedDelimitedSentences = {"!The @quick #brown $fox %jumps ^over &the *lazy (dog", "!the @Quick #brown $fox %jumps ^over &the *lazy (dog", "!the @quick #Brown $fox %jumps ^over &the *lazy (dog", "!the @quick #brown $Fox %jumps ^over &the *lazy (dog", "!the @quick #brown $fox %Jumps ^over &the *lazy (dog", "!the @quick #brown $fox %jumps ^Over &the *lazy (dog", "!the @quick #brown $fox %jumps ^over &The *lazy (dog", "!the @quick #brown $fox %jumps ^over &the *Lazy (dog", "!the @quick #brown $fox %jumps ^over &the *lazy (Dog"};
    private static String[] expectedVoweledSentences = {"!the @quick #brown $fox %jumps ^over &the *laZy (dog", "!the @quick #brown $fox %jumps ^oveR &the *lazy (dog", "!the @quiCk #brown $fox %jumps ^over &the *lazy (dog", "!the @quick #broWn $foX %jumps ^oVer &the *lazy (doG", "!the @quIck #brown $fox %juMps ^over &the *lazy (dog"};

    private static String sentenceForAbreviation = "One day work will end, play is forever! One day play will end, work is forever!";

    @Test
    public void whitespaceCaptializeTest() {
        assertEquals("!@#$%^&*()_+", WordUtils.capitalize("!@#$%^&*()_+"));
        assertEquals("!the @quick #brown $fox %jumps ^over &the *lazy (dog", WordUtils.capitalize(delimitedSentence));
        assertEquals("The Quick Brown Fox Jumps Over The Lazy Dog", WordUtils.capitalize(typicalSentence));
        for (int x = 0; x < whitespaceSelective.length - 1; x++) {
            assertEquals(expectedWhitespaceSelective[x], WordUtils.capitalize(whitespaceSelective[x]));
        }
    }

    @Test
    public void delimiterCapitalizeTest(){
        assertEquals("!the @quick #brown $fox %jumps ^over &the *lazy (dog", WordUtils.capitalize(delimitedSentence, null));
        for (int x = 0; x < delimiters.length()-1; x++) {
            assertEquals(expectedDelimitedSentences[x], WordUtils.capitalize(delimitedSentence, delimiters.charAt(x)));
        }
        for (int x = 0; x < vowels.length()-1; x++) {
            assertEquals(expectedVoweledSentences[x], WordUtils.capitalize(delimitedSentence, vowels.charAt(x)));
        }

    }
    @Test
    public void abreviateTest(){
        assertEquals("One day work will end, play is forever! ... I'll find some way to end this.", WordUtils.abbreviate(sentenceForAbreviation, '!', -1, "... I'll find some way to end this." ));
        assertEquals("One day work will end, play is forever! One day play will end, w #-%", WordUtils.abbreviate(sentenceForAbreviation, 'A', 'Z', "#-%" ));
        assertEquals("One day work will end, play is forever! One day play will end, work is forever! Nothing seemed to be abbreviated here.", WordUtils.abbreviate(sentenceForAbreviation, 'a', 'z', "Nothing seemed to be abbreviated here." ));
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate(sentenceForAbreviation, -1, -1, "... I'll find some way to end this." ));

    }


}
