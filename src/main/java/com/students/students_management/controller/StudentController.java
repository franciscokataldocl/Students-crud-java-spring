package com.students.students_management.controller;

import com.students.students_management.domain.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final List<Student> students = new ArrayList<>(Arrays.asList(
            new Student(1, "Valentina", "valentina123@gmail.com", 22, 3),
            new Student(2, "Emilio", "emilio.789@gmail.com", 24, 5),
            new Student(3, "Nadia", "nadia.r@gmail.com", 30, 2),
            new Student(4, "Lucas", "lucas21@gmail.com", 28, 4)
    ));

    @GetMapping
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("/{email}")
    public Student getByEmail(@PathVariable String email) {
        for (Student s : students) {
            if (s.getEmail().equalsIgnoreCase(email)) {
                return s;
            }
        }
        return null;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student s) {
        students.add(s);
        return s;
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if(students.get(i).getId() == updatedStudent.getId()) {
                updatedStudent.setId(students.get(i).getId());
                students.set(i, updatedStudent);
                return updatedStudent;
            }
        }
        return null;
    }

    @PatchMapping
    public Student patchStudent(@RequestBody Student patchedStudent){
        for(Student s : students){
            if(s.getId() == patchedStudent.getId()) {
                    if(patchedStudent.getNombre() != null){
                        s.setNombre(patchedStudent.getNombre());
                    }
                    if(patchedStudent.getEmail() != null){
                        s.setEmail(patchedStudent.getEmail());
                    }
                    if(patchedStudent.getEdad() != s.getEdad() && patchedStudent.getEdad() != 0){
                        s.setEdad(patchedStudent.getEdad());
                    }
                    if(patchedStudent.getCurso() != s.getCurso() && patchedStudent.getCurso() != 0){
                        s.setCurso(patchedStudent.getCurso());
                    }
                    return s;
            }

        }
        return null;
    }

    @DeleteMapping("/{id}")
    public Student deleteStudent(@PathVariable int id) {
        for(Student s : students){
            if(s.getId() == id){
                students.remove(s);
                return s;
            }
        }
        return null;
    }

}
