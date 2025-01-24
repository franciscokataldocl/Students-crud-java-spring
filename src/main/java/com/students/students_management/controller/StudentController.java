package com.students.students_management.controller;

import com.students.students_management.domain.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> getByEmail(@PathVariable String email) {
        for (Student s : students) {
            if (s.getEmail().equalsIgnoreCase(email)) {
                return ResponseEntity.ok(s);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Object> createStudent(@RequestBody Student s) {
        students.add(s);
        //mostrar uri de recurso creado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{email}")
                .buildAndExpand(s.getEmail())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @PatchMapping
    public ResponseEntity<Object> patchStudent(@RequestBody Student patchedStudent) {
        for (Student s : students) {
            if (s.getId() == patchedStudent.getId()) {
                if (patchedStudent.getNombre() != null) {
                    s.setNombre(patchedStudent.getNombre());
                }
                if (patchedStudent.getEmail() != null) {
                    s.setEmail(patchedStudent.getEmail());
                }
                if (patchedStudent.getEdad() != s.getEdad() && patchedStudent.getEdad() != 0) {
                    s.setEdad(patchedStudent.getEdad());
                }
                if (patchedStudent.getCurso() != s.getCurso() && patchedStudent.getCurso() != 0) {
                    s.setCurso(patchedStudent.getCurso());
                }
                return ResponseEntity.ok().body("Usuario con id: " + patchedStudent.getId() + " actualizado correctamente");
            }

        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping
    public ResponseEntity<Object> updateStudent(@RequestBody Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == updatedStudent.getId()) {
                updatedStudent.setId(students.get(i).getId());
                students.set(i, updatedStudent);
                return ResponseEntity.ok().body("Usuario con id: " + updatedStudent.getId() + " actualizado con éxito");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                students.remove(s);
                return ResponseEntity.ok().body("Usuario con id: " + id + " eliminado");
            }
        }
        return ResponseEntity.notFound().build();
    }

}
