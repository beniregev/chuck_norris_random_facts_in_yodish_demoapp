import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class freeApiService {
  constructor(private httpclient: HttpClient) {}

  getcomments(): Observable<any> {
    return this.httpclient.get("https://jsonplaceholder.typicode.com/posts/1/comments")
  }
}
