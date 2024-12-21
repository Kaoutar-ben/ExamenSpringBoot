package com.kaoutar.dossiermedicalservice.Model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Medical Record")
public class MedicalRecord {
    @ApiModelProperty(notes= "Identifiant du dossier medical")
    private int id;

    @ApiModelProperty(notes = "Identifiant du patient")
    private int patientId;

    @ApiModelProperty(notes ="Identifiant du medecin")
    private String practicienId;

    @ApiModelProperty(notes = "contenu du dossier")
    private String content;

    public MedicalRecord() {}

    public MedicalRecord(int id, int patientId, String practicienId, String content) {
        this.id = id;
        this.patientId = patientId;
        this.practicienId = practicienId;
        this.content = content;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPatientId() {
        return patientId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    public String getPracticienId() {
        return practicienId;
    }
    public void setPracticienId(String practicienId) {
        this.practicienId = practicienId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    @Override
    public String toString() {
        return "MedicalRecord{" + "id=" + id + ", patientId=" + patientId + ", practicienId=" + practicienId + ", content=" + content + '}';
    }

}
