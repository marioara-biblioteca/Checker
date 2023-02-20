import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { SubjectModel } from '../model/message';

@Injectable({
  providedIn: 'root'
})
export class CursuriStudentService {

  constructor(private http: HttpClient,private cookieService:CookieService) {
  }

  executeCursuriStudentService() {
    let headers = new HttpHeaders({
      'Authorization': `Bearer ${this.cookieService.get('access_token')}`
      });
  let options = { headers: headers };
    return this.http.get<Array<SubjectModel>>('http://localhost:8080/subjects',options);
}
}
