package dev.mkuwan.spring.hateoas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class HateoasGreetingController {

    private static final Logger logger = LoggerFactory.getLogger(HateoasGreetingController.class);

    private static final String TEMPLATE = "Hello, %s!!!";

    @RequestMapping("/hateoas-greeting")
    public HttpEntity<HateoasGreeting> greeting(@RequestParam(value = "name", defaultValue = "World") String name){
        HateoasGreeting greeting = new HateoasGreeting(String.format(TEMPLATE, name));

        greeting.add(linkTo(methodOn(HateoasGreetingController.class)
                .greeting(name)).withSelfRel());

        logger.info(String.format("Hateoas Greeting content is %s", greeting.getContent()));

        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }

}
