package br.com.acme.sample.security.cript.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class EncriptedPingGeneratorObserver extends Observer {
    public static final Logger logger = LoggerFactory.getLogger(EncriptedPingGeneratorObserver.class);
    public EncriptedPingGeneratorObserver(Subject subject) {
        this.subject = subject;
        this.subject.add(this);
    }

    @Override
    public void update() {
        String payload ="PING";
        logger.info(payload);
        byte[] cpayload = new byte[0];
        try {
            cpayload = Base64.getEncoder().encode(
                    CryptographyUtil.encrypt(payload.getBytes())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        String b64cpayload = new String(cpayload);

        logger.info( b64cpayload );
        cpayload = Base64.getDecoder().decode(b64cpayload.getBytes());

        byte[] dpayload = new byte[0];
        try {
            dpayload = CryptographyUtil.decrypt(cpayload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info( new String(dpayload) );
    }
}
