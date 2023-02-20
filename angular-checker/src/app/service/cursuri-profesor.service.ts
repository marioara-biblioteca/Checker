import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SubjectModel } from '../model/message';

@Injectable({
  providedIn: 'root'
})
export class CursuriProfesorService {

  constructor(private http: HttpClient) {
  }

  executeCursuriProfesorService() {
    return this.http.get<Array<SubjectModel>>('http://localhost:8080/subjects');
}
}
