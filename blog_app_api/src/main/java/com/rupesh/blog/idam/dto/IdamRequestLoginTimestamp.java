package com.rupesh.blog.idam.dto;

import java.util.List;

public class IdamRequestLoginTimestamp {

    private List<String> loginId;

    private String timestamp;

    public List<String> getLoginId() {
        return loginId;
    }

    public void setLoginId(List<String> loginId) {
        this.loginId = loginId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}