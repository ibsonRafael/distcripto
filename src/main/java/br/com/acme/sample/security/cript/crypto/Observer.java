package br.com.acme.sample.security.cript.crypto;


abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
