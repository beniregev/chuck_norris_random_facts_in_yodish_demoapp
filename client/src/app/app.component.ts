import { Component } from '@angular/core';
import { freeApiService } from './services/freeapi.service';
import { Comments } from './classes/comments';

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Chuck Norris Facts';

  constructor(private _freeApiService: freeApiService) {
  }

  lstcomments: Comments[];

  ngOnInit() {

    this._freeApiService.getcomments()
      .subscribe(
        data => {
          this.lstcomments = data;
        }
      );

  }

}
