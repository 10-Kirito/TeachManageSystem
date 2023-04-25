package com.example.teachmanagesystem.entity.TeacherDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherDepartDTO {
    private Integer teacherId;
    private String teacherName;
    private String gender;
    private LocalDate birthday;
    private String position;
    private String departName;
}
