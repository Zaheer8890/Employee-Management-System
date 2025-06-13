package com.angular.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.angular.crud.entity.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, String>{

	
	//Page<Student> findByNameContainingIgnoreCaseOrDeptContainingIgnoreCaseOrAgeContaining(
	        //String name, String dept, String age, Pageable pageable);
	
	
	
	        
	        
	        /* Define a custom query to filter by multiple fields
    @Query("SELECT s FROM Student s WHERE " +
           "(:id IS NULL OR LOWER(s.id) LIKE LOWER(CONCAT('%', :id, '%'))) AND " +
           "(:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:dept IS NULL OR LOWER(s.dept) LIKE LOWER(CONCAT('%', :dept, '%'))) AND " +
           "(:age IS NULL OR s.age = :age)")
    Page<Student> findByFilter(
            @Param("id") String id,
            @Param("name") String name,
            @Param("dept") String dept,
            @Param("age") Integer age,
            Pageable pageable
    );*/
	
	
	
	
    
   //  Hardcore with entity //@Procedure(name = "Student.fetchDynamic")
	    //or
	@Procedure(procedureName = "fetchStudent")
    List<Student> fetchFiltered(
        @Param("in_Id") String id,
        @Param("in_name") String name,
        @Param("in_age") Integer age,
        @Param("in_dept") String dept
    );
	
	
	/*
	
	@Query(value = "CALL fetchNameDeptByName(:name)", nativeQuery = true)
	List<Object[]> fetchfiltered(@Param("name") String name);


	*/
	
	
}

/*@Repository
public interface StudentRepo extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {
	
}*/

