package com.urna.urnacare.domain;

import com.urna.urnacare.enumeration.AppointmentRequestStatus;
import com.urna.urnacare.enumeration.PaymentStatus;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AppointmentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String requestId;

    @ManyToOne
    @JoinColumn(name="patient_id", nullable=false)
    private User patient;

    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    private Doctor doctor;

    @Column
    private String healthIssueDescription;

    @Column(columnDefinition = "DATE")
    private LocalDate desiredDate;
    @Column
    private Byte desiredStartHours;
    @Column
    private Byte desiredEndHours;
    @Column
    private Boolean dateFlexible;
    @Column
    private Boolean hoursFlexible;

    @Column
    @Enumerated(EnumType.STRING)
    private AppointmentRequestStatus requestStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    private String rejectReason;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getHealthIssueDescription() {
        return healthIssueDescription;
    }

    public void setHealthIssueDescription(String healthIssueDescription) {
        this.healthIssueDescription = healthIssueDescription;
    }

    public LocalDate getDesiredDate() {
        return desiredDate;
    }

    public void setDesiredDate(LocalDate desiredDate) {
        this.desiredDate = desiredDate;
    }

    public Byte getDesiredStartHours() {
        return desiredStartHours;
    }

    public void setDesiredStartHours(Byte desiredStartHours) {
        this.desiredStartHours = desiredStartHours;
    }

    public Byte getDesiredEndHours() {
        return desiredEndHours;
    }

    public void setDesiredEndHours(Byte desiredEndHours) {
        this.desiredEndHours = desiredEndHours;
    }

    public Boolean getDateFlexible() {
        return dateFlexible;
    }

    public void setDateFlexible(Boolean dateFlexible) {
        this.dateFlexible = dateFlexible;
    }

    public Boolean getHoursFlexible() {
        return hoursFlexible;
    }

    public void setHoursFlexible(Boolean hoursFlexible) {
        this.hoursFlexible = hoursFlexible;
    }

    public AppointmentRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(AppointmentRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "AppointmentRequest{" +
                "id=" + id +
                ", requestId='" + requestId + '\'' +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", healthIssueDescription='" + healthIssueDescription + '\'' +
                ", desiredDate=" + desiredDate +
                ", desiredStartHours=" + desiredStartHours +
                ", desiredEndHours=" + desiredEndHours +
                ", dateFlexible=" + dateFlexible +
                ", hoursFlexible=" + hoursFlexible +
                ", requestStatus=" + requestStatus +
                ", createdAt=" + createdAt +
                ", rejectReason='" + rejectReason + '\'' +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
