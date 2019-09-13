import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
// import { Oservable } from '@rxjs/Observable';
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FactService {

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.http.get('//localhost:8080/fact/getAll')
  }
}
