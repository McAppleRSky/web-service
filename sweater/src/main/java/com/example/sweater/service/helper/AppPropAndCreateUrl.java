package com.example.sweater.service.helper;

import java.net.MalformedURLException;
import java.net.URL;

public class AppPropAndCreateUrl {
    private String tProtocol;
    private String hostname;
    private Integer port;

    public String gettProtocol() {
        return tProtocol;
    }

    public void settProtocol(String tProtocol) {
        this.tProtocol = tProtocol;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public AppPropAndCreateUrl() {
    }

    public AppPropAndCreateUrl(String tProtocol, String hostname, Integer port) {
        this.tProtocol = tProtocol;
        this.hostname = hostname;
        this.port = port;
    }

    public URL createUrl(String activation, String activationCode) {
        URL result = null;
        try {
            result = new URL(tProtocol, hostname, port,  "/" + activation + "/" + activationCode);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
