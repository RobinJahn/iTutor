package com.example.itutor.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name="student")
public class Expert extends User implements Serializable {

        //Attributes
        private String expertise;

        //Getters + Setters
        public String getExpertise() {
            return expertise;
        }

        public void setExpertise(String expertise) {
            this.expertise = expertise;
        }
}

