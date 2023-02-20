import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { TaskModel } from '../model/message';

@Injectable({
  providedIn: 'root'
})
export class TaskStudentService {

  constructor(private http: HttpClient,private cookieService:CookieService,private route: ActivatedRoute) {
  }

    subjectid = this.route.snapshot.paramMap.get('id');

  executeTaskStudentService() {

     let headers = new HttpHeaders({
      'Authorization': `Bearer ${this.cookieService.get('access_token')}`
      });
  let options = { headers: headers };
    return this.http.get<Array<TaskModel>>('http://localhost:8080/tasks/subject/'+this.subjectid,options);
}
}
