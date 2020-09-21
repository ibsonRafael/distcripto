package br.com.acme.sample.security.cript.cluster;

import br.com.acme.sample.security.cript.crypto.CryptographyUtil;
import org.jgroups.Address;
import org.jgroups.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClusterMessageFactory {
    public static final Logger logger = LoggerFactory.getLogger(ClusterMessageFactory.class);
    public static Message getKeysFromNode(Address node) {
        logger.info("Pedindo chaves publicas e privadas para o node " + node.toString());
        Message m = new Message();
        m.setObject("GIVE-ME-CERTS");
        m.setDest(node);
        return  m;
    }
    public static Message getSendKeysToNode(Address node) {
        logger.info("Enviando chaves publicas e privadas para o node " + node.toString());
        Message m = new Message();
        m.setObject(CryptographyUtil.getKeyVault());
        m.setDest(node);
        return  m;
    }

    public static Message getSendKeysToAll() {
        logger.info("Enviando chaves publicas e privadas para o cluster");
        Message m = new Message();
        m.setObject(CryptographyUtil.getKeyVault());
        m.setDest(null);
        return  m;
    }
}
