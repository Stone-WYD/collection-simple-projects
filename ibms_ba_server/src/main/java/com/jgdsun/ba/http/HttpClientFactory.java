package com.jgdsun.ba.http;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientFactory {

    private static HttpClientFactory instance = new HttpClientFactory();

    CloseableHttpClient closeableHttpClient = null;

    public HttpClientFactory()
    {
        closeableHttpClient = HttpClients.createDefault();
    }

    public static HttpClientFactory getInstance()
    {
        return instance;
    }

    /**
     * 获取httpclient
     * @return
     */
    public CloseableHttpClient getCloseableHttpClient()
    {
        return closeableHttpClient;
    }

}
