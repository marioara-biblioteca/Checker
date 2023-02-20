import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { MessageModel } from '../model/message';

@Injectable({
    providedIn: 'root'
})
export class HelloWorldService {
    constructor(private http: HttpClient,private cookieService:CookieService) {
    }
    executeHelloWorldService() {
        let headers = new HttpHeaders({
            'Authorization': `Bearer ${this.cookieService.get('access_token')}`
            });
        let options = { headers: headers };
        this.http.post('http://localhost:8080/logout',options);

        sessionStorage.clear();
        localStorage.clear();
        this.cookieService.deleteAll();
    }
}