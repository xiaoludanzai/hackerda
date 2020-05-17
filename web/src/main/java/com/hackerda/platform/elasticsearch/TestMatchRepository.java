package com.hackerda.platform.elasticsearch;

import com.hackerda.platform.elasticsearch.document.TestMatchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestMatchRepository extends ElasticsearchRepository<TestMatchDocument,Long> {
}
