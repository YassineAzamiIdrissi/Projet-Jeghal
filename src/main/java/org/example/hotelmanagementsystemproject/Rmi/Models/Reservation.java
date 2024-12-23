package org.example.hotelmanagementsystemproject.Rmi.Models;

import java.io.Serializable;

public class Reservation implements Serializable {
    private Integer id;
    private Integer clientId;
    private Integer chambreId;
    private boolean accepted;
    private boolean answered;
    private int nbrPersonnes;
    private String from;
    private int nbrDays;
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Reservation() {
    }

    public Reservation(Integer clientId, Integer chambreId, boolean accepted, boolean answered, int nbrPersonnes, String from, int nbrDays) {
        this.clientId = clientId;
        this.chambreId = chambreId;
        this.accepted = accepted;
        this.answered = answered;
        this.nbrPersonnes = nbrPersonnes;
        this.from = from;
        this.nbrDays = nbrDays;
    }

    public Reservation(Integer id, Integer clientId, Integer chambreId, boolean accepted, boolean answered, String from, int nbrPersonnes, int nbrDays) {
        this.id = id;
        this.clientId = clientId;
        this.chambreId = chambreId;
        this.accepted = accepted;
        this.answered = answered;
        this.from = from;
        this.nbrPersonnes = nbrPersonnes;
        this.nbrDays = nbrDays;
    }

    public int getNbrPersonnes() {
        return nbrPersonnes;
    }

    public void setNbrPersonnes(int nbrPersonnes) {
        this.nbrPersonnes = nbrPersonnes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Integer getChambreId() {
        return chambreId;
    }

    public void setChambreId(Integer chambreId) {
        this.chambreId = chambreId;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getNbrDays() {
        return nbrDays;
    }

    public void setNbrDays(int nbrDays) {
        this.nbrDays = nbrDays;
    }
}
