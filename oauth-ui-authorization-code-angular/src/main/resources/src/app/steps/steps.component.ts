import { Component, Injectable, OnInit } from '@angular/core';
import { HomeComponent } from 'app/home/home.component';
import { InstructStepService, StepInfoDTO } from 'app/services/InstructStepsService';
import { AppService } from '../app/app.service';



@Component({
  selector: 'app-steps',
  templateUrl: './steps.component.html',
  styleUrls: ['./steps.component.css'],
})


export class StepsComponent implements OnInit {

  private listStepsUrl = 'http://localhost:8089/instruction-server/api/v1/steps/';
  public steps: Array<{ value: StepInfoDTO }> = []

  constructor(private _service: AppService, private _shareInstStepServ: InstructStepService, private _home: HomeComponent) {

    this._shareInstStepServ.getStepsEventSubject.subscribe((data) => {
      this.getSteps(data);
    })

  }

  ngOnInit(): void {

  }

  getSteps(id) {
    this._service.getResource(this.listStepsUrl + id)
      .subscribe(
        data => this.steps = data,
        (error) => {
          for (var i = 0; i < error.error.messages.length; i++) {
            this._home.showTextMessageErr(error.error.messages[i])
          }
        }
      );
  }


  flipCard(id) {
    if (document.getElementById("flipStep" + id).style.transform == 'rotateY(180deg)') {
      document.getElementById("flipStep" + id).style.transform = 'rotateY(0deg)'
    } else {
      document.getElementById("flipStep" + id).style.transform = 'rotateY(180deg)'
    }
  }

}
