package br.com.acme.sample.security.cript;

import javax.servlet.ServletInputStream;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private byte[] data;
    HttpServletRequest req;
    public MyHttpServletRequestWrapper(HttpServletRequest request, byte[] data) {
        super(request);
        req = request;
        this.data = data;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        String enc = req.getCharacterEncoding();
        if(enc == null)
            enc = "UTF-8";
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data), enc));
    }

    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStreamWrapper(data);
    }

}
