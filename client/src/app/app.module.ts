import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from "@angular/common/http";
import { FactService } from "./shared/fact/fact.service";
import { FactListComponent } from './fact-list/fact-list.component';
import { MatTabsModule } from '@angular/material/tabs';
import { FactsTableComponent } from './facts-table/facts-table.component';
import { MatTableModule,
  MatSortModule,
  MatPaginatorModule } from '@angular/material';
import { MyMaterialModule } from './my-material.module';
import { freeApiService } from './services/freeapi.service';


const material = [
  MatTableModule,
  MatSortModule,
  MatPaginatorModule
];
@NgModule({
  declarations: [
    AppComponent
    , FactListComponent
    , FactsTableComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule
    , MyMaterialModule
    , HttpClientModule
    , MatTabsModule
    , MatTableModule
    , MatPaginatorModule
    , MatSortModule
    , material, MyMaterialModule
  ],
  exports: [material],
  providers: [
    FactService
    , freeApiService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
