package com.angular.crud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.angular.crud.entity.Student;

import jakarta.persistence.criteria.*;

public class StudentSpecification {

    public static Specification<Student> filterByFields(String id ,String name, String dept, Integer age) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (id != null && !id.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("id")), "%" + id.toLowerCase() + "%"));
            }

            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (dept != null && !dept.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("dept")), "%" + dept.toLowerCase() + "%"));
            }

            if (age != null) {
                predicates.add(cb.equal(root.get("age"), age));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

