package com.zhixu.core.config;

/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */
public class DataSourceProperty {

    private String host;

    private Integer port;

    private String username;

    private String password;


    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
