package br.com.acme.sample.encriptedservice;

import br.com.acme.sample.security.cript.crypto.CryptographyUtil;
import br.com.acme.sample.security.cript.crypto.EncriptedPingGeneratorObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Base64;

@RestController()
public class XablauController {
    public static final Logger logger = LoggerFactory.getLogger(EncriptedPingGeneratorObserver.class);

    @Autowired
    Environment environment;

    @PostMapping("/xablau")
    public String doWhatEver( @RequestBody String entrada) {
        logger.info("PAYLOAD:" + entrada);
        String resposta = null;
        try {
            resposta = "SERVICO 1 (" + InetAddress.getLocalHost().getHostName() +") Recebeu:" +  entrada;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if("PING".equalsIgnoreCase(entrada))
            return "pong";


        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://" + environment.getProperty("upstream.hostname")
                + ":" + environment.getProperty("upstream.port");

        HttpEntity<String> requestEntity = new HttpEntity<>("expected body");
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "pubkey", HttpMethod.OPTIONS, requestEntity, String.class);

        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Criptografando paylaod com chave da UpStream");
            String keybody = responseEntity.getBody().replaceAll("\n", "")
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "");
            byte[] key = Base64.getDecoder().decode(keybody.getBytes());
            try {
                byte[] encripted = CryptographyUtil.encrypt(key, entrada.getBytes());
                String b64Encripted = new String(Base64.getEncoder().encode(encripted));

                logger.info("Envinado payload para dependencia UpStream");
                HttpEntity<String> encPayload= new HttpEntity<>(b64Encripted);
                ResponseEntity<String> returnedEntity = restTemplate.exchange(baseUrl + "whatever", HttpMethod.POST, encPayload, String.class);

                if(returnedEntity.getStatusCode().equals(HttpStatus.OK)){
                    resposta += "\n" +  returnedEntity.getBody();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "" + resposta;
    }

    @PostMapping("/whatever")
    public String doWhatEverItsHaveToDo( @RequestBody String entrada) {
        logger.info("PAYLOAD:" + entrada);
        String resposta = null;
        try {
            resposta = "SERVICO 2 (" + InetAddress.getLocalHost().getHostName() +") Recebeu:" +  entrada;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return resposta;
    }

}
