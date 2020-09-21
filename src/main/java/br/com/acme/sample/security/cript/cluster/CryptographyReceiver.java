package br.com.acme.sample.security.cript.cluster;

import br.com.acme.sample.security.cript.crypto.CryptographyUtil;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class CryptographyReceiver extends ReceiverAdapter {

    public static final Logger logger = LoggerFactory.getLogger(CryptographyReceiver.class);

    private JChannel channel;

    public CryptographyReceiver(JChannel channel) {
        this.channel = channel;
    }

    private boolean isReceiverAndSenderTheSame(Message msg) {
        return msg.getSrc().equals(msg.getSrc());
    }

    @Override
    public void receive(Message msg) {
        if(isReceiverAndSenderTheSame(msg))
            return;

        if(msg.getObject() instanceof HashMap) {
            // FIXME Ignirar caso o receptor seja o mesmo que enviou
            logger.info("Recebendo chaves publicas e privadas");
            CryptographyUtil.setKeyVault(msg.getObject());

        } else if("GIVE-ME-CERTS".equals(msg.getObject())) {
            try {
                logger.info("Enviando chaves publicas e privadas");
                channel.send(ClusterMessageFactory.getSendKeysToNode(msg.getSrc()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
