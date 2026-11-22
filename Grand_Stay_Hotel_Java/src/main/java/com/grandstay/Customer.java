
package com.grandstay;

public class Customer {

    private int customerId;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private String city;

    public Customer(int customerId, String fullName, String email,
                    String phone, String password, String city) {

        this.customerId = customerId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.city = city;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getCity() {
        return city;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
