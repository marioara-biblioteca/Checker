import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { SubjectModel, UserModel } from '../model/message';

@Injectable({
  providedIn: 'root',
})
export class HomeAdminService {

  constructor(private http: HttpClient,private cookieService:CookieService) {

  }

  executeHomeAdminService() {
    let headers = new HttpHeaders({
      'Authorization': `Bearer ${this.cookieService.get('access_token')}`
      });
  let options = { headers: headers };
      return this.http.get<Array<UserModel>>('http://localhost:8080/users',options);
  }
  executeHomeAdminService2() {
    let headers = new HttpHeaders({
      'Authorization': `Bearer ${this.cookieService.get('access_token')}`
      });
  let options = { headers: headers };
    return this.http.get<Array<SubjectModel>>('http://localhost:8080/subjects',options);
}
executeHomeAdminSubjectPost(data: any): Observable<any> {
  return this.http.post('http://localhost:8080/subjects', data);
}


}
