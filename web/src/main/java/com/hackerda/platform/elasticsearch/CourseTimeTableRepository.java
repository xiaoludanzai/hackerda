package com.hackerda.platform.elasticsearch;

import com.hackerda.platform.elasticsearch.document.CourseTimeTableDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseTimeTableRepository extends ElasticsearchRepository<CourseTimeTableDocument,Long> {
}
