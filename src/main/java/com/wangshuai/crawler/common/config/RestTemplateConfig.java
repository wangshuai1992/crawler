package com.wangshuai.crawler.common.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import xin.allonsy.utils.CommonUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * RestTemplateConfig
 *
 * @author wangshuai
 * @date 2020-07-09 10:59
 */
@Configuration
@Slf4j
public class RestTemplateConfig {

    @Value("${crawler.http.timeout}")
    private int timeout;

    @Value("${crawler.http.maxtotal}")
    private int maxTotal;

    @Value("${crawler.http.maxperroute}")
    private int maxPerRoute;

    @Value("${crawler.http.log.lengthlimit}")
    private int logLengthLimit;

    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setDefaultMaxPerRoute(maxPerRoute);
        connectionManager.setValidateAfterInactivity(2000);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setConnectionManager(connectionManager)
                .build();
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory(HttpClient httpClient) {
        // BufferingClientHttpRequestFactory包装 使ClientHttpRequestInterceptor读取响应副本
        return new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        // 解决请求乱码
        StringHttpMessageConverter m = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalMessageConverters(m).build();
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        // restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new CustomRequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    private class CustomRequestInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException {
            HttpHeaders headers = request.getHeaders();
            headers.add("Cache-Control", "no-cache");
            if (!headers.containsKey("Content-Type")) {
                headers.add("Content-Type", "application/json; charset=UTF-8");
            }

            headers.add("Host", request.getURI().getHost());
            if (!headers.containsKey("User-Agent")) {
//                headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0");
                headers.add("User-Agent", "PostmanRuntime/7.26.1");
            }
            return this.executeWithLog(request, body, execution);
        }

        private ClientHttpResponse executeWithLog(HttpRequest request, byte[] body,
                                                  ClientHttpRequestExecution execution) throws IOException {
            String requestLog = traceRequest(request, body);
            long startTime = System.currentTimeMillis();
            ClientHttpResponse response = execution.execute(request, body);
            long endTime = System.currentTimeMillis();
            String elapseLog = "==== time elapsed ===== " + (endTime - startTime) + "(ms)";
            String responseLog = traceResponse(response);
            log.info("\n{}\n{}\n{}", requestLog, elapseLog, responseLog);
            return response;
        }

        private String traceRequest(HttpRequest request, byte[] body) {
            JSONObject jsonObject = new JSONObject(true);
            jsonObject.put("URI", request.getURI());
            jsonObject.put("Request body", CommonUtil.limitedString(new String(body, StandardCharsets.UTF_8), logLengthLimit));
            jsonObject.put("Method", request.getMethod());
            jsonObject.put("Headers", request.getHeaders());
            return "==== rest request ===== " + jsonObject.toJSONString();
        }

        private String traceResponse(ClientHttpResponse response) {
            try {
                JSONObject jsonObject = new JSONObject(true);
                jsonObject.put("Status code", response.getRawStatusCode());
                jsonObject.put("Status text", response.getStatusText());
                jsonObject.put("Response body", CommonUtil
                        .limitedString(StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8), logLengthLimit));
                jsonObject.put("Headers", response.getHeaders());
                return "==== rest response ==== " + jsonObject.toJSONString();
            } catch (Exception e) {
                log.error("traceResponse error", e);
                return "==== rest response ==== traceResponse error";
            }
        }
    }

    // private class CustomResponseErrorHandler extends DefaultResponseErrorHandler {
    // @Override
    // public void handleError(ClientHttpResponse response) throws IOException {
    // HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());
    // if (statusCode == null) {
    // Charset charset = getCharset(response) == null ? StandardCharsets.UTF_8 : getCharset(response);
    // String respBody = new String(getResponseBody(response), charset);
    // this.logError(response.getRawStatusCode(), respBody);
    // throw new UnknownHttpStatusCodeException(response.getRawStatusCode(), response.getStatusText(),
    // response.getHeaders(), respBody.getBytes(charset), charset);
    // }
    // handleError(response, statusCode);
    // }
    //
    // protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
    // Charset charset = getCharset(response) == null ? StandardCharsets.UTF_8 : getCharset(response);
    // String respBody = new String(getResponseBody(response), charset);
    // this.logError(statusCode.value(), respBody);
    // switch (statusCode.series()) {
    // case CLIENT_ERROR:
    // throw new HttpClientErrorException(statusCode, response.getStatusText(),
    // response.getHeaders(), respBody.getBytes(charset), charset);
    // case SERVER_ERROR:
    // throw new HttpServerErrorException(statusCode, response.getStatusText(),
    // response.getHeaders(), respBody.getBytes(charset), charset);
    // default:
    // throw new UnknownHttpStatusCodeException(statusCode.value(), response.getStatusText(),
    // response.getHeaders(), respBody.getBytes(charset), charset);
    // }
    // }
    //
    // private void logError(int statusCode, String respBody) {
    // log.error("http 请求出错, http code : {}, response body : {}", statusCode, respBody);
    // }
    // }

}