package br.com.acme.sample.security.cript;

import br.com.acme.sample.security.cript.cluster.CryptographyReceiver;
import br.com.acme.sample.security.cript.cluster.ClusterMessageFactory;
import br.com.acme.sample.security.cript.crypto.CryptographyUtil;
import br.com.acme.sample.security.cript.crypto.EncriptedPingGeneratorObserver;
import br.com.acme.sample.security.cript.crypto.KeyChangeObserver;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Base64;

public class ClusterFormation {

    static JChannel channel;

    public static final Logger logger = LoggerFactory.getLogger(ClusterFormation.class);

    public ClusterFormation() {
        try {
            start();
            channel.getState(null, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void start() throws Exception {
        String fileName = "xablau.xml";
        ClassLoader classLoader = getClass().getClassLoader();
//        File file = new File(classLoader.getResource(fileName).getFile());

        channel = new JChannel(classLoader.getResourceAsStream(fileName));
        channel.connect("fullcript");
        channel.setReceiver(new CryptographyReceiver(channel));

        Address master=channel.getView().getMembers().get(0);
        if(master.equals(channel.getAddress())) {
            logger.info("Eu reclamo esse cluster em mey nome, e de minhas gerações futuras!");
            logger.info("Dado que é meu torno-me o mestre, curvem-se perante seu líder");
            CryptographyUtil.generateKeys();
            new KeyChangeObserver(CryptographyUtil.cryptographyStateSubjet, channel);
            new EncriptedPingGeneratorObserver(CryptographyUtil.cryptographyStateSubjet);
            CryptographyUtil.scheduleRotateKey(120000);
        } else {
            logger.info("Não sou mestre! Preciso das chaves");
            logger.info("Pedindo chaves publicas e privadas");
            channel.send(ClusterMessageFactory.getKeysFromNode(master));
        }
    }




}
