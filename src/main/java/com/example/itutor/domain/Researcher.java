package com.example.itutor.domain;

import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
public class Researcher extends User implements Serializable {

    // Affiliation being the institution / company the researcher works for
    private String affiliation;

    // Qualification being the knowledge field of the researcher
    private String qualification;

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String researcherAffiliation) {
        this.affiliation = researcherAffiliation;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String researcherQualification) {
        this.qualification = researcherQualification;
    }

    @Override
    public String toString() {
        return "Researcher {" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", affiliation=" + affiliation + ", credentials=" + qualification + '}';
    }

}
