import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Output } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app/app/app.component';
import { HomeComponent } from './app/home/home.component';
import { FooComponent } from './app/foo.component';
import { StepsComponent } from './app/steps/steps.component';
import { InstructionsComponent } from './app/instructions/instructions.component';
import { InstructStepService } from 'app/services/InstructStepsService';
import { CreateNewComponent } from 'app/create-new-instructions/create-new-instructions.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LevelsComponent } from './app/levels/levels.component';






@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FooComponent,
    StepsComponent,
    InstructionsComponent,
    CreateNewComponent,
    LevelsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot([
      { path: '', component: HomeComponent, pathMatch: 'full' },], { onSameUrlNavigation: 'reload' })
  ],
  providers: [InstructStepService],
  bootstrap: [AppComponent]
})
export class AppModule { }
