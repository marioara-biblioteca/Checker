import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GroupModel } from '../model/message';

@Injectable({
  providedIn: 'root'
})
export class AdminTabeleService {

  constructor(private http: HttpClient) { }
  executeTabeleAdminService() {
    return this.http.get<Array<GroupModel>>('http://localhost:8080/groups');
}
}
