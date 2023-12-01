package com.example.itutor.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name="researcher")
public class Researcher extends User implements Serializable {

    // Attributes
    // Q: should the researchers be actual researchers, for example working for a company
    // or are these just normal people with the ability to see the statistics on the site

    // Affiliation being the institution / company the researcher works for
    private String researcherAffiliation;

    // Qualification being the knowledge field of the researcher
    private String researcherQualification;

    public String getResearcherAffiliation() {
        return researcherAffiliation;
    }

    public void setResearcherAffiliation(String affiliation) {
        this.researcherAffiliation = affiliation;
    }

    public String getResearcherQualification() {
        return researcherQualification;
    }

    public void setResearcherQualification(String credentials) {
        this.researcherQualification = credentials;
    }

    @Override
    public String toString() {
        return "Researcher {" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", affiliation=" + researcherAffiliation + ", credentials=" + researcherQualification + '}';
    }

}
