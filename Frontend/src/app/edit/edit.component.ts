import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-edit',
  standalone: false,
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  studentForm: FormGroup;
  studentId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private httpClient: HttpClient
  ) {
    this.studentForm = this.fb.group({
      id: [''],
      name: [''],
      age: [''],
      dept: ['']
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.studentId = Number(params.get('id'));
      console.log('📌 Extracted Student ID:', this.studentId);
      if (this.studentId) {
        this.fetchStudentDetails();
      }
    });
  }

  fetchStudentDetails() {
    if (!this.studentId) {
      console.error('❌ Student ID is null or undefined');
      return;
    }

    const url = `http://localhost:8080/showStudent/${this.studentId}`;
    console.log('Fetching student data from:', url);

    this.httpClient.get(url, { withCredentials: true }).subscribe({
      next: (data: any) => {
        console.log('✅ Student data fetched:', data);
        this.studentForm.setValue({
          id: data.id || '',
          name: data.name || '',
          age: data.age || '',
          dept: data.dept || ''
        });
      },
      error: (error) => console.error('❌ Error fetching student details:', error)
    });
  }

  updateStudent() {
    if (!this.studentId) {
      console.error('❌ Cannot update: Student ID is missing.');
      return;
    }

    const url = `http://localhost:8080/updateStudent/${this.studentId}`;
    console.log('Updating student data at:', url);

    this.httpClient.put(url, this.studentForm.value, { withCredentials: true }).subscribe({
      next: () => {
        alert('✅ Student updated successfully!');
        this.router.navigate(['/show-student']);
      },
      error: (error) => console.error('❌ Error updating student:', error)
    });
  }



  goToStudentList() {
    this.router.navigate(['/show-student']);
  }
}


