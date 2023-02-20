import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { TaskModel } from '../model/message';

@Injectable({
  providedIn: 'root'
})
export class TaskProfesoriService {

  constructor(private http: HttpClient,private cookieService:CookieService) {
  }

  executeTaskProfesoriService() {
    let headers = new HttpHeaders({
      'Authorization': `Bearer ${this.cookieService.get('access_token')}`
      });
  let options = { headers: headers };
    return this.http.get<Array<TaskModel>>('http://localhost:8080/tasks',options);
}
}
