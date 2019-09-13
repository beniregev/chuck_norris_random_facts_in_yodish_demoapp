import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {FactService} from "./shared/fact/fact.service";
import { FactListComponent } from './fact-list/fact-list.component';

@NgModule({
  declarations: [
    AppComponent,
    FactListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [
    FactService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
