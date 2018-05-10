package net.erchen.billomat.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import static java.util.Collections.singletonList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@Slf4j
@SuppressWarnings("WeakerAccess")
public class BillomatService {

    private final String apiKey;

    @Getter(AccessLevel.PACKAGE)
    private final String baseUrl;

    @Getter(AccessLevel.PACKAGE)
    private RestTemplate restTemplate;


    public BillomatService(String billomatId, String apiKey) {
        this.baseUrl = String.format("https://%s.billomat.net/api/", billomatId);
        this.apiKey = apiKey;

        this.restTemplate = new RestTemplate(singletonList(jsonMessageConverter()));
        this.restTemplate.setRequestFactory(requestFactory());

        restTemplate.setInterceptors(singletonList((httpRequest, bytes, clientHttpRequestExecution) -> {
            httpRequest.getHeaders().setAccept(singletonList(APPLICATION_JSON_UTF8));
            httpRequest.getHeaders().add("X-BillomatApiKey", this.apiKey);
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        }));

    }

    private MappingJackson2HttpMessageConverter jsonMessageConverter() {
        return new MappingJackson2HttpMessageConverter(objectMapper());
    }

    private ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());
        return objectMapper;
    }

    private ClientHttpRequestFactory requestFactory() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10 * 1000);
        simpleClientHttpRequestFactory.setReadTimeout(30 * 1000);
        return simpleClientHttpRequestFactory;
    }

    public IncomingsBuilder getIncomings() {
        return new IncomingsBuilder(this);
    }
}


