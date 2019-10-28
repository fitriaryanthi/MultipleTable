package com.example.dosen.Model;

public class Dosen {
    private long id;
    private String nip;
    private String name;
    private String phone;
    private String email;

    public Dosen(long id, String nip, String name, String phone, String email){
        this.id = id;
        this.name = name;
        this.nip = nip;
        this.phone = phone;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
