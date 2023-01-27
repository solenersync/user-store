package com.solenersync.userstore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/users")
public class UserstoreController {

    @GetMapping("/user/{id}")
    public String index(@PathVariable Integer id) {
        log.debug("Retrieving user {} ",id);
        return "Userstore response userid=" + id;
    }

    @PostMapping("/user")
    public String user(@RequestBody String email) {
        log.debug("Returning user id {} ",email);
        return "Hello there from userstore - this is your id 10001";
    }
}
