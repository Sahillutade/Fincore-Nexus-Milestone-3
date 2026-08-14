package com.infosys.finbank.customerservice.dto;

public class LoginResponse {

    private String message;
    private Long custid;
    private String fullName;
    private String email;
    private String token;
    public LoginResponse() {
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Long getCustid() {
        return custid;
    }
    public void setCustid(Long custid) {
        this.custid = custid;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public LoginResponse(String message, Long custid, String fullName, String email, String token) {
        this.message = message;
        this.custid = custid;
        this.fullName = fullName;
        this.email = email;
        this.token = token;
    }

}
