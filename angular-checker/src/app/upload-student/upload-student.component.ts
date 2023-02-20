import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ResultModel, ResultModel2, TaskModel } from '../model/message';
import { UploadStudentService } from '../service/upload-student.service';

@Component({
  selector: 'app-upload-student',
  templateUrl: './upload-student.component.html',
  styleUrls: ['./upload-student.component.css']
})
export class UploadStudentComponent implements OnInit {
  selectedFile: File;

  resultList:Array<ResultModel2>=[];
  taskList: Array<TaskModel>=[];
  id: any;
  constructor(private router: Router, private uploadStudentService: UploadStudentService, private formBuilder: FormBuilder,

    private http: HttpClient,
    private cookieService:CookieService,private route: ActivatedRoute) { }
 taskArray:any[]=[];
 onFileChanged(event) {
  this.selectedFile = event.target.files[0];
}
taskid = this.route.snapshot.paramMap.get('id');

postData2() {
  const formData = new FormData();
  formData.append('file', this.selectedFile);

  let headers = new HttpHeaders({
//    'Authorization': `Bearer ${this.cookieService.get('access_token')}`,
 //  'Content-Type': 'multipart/form-data',

    });
let options = { headers: headers };
//formData.append('File',  this.selectedFile);


//this.http.post('http://localhost:8080/upload-file/'+this.cookieService.get('username'), formData,options).subscribe(
  this.http.post('http://localhost:8080/upload-file/'+this.cookieService.get('userId')+'/task/'+this.taskid,formData).subscribe(

  res => {
    //   window.location.reload();
    console.log(res);
    //this.data.testLink="un link";

     },
     err => {
         console.log(err);
       //  window.location.reload();


});
}
 ngOnInit() {
  let headers = new HttpHeaders({
    'Authorization': `Bearer ${this.cookieService.get('access_token')}`
    });
let options = { headers: headers };
  this.http.get<Array<ResultModel2>>('http://localhost:8080/results/'+this.cookieService.get('userId'),options).subscribe((res: string | any[]) => {
    //  let obj=JSON.parse(res);

     // this.welcomeMessage = res.content;
     for(var i=0;i<res.length;i++)
    {
      this.resultList.push(res[i]);
    }

      console.log( res);
    });

    this.id = this.route.snapshot.paramMap.get('id');

 this.uploadStudentService.executeTaskUploadStudentService(this.id).subscribe((res: string | any[]) => {
  //  let obj=JSON.parse(res);

   // this.welcomeMessage = res.content;
   for(var j=0;j<res.length;j++)
  {
    this.taskList.push(res[j]);
  }

    console.log( res);
  });

  }

}
