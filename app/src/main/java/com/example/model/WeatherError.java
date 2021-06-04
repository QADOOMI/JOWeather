package com.example.model;

public class WeatherError {

    private String message;
    private boolean local;

    public WeatherError(String message, boolean local) {
        this.message = message;
        this.local = local;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }
}
