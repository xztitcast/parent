package com.taotao.admin.web.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Configuration
public class JacksonConfig {

	@Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {

            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString("");
            }

        });
        objectMapper.registerModules(
        		new SimpleModule().addSerializer(Long.class, ToStringSerializer.instance)
        						  .addSerializer(Long.TYPE, ToStringSerializer.instance)
        						  .addSerializer(BigInteger.class, ToStringSerializer.instance)
        						  .addSerializer(BigDecimal.class, ToStringSerializer.instance)
								  .addDeserializer(String.class, new JsonDeserializer<String>() {
									  @Override public String deserialize(JsonParser p, DeserializationContext
									  ctxt) throws IOException, JsonProcessingException { 
										  String value = p.getText(); 
										  return value == null || value.trim().equals("") || value.equals("undefined") ? null : value; 
									}
								  })
		 
        );
        return objectMapper;
    }
}
