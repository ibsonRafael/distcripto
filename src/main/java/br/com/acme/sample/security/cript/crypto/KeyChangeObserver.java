package br.com.acme.sample.security.cript.crypto;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class KeyChangeObserver extends Observer {
    public static final Logger logger = LoggerFactory.getLogger(KeyChangeObserver.class);

    JChannel channel;

    public KeyChangeObserver(Subject subject, JChannel channel) {
        this.subject = subject;
        this.channel = channel;
        this.subject.add(this);
    }

    @Override
    public void update() {
        logger.info("Key changed!");
        logger.info("Enviando chaves publicas e privadas");
        Message m = new Message();
        m.setObject(CryptographyUtil.getKeyVault());
        m.setDest(null);
        try {
            channel.send(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
