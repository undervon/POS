package com.pos.bookstorejwt.config;

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

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet result = new MessageDispatcherServlet();

        result.setApplicationContext(applicationContext);
        result.setTransformWsdlLocations(true);

        return new ServletRegistrationBean(result,
                "/login/*",
                "/register/*",
                "/token/*",
                "/addUser/*",
                "/deleteUser/*",
                "/editPasswordUser/*",
                "/editRoleUser/*");
    }

    @Bean(name = "login")
    public DefaultWsdl11Definition defaultWsdl11DefinitionLogin(XsdSchema loginSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("LoginPort");
        result.setLocationUri("/login");
        result.setTargetNamespace("http://com.pos.bookstorejwt/login");
        result.setSchema(loginSchema);

        return result;
    }

    @Bean
    public XsdSchema loginSchema() {
        return new SimpleXsdSchema(new ClassPathResource("Login.xsd"));
    }

    @Bean(name = "register")
    public DefaultWsdl11Definition defaultWsdl11DefinitionRegister(XsdSchema registerSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("RegisterPort");
        result.setLocationUri("/register");
        result.setTargetNamespace("http://com.pos.bookstorejwt/register");
        result.setSchema(registerSchema);

        return result;
    }

    @Bean
    public XsdSchema registerSchema() {
        return new SimpleXsdSchema(new ClassPathResource("Register.xsd"));
    }

    @Bean(name = "token")
    public DefaultWsdl11Definition defaultWsdl11DefinitionToken(XsdSchema tokenSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("TokenPort");
        result.setLocationUri("/token");
        result.setTargetNamespace("http://com.pos.bookstorejwt/token");
        result.setSchema(tokenSchema);

        return result;
    }

    @Bean
    public XsdSchema tokenSchema() {
        return new SimpleXsdSchema(new ClassPathResource("Token.xsd"));
    }

    @Bean(name = "addUser")
    public DefaultWsdl11Definition defaultWsdl11DefinitionAddUser(XsdSchema addUserSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("AddUserPort");
        result.setLocationUri("/addUser");
        result.setTargetNamespace("http://com.pos.bookstorejwt/addUser");
        result.setSchema(addUserSchema);

        return result;
    }

    @Bean
    public XsdSchema addUserSchema() {
        return new SimpleXsdSchema(new ClassPathResource("AddUser.xsd"));
    }

    @Bean(name = "deleteUser")
    public DefaultWsdl11Definition defaultWsdl11DefinitionDeleteUser(XsdSchema deleteUserSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("DeleteUserPort");
        result.setLocationUri("/deleteUser");
        result.setTargetNamespace("http://com.pos.bookstorejwt/deleteUser");
        result.setSchema(deleteUserSchema);

        return result;
    }

    @Bean
    public XsdSchema deleteUserSchema() {
        return new SimpleXsdSchema(new ClassPathResource("DeleteUser.xsd"));
    }

    @Bean(name = "editPasswordUser")
    public DefaultWsdl11Definition defaultWsdl11DefinitionEditPasswordUser(XsdSchema editPasswordUserSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("EditPasswordUserPort");
        result.setLocationUri("/editPasswordUser");
        result.setTargetNamespace("http://com.pos.bookstorejwt/editPasswordUser");
        result.setSchema(editPasswordUserSchema);

        return result;
    }

    @Bean
    public XsdSchema editPasswordUserSchema() {
        return new SimpleXsdSchema(new ClassPathResource("EditPasswordUser.xsd"));
    }

    @Bean(name = "editRoleUser")
    public DefaultWsdl11Definition defaultWsdl11DefinitionEditRoleUser(XsdSchema editRoleUserSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("EditRoleUserPort");
        result.setLocationUri("/editRoleUser");
        result.setTargetNamespace("http://com.pos.bookstorejwt/editRoleUser");
        result.setSchema(editRoleUserSchema);

        return result;
    }

    @Bean
    public XsdSchema editRoleUserSchema() {
        return new SimpleXsdSchema(new ClassPathResource("EditRoleUser.xsd"));
    }
}

