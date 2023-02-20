import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers:[CookieService]
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  error = '';
  //cookieService: CookieService;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private http: HttpClient,
    private cookieService:CookieService
  ) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
  //   let headers = new HttpHeaders({
  //     'username':  this.loginForm.get('username').value,
  //     'password':this.loginForm.get('password').value });
  // let options = { headers: headers };
    this.http.post('http://localhost:8080/login', this.loginForm.value).subscribe(
      (response: any) => {
        // store JWT in cookie or local storage
        console.log(response.access_token);
        console.log(response.refresh_token);
        console.log( this.loginForm.get('username').value);
        this.cookieService.set('access_token', response.access_token);
        this.cookieService.set('refresh_token', response.refresh_token);
        this.cookieService.set('username',this.loginForm.get('username').value);

        //console.log(response);
        console.log(response.user_type)
    if(response.user_type=="[STUDENT]")
    {
      this.router.navigate(['/home_student']);

    }
   else if(response.user_type=="[PROFESSOR]")
    {
      this.router.navigate(['/home_profesor']);

    }
    else if(response.user_type=="[ADMIN]")
    {
      this.router.navigate(['/home_admin']);

    }
    else{
        console.log("user wrong")
    }

        // redirect to homepage
      //  this.router.navigate(['/home_admin']);

      },
      error => {
        this.error = error;
        this.loading = false;
      }
    );
    this.http.get('http://localhost:8080/users/info',{params:{username:this.loginForm.get('username').value}}).subscribe(
      (response: any) => {
        // store JWT in cookie or local storage
        console.log(response.userId);

        this.cookieService.set('userId',response.userId);

        //console.log(response);


        // redirect to homepage
      //  this.router.navigate(['/home_admin']);

      },
      error => {
        this.error = error;
        this.loading = false;
      }
    );
  }
}
