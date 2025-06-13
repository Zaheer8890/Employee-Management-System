/*import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-show-student',
  standalone: false,
  templateUrl: './show-student.component.html',
  styleUrls: ['./show-student.component.css']  
})
export class ShowStudentComponent implements OnInit {

  students: any = [];

  constructor(private httpClient: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.getAllStudentData();
  }

  getAllStudentData() {
    const baseURL = "http://localhost:8080/showStudent";
    this.httpClient.get(baseURL, { withCredentials: true })  // ✅ Ensures session is sent
      .subscribe({
        next: (response: any) => {
          console.log("✅ Student data:", response);
          this.students = response;
        },
        error: (error) => console.error("❌ Error fetching students:", error),
      });
  }
  
  
  
  deleteStudent(studentId: any) {
    const baseURL = "http://localhost:8080/deletestudent";
    this.httpClient.get(baseURL + "/" + studentId, { withCredentials: true }).subscribe({
      next: () => {
        console.log("✅ Student deleted successfully");
        this.getAllStudentData(); // Refresh list after deletion
      },
      error: (error) => console.error("❌ Error deleting student: " + error),
    });
  }

  logout() {
    const logoutURL = "http://localhost:8080/logout";
    this.httpClient.get(logoutURL, { withCredentials: true }).subscribe({
      next: () => {
        console.log("✅ Logout successful");
        alert("Logged out successfully!");
        sessionStorage.removeItem('isLoggedIn');  
        sessionStorage.removeItem('username');  
        this.router.navigate(['/login']);  // Redirect to login
      },
      error: (error) => console.error("❌ Error logging out: " + error),
    });
  }

  goToStudentEdit(studentId: number) {
    this.router.navigate(['/edit', studentId]); // ✅ Redirect with ID
  }
  
}*/

// single page search

/*import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-show-student',
  standalone: false,
  templateUrl: './show-student.component.html',
  styleUrls: ['./show-student.component.css']
})
export class ShowStudentComponent implements OnInit {

  students: any[] = [];
  filteredStudents: any[] = [];

  currentPage: number = 0;
  totalPages: number = 0;
  pageSize: number = 8;

  searchTerm: string = ''; // For storing search input

  constructor(private httpClient: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.getAllStudentData();
  }

  getAllStudentData(page: number = 0) {
    const baseURL = `http://localhost:8080/showStudent?page=${page}&size=${this.pageSize}`;
    this.httpClient.get(baseURL, { withCredentials: true }).subscribe({
      next: (response: any) => {
        this.students = response.content;
        this.filteredStudents = response.content; // Initially set to all students
        this.totalPages = response.totalPages;
        this.currentPage = response.number;
      },
      error: (error) => console.error("❌ Error fetching students:", error),
    });
  }

  deleteStudent(studentId: any) {
    const baseURL = "http://localhost:8080/deletestudent";
    this.httpClient.get(baseURL + "/" + studentId, { withCredentials: true }).subscribe({
      next: () => {
        this.getAllStudentData(this.currentPage);
      },
      error: (error) => console.error("❌ Error deleting student: " + error),
    });
  }

  logout() {
    const logoutURL = "http://localhost:8080/logout";
    this.httpClient.get(logoutURL, { withCredentials: true }).subscribe({
      next: () => {
        alert("Logged out successfully!");
        sessionStorage.clear();
        this.router.navigate(['/login']);
      },
      error: (error) => console.error("❌ Error logging out: " + error),
    });
  }

  nextPage() {
    if (this.currentPage + 1 < this.totalPages) {
      this.getAllStudentData(this.currentPage + 1);
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.getAllStudentData(this.currentPage - 1);
    }
  }

  // Filter students based on the search term
  filterStudents() {
    const term = this.searchTerm.trim().toLowerCase();
    if (!term) {
      this.filteredStudents = [...this.students]; // Reset if no search term
      return;
    }

    this.filteredStudents = this.students.filter((student) =>
      student.name?.toLowerCase().includes(term) ||
      student.dept?.toLowerCase().includes(term) ||
      String(student.age).includes(term)
    );
  }
} */



import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-show-student',
  standalone: false,
  templateUrl: './show-student.component.html',
  styleUrls: ['./show-student.component.css']
})
export class ShowStudentComponent implements OnInit {

  students: any[] = [];

  currentPage: number = 0;
  totalPages: number = 0;
  pageSize: number = 8;

  // Individual search fields
  idFilter: string = '';
  nameFilter: string = '';
  ageFilter: string = '';
  deptFilter: string = '';

  constructor(private httpClient: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.getAllStudentData();
  }

  getAllStudentData(page: number = 0) {
    let url = `http://localhost:8080/showStudent?page=${page}&size=${this.pageSize}`;

    // Add filters to URL
    if (this.idFilter.trim())   url += `&id=${this.idFilter.trim()}`;
    if (this.nameFilter.trim()) url += `&name=${this.nameFilter.trim()}`;
    if (this.ageFilter.trim())  url += `&age=${this.ageFilter.trim()}`;
    if (this.deptFilter.trim()) url += `&dept=${this.deptFilter.trim()}`;

    this.httpClient.get(url, { withCredentials: true }).subscribe({
      next: (response: any) => {
        this.students = response.content;
        this.totalPages = response.totalPages;
        this.currentPage = response.number;
      },
      error: (error) => console.error("❌ Error fetching students:", error),
    });
  }

  deleteStudent(studentId: any) {
    const url = `http://localhost:8080/deletestudent/${studentId}`;
    this.httpClient.get(url, { withCredentials: true }).subscribe({
      next: () => this.getAllStudentData(this.currentPage),
      error: (error) => console.error("❌ Error deleting student:", error),
    });
  }

  logout() {
    const logoutURL = "http://localhost:8080/logout";
    this.httpClient.get(logoutURL, { withCredentials: true }).subscribe({
      next: () => {
        alert("Logged out successfully!");
        sessionStorage.clear();
        this.router.navigate(['/login']);
      },
      error: (error) => console.error("❌ Error logging out: ", error),
    });
  }

  nextPage() {
    if (this.currentPage + 1 < this.totalPages) {
      this.getAllStudentData(this.currentPage + 1);
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.getAllStudentData(this.currentPage - 1);
    }
  }

  onSearchChange() {
    this.currentPage = 0;
    this.getAllStudentData();
  }
}




