import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { response } from 'express';

@Component({
  selector: 'app-add-student',
  standalone: false,
  templateUrl: './add-student.component.html',
  styleUrls: ['./add-student.component.css'] 
})

export class AddStudentComponent {

  constructor(private httpClient : HttpClient , private router: Router){}

  student = new FormGroup({
    id : new FormControl(),
    name : new FormControl(),
    age : new FormControl(),
    dept : new FormControl()
  });

  handleSubmit() {
    const url = "http://localhost:8080/addStudent";
    console.log(this.student.value);

    this.httpClient.post(url, this.student.value, { withCredentials: true }) // ✅ Include session cookies
    .subscribe({
      next: () => {
        alert("✅ Student created successfully!");
        this.student.reset(); // Clear form
        this.router.navigateByUrl('/show-student', { skipLocationChange: true }).then(() => {
          this.router.navigate(['/show-student']); // Refresh student list
        });
      },
      error: (error) => console.error("❌ Error adding student:", error),
    });
}

  
  

  goToStudentList() {
    this.router.navigate(['/show-student']);
  }
}
