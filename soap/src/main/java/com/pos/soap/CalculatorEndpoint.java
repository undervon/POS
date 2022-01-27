package com.pos.soap;

import lombok.extern.log4j.Log4j2;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.pos.com.calculator.AddRequest;
import soap.pos.com.calculator.AddResponse;
import soap.pos.com.calculator.DecreasedRequest;
import soap.pos.com.calculator.DecreasedResponse;
import soap.pos.com.calculator.DivisionRequest;
import soap.pos.com.calculator.DivisionResponse;
import soap.pos.com.calculator.MultiplicationRequest;
import soap.pos.com.calculator.MultiplicationResponse;


@Endpoint
@Log4j2
public class CalculatorEndpoint {

    private static final String NAMESPACE_URI = "http://com.pos.soap/Calculator";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addRequest")
    @ResponsePayload
    public AddResponse add(@RequestPayload AddRequest addRequest) {
        log.info("add");

        AddResponse addResponse = new AddResponse();

        addResponse.setResult(addRequest.getParam1().add(addRequest.getParam2()));

        return addResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "decreasedRequest")
    @ResponsePayload
    public DecreasedResponse decreased(@RequestPayload DecreasedRequest decreasedRequest) {
        log.info("decreased");

        DecreasedResponse decreasedResponse = new DecreasedResponse();

        decreasedResponse.setResult(decreasedRequest.getParam1().subtract(decreasedRequest.getParam2()));

        return decreasedResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "multiplicationRequest")
    @ResponsePayload
    public MultiplicationResponse multiplication(@RequestPayload MultiplicationRequest multiplicationRequest) {
        log.info("multiplication");

        MultiplicationResponse multiplicationResponse = new MultiplicationResponse();

        multiplicationResponse.setResult(multiplicationRequest.getParam1().multiply(multiplicationRequest.getParam2()));

        return multiplicationResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "divisionRequest")
    @ResponsePayload
    public DivisionResponse division(@RequestPayload DivisionRequest divisionRequest) {
        log.info("division");

        DivisionResponse divisionResponse = new DivisionResponse();

        divisionResponse.setResult(divisionRequest.getParam1().divide(divisionRequest.getParam2()));

        return divisionResponse;
    }
}
