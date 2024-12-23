package org.example.hotelmanagementsystemproject.Rmi.Models;

import java.io.Serializable;

public class Chambre implements Serializable {
    private Integer id;
    private String description;
    private int prixNuit;
    private Integer createBy;
    private double surface;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrixNuit() {
        return prixNuit;
    }

    public void setPrixNuit(Integer prixNuit) {
        this.prixNuit = prixNuit;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(float surface) {
        this.surface = surface;
    }

    public Chambre(String description,
                   int prixNuit,
                   double surface,
                   Integer createBy) {
        this.description = description;
        this.prixNuit = prixNuit;
        this.surface = surface;
        this.createBy = createBy;
    }

    public Chambre() {
    }

    public Chambre(Integer id,
                   String description,
                   int prixNuit,
                   Integer createBy,
                   float surface) {
        this.id = id;
        this.description = description;
        this.prixNuit = prixNuit;
        this.createBy = createBy;
        this.surface = surface;
    }
}
