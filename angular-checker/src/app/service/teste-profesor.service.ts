import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { TestModel } from '../model/message';

@Injectable({
  providedIn: 'root'
})
export class TesteProfesorService {

  constructor(private http: HttpClient,private cookieService:CookieService) { }

   executeTestProfesorService() {
    let headers = new HttpHeaders({
      'Authorization': `Bearer ${this.cookieService.get('access_token')}`
      });
  let options = { headers: headers };
    return this.http.get<Array<TestModel>>('http://localhost:8080/tests',options);
}
}
