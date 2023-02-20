import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ResultModel, ResultModel2, TaskModel } from '../model/message';

@Injectable({
  providedIn: 'root'
})
export class UploadStudentService {


  constructor(private http: HttpClient,private cookieService:CookieService,private route: ActivatedRoute) { }
  executeResultateStudentService() {
    let headers = new HttpHeaders({
      'Authorization': `Bearer ${this.cookieService.get('access_token')}`
      });
  let options = { headers: headers };
    return this.http.get<Array<ResultModel2>>('http://localhost:8080/results/'+this.cookieService.get('username'),options);
}
executeTaskUploadStudentService(id:Number) {
  let headers = new HttpHeaders({
    'Authorization': `Bearer ${this.cookieService.get('access_token')}`
    });
let options = { headers: headers };

  return this.http.get<Array<TaskModel>>('http://localhost:8080/tasks/'+id,options);
}
}
