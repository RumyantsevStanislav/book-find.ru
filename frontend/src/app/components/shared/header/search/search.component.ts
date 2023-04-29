import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  form: FormGroup
  submitted = false

  constructor(private router: Router) {
    this.form = new FormGroup({
      queryText: new FormControl(null, [
        Validators.required,
        Validators.minLength(1)
      ])
    })
  }

  ngOnInit(): void {
  }

  submit() {
    if (this.form.invalid) {
      return
    }
    const queryText = this.form.value.queryText
    this.router.navigate(['/search'], {queryParams: {search: queryText}}).then(r => '/');
  }
}
