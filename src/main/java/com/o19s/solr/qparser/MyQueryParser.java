package com.o19s.solr.qparser;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.SyntaxError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyQueryParser extends QParser {

    private static final Logger LOG = LoggerFactory.getLogger(MyQueryParser.class);

    public MyQueryParser(String qstr, SolrParams localParams, SolrParams params, SolrQueryRequest req) {
        super(qstr, localParams, params, req);
    }

    private String getBestParam(SolrParams globalParams, SolrParams localParams, String parameter, String defVal) {
        SolrParams preferredParams = localParams;
        if (preferredParams == null) {
            preferredParams = params;
        }
        return preferredParams.get(parameter, defVal);
    }

    public Query parse() throws SyntaxError {
        // Get the field to query
        String qf = getBestParam(params, localParams, "qf", null);

        // Pass-through to the Lucene Classic query parser
        QueryParser parser = new QueryParser(qf, new StandardAnalyzer());
        Query query = null;
        try {
            query = parser.parse(qstr);
            LOG.info(String.format("End-user's query: %s --> Parsed Lucene Classic query: %s", qstr, query.toString()));
        } catch (ParseException pe) {
            LOG.error(String.format("Parsing error: %s", pe));
            throw new SyntaxError(pe);
        }
        return query;
    }
}
