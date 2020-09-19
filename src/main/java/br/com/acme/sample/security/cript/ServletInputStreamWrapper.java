package br.com.acme.sample.security.cript;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;

public class ServletInputStreamWrapper extends ServletInputStream {

    private byte[] data;
    private int idx = 0;
    ServletInputStreamWrapper(byte[] data) {
        if(data == null)
            data = new byte[0];
        this.data = data;
    }
    @Override
    public int read() throws IOException {
        if(idx == data.length)
            return -1;
        return data[idx++];
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {

    }
}