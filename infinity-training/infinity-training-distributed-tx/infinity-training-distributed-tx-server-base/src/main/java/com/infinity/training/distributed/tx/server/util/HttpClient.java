package com.infinity.training.distributed.tx.server.util;

import com.alibaba.fastjson.JSON;
import com.infinity.training.distributed.tx.server.transactional.InfinityTransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 18:00
 */
@Slf4j
@Component
public class HttpClient {

    @Autowired
    private InfinityTransactionManager transactionManager;

    public String get(String url) throws RuntimeException {
        String result = "";
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-type", "application/json");
            httpGet.addHeader("groupId", transactionManager.getCurrentGroupId());
            httpGet.addHeader("transactionCount", String.valueOf(transactionManager.getTransactionCount()));
            log.info("服务调用, 发送请求: url: {}, header: {}", url, JSON.toJSONString(httpGet.getAllHeaders()));
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
            log.info("服务调用, 接收响应: statusCode: {}, result: {}", statusCode, result);
            response.close();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("http request error: " + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return result;
    }
}
