package edu.neu.csye7374.smartjob.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("EMPLOYER")
public class Employer extends User {
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobPost> jobPosts;
    
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
    
    /**
     * Builder class for Employer
     */
    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String companyName;
        private String industry;
        private String companySize;
        private String companyWebsite;
        private String companyDescription;
        
        public Builder() {
        }
        
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        
        public Builder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }
        
        public Builder industry(String industry) {
            this.industry = industry;
            return this;
        }
        
        public Builder companySize(String companySize) {
            this.companySize = companySize;
            return this;
        }
        
        public Builder companyWebsite(String companyWebsite) {
            this.companyWebsite = companyWebsite;
            return this;
        }
        
        public Builder companyDescription(String companyDescription) {
            this.companyDescription = companyDescription;
            return this;
        }
        
        public Employer build() {
            Employer employer = new Employer();
            employer.setFirstName(firstName);
            employer.setLastName(lastName);
            employer.setEmail(email);
            employer.setPassword(password);
            employer.setCompanyName(companyName);
            employer.setIndustry(industry);
            employer.setCompanySize(companySize);
            employer.setCompanyWebsite(companyWebsite);
            employer.setCompanyDescription(companyDescription);
            return employer;
        }
    }
} 