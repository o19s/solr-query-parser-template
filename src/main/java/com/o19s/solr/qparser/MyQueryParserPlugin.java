package com.o19s.solr.qparser;

import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QParserPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyQueryParserPlugin extends QParserPlugin {

    private static final Logger LOG = LoggerFactory.getLogger(MyQueryParserPlugin.class);

    public QParser createParser(String s, SolrParams localParams, SolrParams globalParams, SolrQueryRequest solrQueryRequest) {
        LOG.info(String.format("createParser(s=%s, localParams=%s, globalParams=%s)", s, localParams, globalParams));
        return new MyQueryParser(s, localParams, globalParams, solrQueryRequest);
    }
}
