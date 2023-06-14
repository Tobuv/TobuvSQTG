package com.tobuv.sqtg.search.repository;

import com.tobuv.sqtg.model.search.SkuEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SkuRepository extends ElasticsearchRepository<SkuEs, Long> {
}
