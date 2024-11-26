package sm.hospitalsm.controller;

import org.antlr.v4.runtime.misc.NotNull;

public class PrescriptionDTO {
    @NotNull
    private long patientId;
    @NotNull
    private String medication;

    private String especifications;

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getEspecifications() {
        return especifications;
    }

    public void setEspecifications(String especifications) {
        this.especifications = especifications;
    }
}
