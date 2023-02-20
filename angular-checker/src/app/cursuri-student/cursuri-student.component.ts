import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SubjectModel } from '../model/message';
import { CursuriStudentService } from '../service/cursuri-student.service';

@Component({
  selector: 'app-cursuri-student',
  templateUrl: './cursuri-student.component.html',
  styleUrls: ['./cursuri-student.component.css']
})
export class CursuriStudentComponent {

  welcomeMessage = '';

email='';
subjectList:Array<SubjectModel>=[];


  constructor(private route: ActivatedRoute,
    private router: Router, private cursuriStudentService: CursuriStudentService) { }
userArray:any[]=[];
subjectArray:any[]=[];

//user:UserModel=new UserModel();
  ngOnInit() {
    this.cursuriStudentService.executeCursuriStudentService().subscribe((res) => {
    //  let obj=JSON.parse(res);

     // this.welcomeMessage = res.content;
     for(var i=0;i<res.length;i++)
    {
      this.subjectList.push(res[i]);
    }

      console.log( res);
    });


  }
}
