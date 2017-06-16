package com.cocos.mfcn.cralwer.modals;

public class GrilPageInfo {
    private String status;
    private String mainPhoto;
    private String avatar;
    private String userName;
    private String camScore;
    private String gender;
    private String weight;
    private String height;
    private int age;
    private String city;
    private String Country;
    private String sexualPreference;
    private String maritalStatus;
    private String pets;
    private String aboutMe;

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCamScore() {
        return camScore;
    }

    public void setCamScore(String camScore) {
        this.camScore = camScore;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getSexualPreference() {
        return sexualPreference;
    }

    public void setSexualPreference(String sexualPreference) {
        this.sexualPreference = sexualPreference;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPets() {
        return pets;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @Override
    public String toString() {
        return "GrilPageInfo{" +
                "status='" + status + '\'' +
                ", mainPhoto='" + mainPhoto + '\'' +
                ", avatar='" + avatar + '\'' +
                ", userName='" + userName + '\'' +
                ", camScore='" + camScore + '\'' +
                ", gender='" + gender + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", Country='" + Country + '\'' +
                ", sexualPreference='" + sexualPreference + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", pets='" + pets + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                '}';
    }
}
