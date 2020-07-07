package test.chuvasova.webcrawler.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import test.chuvasova.webcrawler.domain.CrawledPage;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class CrawlerTest {

    @Test
    public void getCrawledPageFromHtmlDocument() {

        Crawler crawler = new Crawler(1, 1);

        List<String> keywords = Arrays.asList("Tesla");

        CrawledPage resultTestPage = crawler.getCrawledPageFromHtmlDocument(keywords, "test", null, getStubPage());
        assertNotNull(resultTestPage);
        assertEquals("test", resultTestPage.getUrl());
        assertTrue(resultTestPage.calculateTotalInclusion() == 2);
    }

    private Document getStubPage() {
        return Jsoup.parse("<p><b>Tesla, Inc.</b> (formerly <b>Tesla Motors, Inc.</b>) is an American <a href=\"/wiki/Electric_vehicle\" title=\"Electric vehicle\">electric vehicle</a> and clean energy company based in <a href=\"/wiki/Palo_Alto,_California\" title=\"Palo Alto, California\">Palo Alto, California</a>.<sup id=\"cite_ref-10\" class=\"reference\"><a href=\"#cite_note-10\">[9]</a></sup> The company specializes in <a href=\"/wiki/Electric_vehicle\" title=\"Electric vehicle\">electric vehicle</a> manufacturing, battery <a href=\"/wiki/Energy_storage\" title=\"Energy storage\">energy storage</a> from home to grid scale and, through its acquisition of <a href=\"/wiki/SolarCity\" title=\"SolarCity\">SolarCity</a>, <a href=\"/wiki/Solar_panel\" title=\"Solar panel\">solar panel</a> and <a href=\"/wiki/SolarCity#Solar_roof\" title=\"SolarCity\">solar roof tile</a> manufacturing.<sup id=\"cite_ref-11\" class=\"reference\"><a href=\"#cite_note-11\">[10]</a></sup>\n" +
                "</p>", "http://test.test");

    }

    @Test
    public void testGetConnection() {
        String startUrl = "http://google.com/test";
        Connection connection = Jsoup.connect(startUrl);
        assertFalse(connection.response().statusCode() == 200);
        System.err.println("Error creating HTTP connection");
    }
}
