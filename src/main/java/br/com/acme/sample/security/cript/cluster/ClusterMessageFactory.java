package br.com.acme.sample.security.cript.cluster;

import br.com.acme.sample.security.cript.crypto.CryptographyUtil;
import org.jgroups.Address;
import org.jgroups.Message;

public class ClusterMessageFactory {
    public static Message getKeysFromNode(Address node) {
        Message m = new Message();
        m.setObject("GIVE-ME-CERTS");
        m.setDest(node);
        return  m;
    }
    public static Message getSendKeysToNode(Address node) {
        Message m = new Message();
        m.setObject(CryptographyUtil.getKeyVault());
        m.setDest(node);
        return  m;
    }

    public static Message getSendKeysToAll() {
        Message m = new Message();
        m.setObject(CryptographyUtil.getKeyVault());
        m.setDest(null);
        return  m;
    }
}
