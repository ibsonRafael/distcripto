package br.com.acme.sample.security.cript.servlet.filter;

import br.com.acme.sample.security.cript.MyHttpServletRequestWrapper;
import br.com.acme.sample.security.cript.crypto.CryptographyUtil;
import br.com.acme.sample.security.cript.crypto.KeyChangeObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Base64;
import java.util.stream.Collectors;

@Component
public class EncriptionIncomeFilter  implements Filter {
    public static final Logger logger = LoggerFactory.getLogger(EncriptionIncomeFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        logger.info(req.getRequestURI());
//        if("GET".equalsIgnoreCase(req.getMethod()) && "/pubkey".equalsIgnoreCase(req.getRequestURI())) {
//            String newContent = "-----BEGIN PUBLIC KEY-----\n";
//            newContent += CryptographyUtil.getHumanReadblePublicKey();
//            newContent += "\n-----END PUBLIC KEY-----\n";
//            res.setHeader("Content-Type", "text/plain");
////            res.setContentLength(newContent.length());
//            res.setStatus(200);
//            PrintWriter pw = res.getWriter();
//            pw.write(newContent);
//            pw.close();
//            return;
//        }

        res.setHeader("X-ResponseFrom", InetAddress.getLocalHost().getHostName());
        req = doBeforeProcessing(req, res);

        Throwable problem = null;
        try {
            filterChain.doFilter(req, res);
        } catch (Throwable t) {
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(req, res);
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
        }

    }



    private MyHttpServletRequestWrapper doBeforeProcessing(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        if(!"OPTIONS".equalsIgnoreCase(req.getMethod())) {
            byte[] data = Base64.getDecoder().decode(body.getBytes());
            byte[] decryptedData = new byte[0];
            try {
                decryptedData = CryptographyUtil.decrypt(data);
                return new MyHttpServletRequestWrapper(req, decryptedData);
            } catch (Exception e) {
                e.printStackTrace();
                return new MyHttpServletRequestWrapper(req, body.getBytes());
            }
        }
        return new MyHttpServletRequestWrapper(req, body.getBytes());
    }

    private void doAfterProcessing(HttpServletRequest req, HttpServletResponse newres) {
        //PubKeyResponseWrapper newres = new PubKeyResponseWrapper((HttpServletResponse) res);
        if("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            logger.info(newres.toString());
        } else {
            newres.addHeader("Content-Encoding", "rsa1024");
//            res = newres;
        }
    }

    @Override
    public void destroy() {

    }
}
