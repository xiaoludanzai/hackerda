package com.hackerda.spider.cookie;

import org.junit.Before;
import org.junit.Test;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MemoryCookiePersistTest {

    private MemoryCookiePersist<String> persist;


    @Before
    public void before(){
        persist = new MemoryCookiePersist<>();
    }


    @Test
    public void saveAndGet() {
        List<HttpCookie> list = new ArrayList<>();
        String dummyAccount = "test";
        persist.saveByAccount(list, dummyAccount);
        List<HttpCookie> get = persist.getByAccount(dummyAccount);
        assertSame(list, get);

    }

    @Test
    public void clearByAccount() {
        List<HttpCookie> list = new ArrayList<>();
        String dummyAccount = "test";
        persist.saveByAccount(list, dummyAccount);
        persist.clearByAccount(dummyAccount);
        List<HttpCookie> get = persist.getByAccount(dummyAccount);
        assertNull(get);
    }

    @Test
    public void loadAll() {
        int count = 3;

        for(int x = 0; x< 3; x++){
            persist.saveByAccount(new ArrayList<>(), Integer.toString(x));
        }
        assertEquals(persist.loadAll().size(), count);
    }
}