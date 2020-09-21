package br.com.acme.sample.security.cript.servlet;

import br.com.acme.sample.security.cript.crypto.CryptographyUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "certificateDicoveryServlet",
        urlPatterns = {
                "/pubkey"
        }
)
public class CertificateDicoveryServlet extends HttpServlet {
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Content-Type", "text/plain");
        res.setHeader("Access-Control-Allow-Origin", "http://localhost:9090");
        res.setHeader("Access-Control-Allow-Methods", "OPTIONS");

        res.setHeader("Cache-Control", "private, max-age=< 15, must-revalidate");

        String newContent = "-----BEGIN PUBLIC KEY-----\n";
        newContent += CryptographyUtil.getHumanReadblePublicKey();
        newContent += "\n-----END PUBLIC KEY-----\n";

        res.setStatus(200);
        PrintWriter pw = res.getWriter();
        pw.write(newContent);
        pw.close();
        return;
    }
}
