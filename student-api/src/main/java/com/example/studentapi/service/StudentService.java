package com.example.studentapi.service;

import com.example.studentapi.exception.ResourceNotFoundException;
import com.example.studentapi.model.Student;
import com.example.studentapi.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> getAll() {
        return repo.findAll();
    }

    public Student getByRoll(int rollNo) {
        return repo.findById(rollNo)
                   .orElseThrow(() -> new ResourceNotFoundException("Student not found with rollNo: " + rollNo));
    }

    public Student addStudent(Student s) {
        return repo.save(s);
    }

    public boolean deleteStudent(int rollNo) {
        if (repo.existsById(rollNo)) {
            repo.deleteById(rollNo);
            return true;
        }
        return false;
    }

    public boolean updateStudent(int rollNo, Student updated) {
        if (repo.existsById(rollNo)) {
            updated.setRollNo(rollNo);   // ensure same ID
            repo.save(updated);
            return true;
        }
        return false;
    }

    // ⭐ NEW: Paginated + Sorted
    public Page<Student> getAllPaged(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repo.findAll(pageable);
    }
}
