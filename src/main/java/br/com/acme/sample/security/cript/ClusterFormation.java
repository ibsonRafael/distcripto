package br.com.acme.sample.security.cript;

import br.com.acme.sample.security.cript.cluster.CryptographyReceiver;
import br.com.acme.sample.security.cript.cluster.ClusterMessageFactory;
import br.com.acme.sample.security.cript.crypto.CryptographyUtil;
import br.com.acme.sample.security.cript.crypto.KeyChangeObserver;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        channel = new JChannel("tcp.xml");
        channel.connect("fullcript");
        channel.setReceiver(new CryptographyReceiver(channel));

        Address master=channel.getView().getMembers().get(0);
        if(master.equals(channel.getAddress())) {
            logger.info("Eu reclamo esse cluster em mey nome, e de minhas gerações futuras!");
            logger.info("Dado que é meu torno-me o mestre, curvem-se perante seu líder");
            CryptographyUtil.generateKeys();
            new KeyChangeObserver(CryptographyUtil.cryptographyStateSubjet, channel);
            CryptographyUtil.scheduleRotateKey(60000);
        } else {
            logger.info("Não sou mestre! Preciso das chaves");
            logger.info("Pedindo chaves publicas e privadas");
            channel.send(ClusterMessageFactory.getKeysFromNode(master));
        }
    }


    private static void gerarPing() {
//        String payload ="PING";
//        logger.info(payload);
//        byte[] cpayload = new byte[0];
//        try {
//            cpayload = Base64.getEncoder().encode(
//                    EncriptionIncomeFilter.encrypt(keyVault.get("publicKey"), payload.getBytes())
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String b64cpayload = new String(cpayload);
//
//        logger.info( b64cpayload );
//        cpayload = Base64.getDecoder().decode(b64cpayload.getBytes());
//
//        byte[] dpayload = new byte[0];
//        try {
//            dpayload = EncriptionIncomeFilter.decrypt(keyVault.get("privateKey"), cpayload);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        logger.info( new String(dpayload) );
    }



}
