package com.example.youthcenter.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reports_for_projects")
@AllArgsConstructor
@Setter
@Getter
public class VolunteerProjectReport extends Report{

    @OneToOne
    @JoinColumn
    private VolunteerProject volunteerProject;

    public VolunteerProjectReport() {}
}
