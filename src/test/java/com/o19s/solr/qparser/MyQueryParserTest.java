package com.o19s.solr.qparser;

import org.apache.solr.SolrTestCaseJ4;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Unit tests
 */
public class MyQueryParserTest extends SolrTestCaseJ4 {
    @BeforeClass
    public static void beforeClass() throws Exception {
        // TODO: Fix the Log4j configuration issue.
        System.out.println("beforeClass");
        initCore("solrconfig.xml", "schema.xml", "target/test-classes/solr");
    }

    @After
    public void clearCore() throws SAXException {
        System.out.println("clearCore");
        assertNull(h.validateUpdate(delQ("*:*")));
        assertNull(h.validateUpdate(commit()));

        assertU(optimize());
    }

    @Test
    public void testQuery1() throws SAXException {
        System.out.println("testQuery1");
        assertNull(h.validateUpdate(adoc("id", "1", "text", "Hello")));
        assertNull(h.validateUpdate(adoc("id", "2", "text", "Hello world")));
        assertNull(h.validateUpdate(commit()));
        assertU(optimize());

        assertQ("Test with qt=/select",
                req("defType", "myqp",
                        "q", "hello world",
                        "qt", "/select",
                        "qf", "text"),
                "//*[@numFound='2']"
        );

        assertQ("Test with qt=/myqp",
                req("q", "hello AND world",
                        "qt", "/myqp",
                        "qf", "text"),
                "//*[@numFound='1']"
        );
    }
}
