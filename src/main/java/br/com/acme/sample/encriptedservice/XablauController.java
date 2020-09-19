package br.com.acme.sample.encriptedservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class XablauController {

    @PostMapping("/xablau")
    public String doWhatEver( @RequestBody String entrada) {
        if("PING".equalsIgnoreCase(entrada))
            return "pong";

        return "error!";
    }

}
