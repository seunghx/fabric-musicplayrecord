package com.melon.chaincode.model.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.*;

@Getter
@Setter
@ToString
public class Query {

    /**
     *  specified field by hyperledger fabric design, used often for identifying state db(couch db) documents.
     */
    private static final String DOC_TYPE_SELECTOR = "docType";

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, Object> selector;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Map<String, SortDirection>> sort;

    @JsonProperty("use_index")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> indexing;

    @JsonProperty("execution_stats")
    private boolean executionStat = true;

    private Query(Map<String, Object> selector, List<Map<String, SortDirection>> sort, List<String> indexing){
        this.selector = selector;
        this.sort = sort;
        this.indexing = indexing;
    }

    public static class QueryBuilder {

        private final Map<String, Object> selectors = new HashMap<>();
        private final List<Map<String, SortDirection>> sorts = new ArrayList<>();
        private final List<String> indexes = new ArrayList<>();

        private QueryBuilder(){}
        private QueryBuilder(String docType){
            selector(DOC_TYPE_SELECTOR, docType);
        }

        public static QueryBuilder newQueryBuilder(String docType){
            return new QueryBuilder(docType);
        }

        public static QueryBuilder from(Query query){
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.selectors.putAll(query.getSelector());
            queryBuilder.indexes.addAll(query.getIndexing());
            queryBuilder.sorts.addAll(query.getSort());

            return queryBuilder;
        }

        public QueryBuilder selector(String property, Object propertyValue){

            selectors.put(property, propertyValue);

            return this;
        }

        public QueryBuilder sortBy(String sortedField, SortDirection direction){
            HashMap<String, SortDirection> sortMap = new HashMap<>();
            sortMap.put(sortedField, direction);
            sorts.add(sortMap);

            return this;
        }

        public QueryBuilder indexing(String designDoc, String indexName){
            indexes.add(designDoc);
            indexes.add(indexName);

            return this;
        }

        public Query buildQuery(){
            Query query = new Query(selectors, sorts, indexes);
            return query;
        }
    }
}
