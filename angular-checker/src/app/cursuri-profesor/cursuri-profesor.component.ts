import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SubjectModel } from '../model/message';
import { CursuriProfesorService } from '../service/cursuri-profesor.service';

@Component({
  selector: 'app-cursuri-profesor',
  templateUrl: './cursuri-profesor.component.html',
  styleUrls: ['./cursuri-profesor.component.css']
})
export class CursuriProfesorComponent {
  welcomeMessage = '';

  email='';
  subjectList:Array<SubjectModel>=[];


    constructor(private route: ActivatedRoute,private router: Router, private cursuriProfesorService: CursuriProfesorService) { }
  userArray:any[]=[];
  subjectArray:any[]=[];

  //user:UserModel=new UserModel();
    ngOnInit() {
      this.cursuriProfesorService.executeCursuriProfesorService().subscribe((res) => {
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
