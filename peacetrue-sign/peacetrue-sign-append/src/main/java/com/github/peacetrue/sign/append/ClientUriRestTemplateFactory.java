//package com.github.peacetrue.sign.append;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.peacetrue.sign.UriApp;
//import com.github.peacetrue.spring.web.client.UriRestTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
//
//import java.util.Collections;
//
///**
// * a RestTemplateFactory for Consumer
// *
// * @author xiayx
// */
//public class ClientUriRestTemplateFactory implements UriRestTemplateFactory {
//
//    private ObjectMapper objectMapper;
//
//    @Override
//    public UriRestTemplate getUriRestTemplate(UriApp app) {
//        BodySignService service = new BodySignService();
//        service.setObjectMapper(objectMapper);
//
////        BodySignHttpMessageConverter messageConverter = new BodySignHttpMessageConverter(objectMapper);
////        messageConverter.setBodySignService(service);
////        messageConverter.setHttpMessageConverter(new MappingJackson2HttpMessageConverter(objectMapper));
//
//        AllEncompassingFormHttpMessageConverter converter = new AllEncompassingFormHttpMessageConverter();
//
//
//        return new UriRestTemplate(app.getUri(), Collections.singletonList(converter));
//    }
//
//
//    @Autowired
//    public void setObjectMapper(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//
//}
