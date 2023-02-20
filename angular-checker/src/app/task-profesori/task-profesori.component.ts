import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { TaskModel } from '../model/message';
import { TaskProfesoriService } from '../service/task-profesori.service';

@Component({
  selector: 'app-task-profesori',
  templateUrl: './task-profesori.component.html',
  styleUrls: ['./task-profesori.component.css']
})
export class TaskProfesoriComponent implements OnInit  {
  welcomeMessage = '';
  selectedFile: File;

  email='';
  taskList:Array<TaskModel>=[];
  taskForm: FormGroup;
  loading = false;
  submitted = false;
  error = '';
  data = {deadLine: "",requirement: "",testLink: "",subjectName: ""};
  data2 = {file:""};

    constructor(private router: Router, private taskProfesoriService: TaskProfesoriService , private formBuilder: FormBuilder,

      private http: HttpClient,
      private cookieService:CookieService,private renderer: Renderer2,private el: ElementRef) { }
 taskArray:any[]=[];
 userId=this.cookieService.get('userId');

 editHtmlTemplate() {

}

 onFileChanged(event) {
  this.selectedFile = event.target.files[0];
}
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
  this.http.post('http://localhost:8080/upload-file/'+this.cookieService.get('userId'),formData).subscribe(

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

 postData() {
  let headers = new HttpHeaders({
    'Authorization': `Bearer ${this.cookieService.get('access_token')}`
    });
let options = { headers: headers };
  return this.http.post<any>('http://localhost:8080/tasks', this.data,options).subscribe(
      res => {
     //   window.location.reload();
      },
      err => {
          console.log("Error occured");
        //  window.location.reload();

      }
  );
}


//  postData2() {
//    let headers = new HttpHeaders({
//      'Authorization': `Bearer ${this.cookieService.get('access_token')}`
//      });
//  let options = { headers: headers };
//    return this.http.post<any>('http://localhost:8080/upload-file/'+this.cookieService.get('username')+'', this.data2,options).subscribe(
//        res => {
//  console.log(res.status);
//        },
//        err => {
//            console.log("Error occured");
//         //   window.location.reload();

//        }
//    );
//  }
  //user:UserModel=new UserModel();
    ngOnInit() {
      this.taskProfesoriService. executeTaskProfesoriService().subscribe((res: string | any[]) => {
      //  let obj=JSON.parse(res);

       // this.welcomeMessage = res.content;
       for(var i=0;i<res.length;i++)
      {
        this.taskList.push(res[i]);
      }

        console.log( res);
      });



      const embed = this.el.nativeElement.querySelector('embed');

      this.renderer.setProperty(embed, 'src', 'http://localhost:8080/upload-file-professor/'+this.userId);


    //   this.taskForm = this.formBuilder.group({
    //     deadline: ['', Validators.required],
    //     requirements: ['', Validators.required],
    //     testLink: ['', Validators.required],
    //     subjectName: ['', Validators.required],

    //   });

    // }

    // get f() { return this.taskForm.controls; }

    // onSubmit() {
    //   this.submitted = true;

    //   if (this.taskForm.invalid) {
    //     return;
    //   }

    //   this.loading = true;
    //   let headers = new HttpHeaders({
    //     'Authorization': `Bearer ${this.cookieService.get('access_token')}`
    //     });
    // let options = { headers: headers };
    //   this.http.post('http://localhost:8080/task', this.taskForm.value,options).subscribe(
    //     (response: any) => {
    //       // store JWT in cookie or local storage

    //       // redirect to homepage
    //     //  this.router.navigate(['/home_admin']);

    //     },
    //     error => {
    //       this.error = error;
    //       this.loading = false;
    //     }
    //   );
    }
}
