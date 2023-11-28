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

    // Credentials being the qualification and the knowledge field of the researcher
    private String researcherCredentials;

    public String getResearcherAffiliation() {
        return researcherAffiliation;
    }

    public void setResearcherAffiliation(String affiliation) {
        this.researcherAffiliation = affiliation;
    }

    public String getResearcherCredentials() {
        return researcherCredentials;
    }

    public void setResearcherCredentials(String credentials) {
        this.researcherCredentials = credentials;
    }

    @Override
    public String toString() {
        return "Student {" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", affiliation=" + researcherAffiliation + ", credentials=" + researcherCredentials + '}';
    }

}
