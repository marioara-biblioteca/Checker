import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HomeAdminService } from '../service/home-admin.service';
import { SubjectModel, UserModel } from '../model/message';
import { Subject } from 'rxjs';
@Component({
  selector: 'app-home-admin',
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.css']
})
export class HomeAdminComponent implements OnInit {

  welcomeMessage = '';
  user_id=0;
email='';
userList:Array<UserModel>=[];
subjectList:Array<SubjectModel>=[];
name:string='';
status:string='';



subject: SubjectModel=new SubjectModel(3,"RETELE","SECOND");

  constructor(private route: ActivatedRoute,
    private router: Router, private homeAdminService: HomeAdminService) { }
userArray:any[]=[];
subjectArray:any[]=[];
submitted = false;

//user:UserModel=new UserModel();
  ngOnInit() {
    this.submitted=false;

    this.homeAdminService.executeHomeAdminService().subscribe((res) => {
    //  let obj=JSON.parse(res);

     // this.welcomeMessage = res.content;
     for(var i=0;i<res.length;i++)
    {
      this.userList.push(res[i]);
    }

      this.email=res[0].email;
      console.log( res);
    });
    this.homeAdminService.executeHomeAdminService2().subscribe((res) => {
      //  let obj=JSON.parse(res);

       // this.welcomeMessage = res.content;
       for(var i=0;i<res.length;i++)
      {
        this.subjectList.push(res[i]);
      }

        console.log( res);
      });


    }
// createSubject():void{
//   this.homeAdminService.createSubject(this.name,this.status)
//   .subscribe(data=>{
//     alert("Subject Created Succesfully.")
//   });
// };

}
