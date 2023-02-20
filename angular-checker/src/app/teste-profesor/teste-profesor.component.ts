import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { TestModel } from '../model/message';
import { TesteProfesorService } from '../service/teste-profesor.service';

@Component({
  selector: 'app-teste-profesor',
  templateUrl: './teste-profesor.component.html',
  styleUrls: ['./teste-profesor.component.css']
})
export class TesteProfesorComponent implements OnInit {
  data = {link: ""};

  testList:Array<TestModel>=[];
  constructor(private router: Router, private testeProfesorService: TesteProfesorService , private formBuilder: FormBuilder,

    private http: HttpClient,
    private cookieService:CookieService) { }
    postData() {
      let headers = new HttpHeaders({
        'Authorization': `Bearer ${this.cookieService.get('access_token')}`
        });
    let options = { headers: headers };
      return this.http.post<any>('http://localhost:8080/upload-file', this.data,options).subscribe(
          res => {
            window.location.reload();
          },
          err => {
              console.log("Error occured");
              window.location.reload();

          }
      );
    }
  ngOnInit() {
    this.testeProfesorService. executeTestProfesorService().subscribe((res: string | any[]) => {
    //  let obj=JSON.parse(res);

     // this.welcomeMessage = res.content;
     for(var i=0;i<res.length;i++)
    {
      this.testList.push(res[i]);
    }

      console.log( res);
    });




  }
}
