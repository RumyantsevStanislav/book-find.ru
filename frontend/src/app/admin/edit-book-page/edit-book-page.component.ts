import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-edit-book-page',
  templateUrl: './edit-book-page.component.html',
  styleUrls: ['./edit-book-page.component.scss']
})
export class EditBookPageComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }

  // id: number;
  // employee: Employee;
  //
  // constructor(private route: ActivatedRoute,private router: Router,
  //             private employeeService: EmployeeService) { }
  //
  // ngOnInit() {
  //   this.employee = new Employee();
  //
  //   this.id = this.route.snapshot.params['id'];
  //
  //   this.employeeService.getEmployee(this.id)
  //     .subscribe(data => {
  //       console.log(data)
  //       this.employee = data;
  //     }, error => console.log(error));
  // }
  //
  // updateEmployee() {
  //   this.employeeService.updateEmployee(this.id, this.employee)
  //     .subscribe(data => console.log(data), error => console.log(error));
  //   this.employee = new Employee();
  //   this.gotoList();
  // }
  //
  // onSubmit() {
  //   this.updateEmployee();
  // }
  //
  // gotoList() {
  //   this.router.navigate(['/employees']);
  // }
}
