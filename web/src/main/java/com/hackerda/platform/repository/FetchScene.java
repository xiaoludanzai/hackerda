package com.hackerda.platform.repository;

public enum FetchScene {

    EVER_GRADE("ever_grade_finish_fetch_set");

    private final String key;

    FetchScene(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
