package br.com.acme.sample.security.cript;

import br.com.acme.sample.security.cript.crypto.CryptographyUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.stream.Collectors;

@Component
public class EncriptionIncomeFilter  implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        if("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            // Enviar certificados
        } else {
            String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            byte[] data = Base64.getDecoder().decode(body.getBytes());
            byte[] decryptedData = new byte[0];
            try {
                decryptedData = CryptographyUtil.decrypt(data);
                req = new MyHttpServletRequestWrapper(req, decryptedData);
            } catch (Exception e) {
                e.printStackTrace();
                req = new MyHttpServletRequestWrapper(req, body.getBytes());
            }
        }

        filterChain.doFilter(req, servletResponse);
    }

    @Override
    public void destroy() {

    }





//    byte[] decryptedData = decrypt(privateKey, encryptedData);
//
//        System.out.println(new String(decryptedData));
}
