package org.example.hotelmanagementsystemproject.Rmi.Models;

import java.io.Serializable;

public class ChambreLine implements Serializable {
    private int id;
    private double surface;
    private int prixNuit;
    private int createdBy;
    private String taken;
    private String descr;

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public ChambreLine(int id, double surface, int prixNuit, String taken, int createdBy, String descr) {
        this.id = id;
        this.surface = surface;
        this.prixNuit = prixNuit;
        this.taken = taken;
        this.createdBy = createdBy;
        this.descr = descr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public int getPrixNuit() {
        return prixNuit;
    }

    public void setPrixNuit(int prixNuit) {
        this.prixNuit = prixNuit;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getTaken() {
        return taken;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }
}

/***
 select chambre.id,chambre.surface,
 chambre.createdByEmp,chambre.prixNuit,reservation.id is not null as isTaken
 from
 chambre left join
 reservation on chambre.id = reservation.chambre_id
 ***/