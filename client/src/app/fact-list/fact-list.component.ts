import { Component, OnInit } from '@angular/core';
import { FactService } from '../shared/fact/fact.service';

@Component({
  selector: 'app-fact-list',
  templateUrl: './fact-list.component.html',
  styleUrls: ['./fact-list.component.css']
})
export class FactListComponent implements OnInit {
  facts: Array<any>;

  constructor(private factService: FactService) { }


  ngOnInit() {
    this.factService.getAll().subscribe(data => {
      this.facts = data;
    });
  }

}
