import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { TaskModel } from '../model/message';
import { TaskStudentService } from '../service/task-student.service';

@Component({
  selector: 'app-task-student',
  templateUrl: './task-student.component.html',
  styleUrls: ['./task-student.component.css']
})
export class TaskStudentComponent {
  welcomeMessage = '';
  data = {link: ""};

  email='';
  taskList:Array<TaskModel>=[];


    constructor(private router: Router, private taskStudentService: TaskStudentService ,private formBuilder: FormBuilder,

      private http: HttpClient,
      private cookieService:CookieService,private route: ActivatedRoute) { }
      subjectid = this.route.snapshot.paramMap.get('id');

    // postData() {
    //   let headers = new HttpHeaders({
    //     'Authorization': `Bearer ${this.cookieService.get('access_token')}`
    //     });
    // let options = { headers: headers };
    //   return this.http.post<any>('http://localhost:8080/upload-file', this.data,options).subscribe(
    //       res => {
    //         window.location.reload();
    //       },
    //       err => {
    //           console.log("Error occured");
    //           window.location.reload();

    //       }
    //   );
    // }
    taskArray:any[]=[];

  //user:UserModel=new UserModel();
    ngOnInit() {
      let headers = new HttpHeaders({
        'Authorization': `Bearer ${this.cookieService.get('access_token')}`
        });
    let options = { headers: headers };
      this.http.get<Array<TaskModel>>('http://localhost:8080/tasks/subject/'+this.subjectid,options).subscribe((res: string | any[]) => {
      //  let obj=JSON.parse(res);

       // this.welcomeMessage = res.content;
       for(var i=0;i<res.length;i++)
      {
        this.taskList.push(res[i]);
      }

        console.log( res);
      });


    }
}
