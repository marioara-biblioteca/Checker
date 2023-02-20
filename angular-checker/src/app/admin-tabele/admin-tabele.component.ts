import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GroupModel, TaskModel } from '../model/message';
import { AdminTabeleService } from '../service/admin-tabele.service';

@Component({
  selector: 'app-admin-tabele',
  templateUrl: './admin-tabele.component.html',
  styleUrls: ['./admin-tabele.component.css']
})
export class AdminTabeleComponent {
  groupList:Array<GroupModel>=[];
  constructor(private route: ActivatedRoute,
    private router: Router, private adminTabeleService: AdminTabeleService) { }
userArray:any[]=[];
subjectArray:any[]=[];
submitted = false;

//user:UserModel=new UserModel();
  ngOnInit() {
    this.submitted=false;

    this.adminTabeleService.executeTabeleAdminService().subscribe((res: string | any[]) => {
    //  let obj=JSON.parse(res);

     // this.welcomeMessage = res.content;
     for(var i=0;i<res.length;i++)
    {
      this.groupList.push(res[i]);
    }

      console.log( res);
    });

// createSubject():void{
//   this.homeAdminService.createSubject(this.name,this.status)
//   .subscribe(data=>{
//     alert("Subject Created Succesfully.")
//   });
// };

  }
}
