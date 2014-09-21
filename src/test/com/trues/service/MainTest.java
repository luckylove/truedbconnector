package com.trues.service;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * User: son.nguyen
 * Date: 10/24/13
 * Time: 1:34 PM
 */
public class MainTest {

    protected static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        TRUEDBService.GetQemRoute("aaaaaa", "pr", "koka", "");
    }


}


