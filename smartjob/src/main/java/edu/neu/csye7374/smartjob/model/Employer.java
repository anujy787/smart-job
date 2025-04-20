package edu.neu.csye7374.smartjob.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("EMPLOYER")
public class Employer extends User {
    
    @Column
    private String companyName;
    
    @Column
    private String industry;
    
    @Column
    private String companySize;
    
    @Column
    private String companyWebsite;
    
    @Column(columnDefinition = "TEXT")
    private String companyDescription;

    public Employer() {
        super();
        setRole(UserRole.EMPLOYER);
    }
    
    @Override
    public String getUserType() {
        return "EMPLOYER";
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }
} 