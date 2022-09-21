package africa.semicolon.lumexpress.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
    @GetMapping("/bye")
    public String Welcome(){
        return "Its a new line";
    }
}

