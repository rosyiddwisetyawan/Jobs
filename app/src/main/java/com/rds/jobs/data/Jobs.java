package com.rds.jobs.data;

/**
 * Created by Team 2 on 11/9/2018.
 */

public class Jobs {

    private String id;
    private String image;
    private String company;
    private String date;
    private String position;
    private String jobtype;
    private String city;
    private String url;
    private String description;
    private String responsibility;
    private String requirement;

    private String matauang;
    private String gajiawal;
    private String gajiakhir;

    public Jobs(String id,String image, String company, String date, String position, String jobtype,
                String city, String url, String description, String responsibility, String requirement,
                String matauang, String gajiawal, String gajiakhir) {
        this.id = id;
        this.image = image;
        this.company = company;
        this.date = date;
        this.position = position;
        this.jobtype = jobtype;
        this.city = city;
        this.url = url;
        this.description = description;
        this.responsibility = responsibility;
        this.requirement = requirement;
        this.matauang = matauang;
        this.gajiawal = gajiawal;
        this.gajiakhir = gajiakhir;

    }

    public Jobs() {

    }

    public String getMatauang() {
        return matauang;
    }

    public void setMatauang(String matauang) {
        this.matauang = matauang;
    }

    public String getGajiawal() {
        return gajiawal;
    }

    public void setGajiawal(String gajiawal) {
        this.gajiawal = gajiawal;
    }

    public String getGajiakhir() {
        return gajiakhir;
    }

    public void setGajiakhir(String gajiakhir) {
        this.gajiakhir = gajiakhir;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

}
