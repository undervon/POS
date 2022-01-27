package com.soap.student;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();

        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean<>(servlet, "/student/*");
    }

    @Bean(name = "student")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema studentSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("StudentPort");
        result.setLocationUri("/student");
        result.setTargetNamespace("http://com.soap.student/student");
        result.setSchema(studentSchema);

        return result;
    }

    @Bean
    public XsdSchema studentSchema() {
        return new SimpleXsdSchema(new ClassPathResource("Student.xsd"));
    }
}
