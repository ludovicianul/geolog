package com.insidecoding.geolog.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LogFailedEntity {

    @Id
    private String ip;

    private String city;

    private String country;

    private String org;

    private long attempts = 1;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public long getAttempts() {
        return attempts;
    }

    public void setAttempts(long attempts) {
        this.attempts = attempts;
    }

    public void increaseAttempts() {
        this.attempts++;
    }

    @Override
    public String toString() {
        return "LogFailedEntity [ip=" + ip + ", city=" + city + ", country=" + country + ", org=" + org + ", attempts="
                + attempts + "]";
    }

}
