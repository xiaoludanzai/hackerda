package com.hackerda.spider;

import com.hackerda.spider.support.search.evaluation.EvaluationPagePost;
import com.hackerda.spider.support.search.evaluation.EvaluationPost;
import com.hackerda.spider.support.search.evaluation.searchresult.TeachingEvaluation;

public interface UrpEvaluationSpider {

    TeachingEvaluation searchTeachingEvaluationInfo();

    String getEvaluationToken(EvaluationPagePost evaluationPagePost);

    void evaluation(EvaluationPost evaluationPost);
}
