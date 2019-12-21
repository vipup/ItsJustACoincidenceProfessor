package com.tylerj.coincidenceprofessor.tests;

import com.tylerj.coincidenceprofessor.codesearch.CodeSearch;

import static org.junit.Assert.assertNotSame;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for CodeSearch
 */
public class CodeSearchTests {
    /**
     * Tests a sample Hello, world program.
     */
    @Test
    public void BasicHelloWorldTest() {
        String s1 = "public class Main {" +
                "public static void main(String[] args) { System.out.println(\"Hello, world!\") } }";

        assertEquals(CodeSearch.getSimilarSourceCode(s1, 23, 2), CodeSearch.getSimilarSourceCode(s1, 23, 2));
    }
    
    /**
     * Tests a sample Hello, world program.
     */
    @Test
    public void testRandomCodePen() {
        String s1 = new ReadFully().readFully("Codepen1.js") ;

        assertNotSame(CodeSearch.getSimilarSourceCode(s1.substring(s1.indexOf("let"), s1.indexOf("$(")), 22, 2), CodeSearch.getSimilarSourceCode(s1.substring(s1.indexOf("let"), s1.indexOf("$(")), 22, 3));
    }
}