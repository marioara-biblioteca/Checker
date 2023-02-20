export class MessageModel {
    toBeTruthy(): any {
      throw new Error('Method not implemented.');
    }
    id: number;
    content: string;
    constructor(private _id: number, public message: string) {
        this.id = _id;
        this.content = message;
    }
}


export class UserModel {
    user_id: number;
    email: string;
    firstName: string;
    password:string;
    lastName: string;
    grouping_id:number;
    role_id:number
    constructor(private _id: number, public _email: string,public _first_name:string,public _last_name:string,private _password:string,private _grouping_id:number,private _role_id:number) {
        this.user_id = _id;
        this.email = _email;
        this.firstName = _first_name;
        this.password=_password;
        this.lastName = _last_name;
        this. grouping_id =  _grouping_id;
        this.role_id = _role_id;


    }
}


export class SubjectModel {
    subjectId: number;
   name: string;
   status:string;
id: any;
    constructor(private _subject_id: number, public _name: string, public _status: string) {
        this.subjectId = _subject_id;
        this.name =  _name;
        this.status=_status;
    }
}
export class TaskModel {

   deadLine: string;
   requirement: string;
   testLink: string;
   subjectName: string;
   taskId: any;
    constructor(private task_id:any,public dead_Line:string,public _requirement:string,public test_Link:string,public subject_Name:string) {
     this.taskId=task_id;
        this.deadLine=dead_Line
        this.requirement=_requirement
        this.testLink=test_Link
        this.subjectName=subject_Name
    }
}
export class GroupModel {
   grouping_id: number;

  groupIdentifier:string;
  series:string
    constructor(private  _grouping_id: number, public _group_identifier: string,public _series :string) {
        this.grouping_id = _grouping_id;
        this.groupIdentifier =  _group_identifier;
        this.series=_series;
    }
}

export class TestModel {
    test_id: number;
 link:string;

     constructor(private  _id: number, public _link: string) {
         this.test_id = _id;
this.link=_link
     }
 }

 export class ResultModel{
result_id:number;
resultLink:string;
sourceLink:string;
uploaded_time:string;
taskId:number;
user_id:number;
constructor(private  _id: number, public _link: string,public _source_link:string,public  _uploaded_time:string,public _task_id:number,public _user_id:number) {
    this.result_id = _id;
this.resultLink=_link;
this.sourceLink=_source_link;
this.uploaded_time=_uploaded_time;
this.taskId=_task_id;
this.user_id=_user_id;

}
}
export class ResultModel2{

    resultLink:string;
    sourceLink:string;

    constructor( public _link: string,public _source_link:string) {
    this.resultLink=_link;
    this.sourceLink=_source_link;


    }
 }
