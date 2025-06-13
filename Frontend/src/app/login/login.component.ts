import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  
  constructor(private httpClient: HttpClient, private router: Router) {}

  loginForm = new FormGroup({
    username: new FormControl(''), // Ensure default empty value
    password: new FormControl('')
  });

  handleLogin() {
    const url = "http://localhost:8080/login";
    const loginData = {
      username: this.loginForm.value.username || '',
      password: this.loginForm.value.password || ''
    };
  
    console.log("🔵 Sending login request: ", loginData);
  
    
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
  
    this.httpClient.post(url, loginData, { headers, withCredentials: true }).subscribe({
      next: (response: any) => {
        
        console.log("🟢 Login Response: ", response);
  
        if (response && response.message === "Login successful") {
          sessionStorage.setItem('user', loginData.username);
          sessionStorage.setItem('isLoggedIn', 'true');
          this.router.navigate(['/show-student']);
        } else {
          alert("❌ Invalid credentials! Please try again.");
        }
      },
      error: (error) => {
        console.error("🔴 Login failed: ", error);
        alert("❌ Login failed! Check your credentials.");
      }
    });
  }
  

  
}
