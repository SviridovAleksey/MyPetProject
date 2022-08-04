import { Injectable } from '@angular/core';
import { HomeComponent } from 'app/home/home.component';
import { Subject } from "rxjs/Subject";


@Injectable()
export class StepTextDTO {
  constructor(
    public text: string) { }
}

@Injectable()
export class StepInfoDTO {
  constructor(
    public id: number,
    public name: string,
    public stepPlace: number,
    public infoId: number,
    public stepTextDTO: StepTextDTO) { }
}

@Injectable()
export class ConstructDTO {
  constructor(
    public name: string,
    public value: number) { }
}

@Injectable()
export class LevelsDTO {
  constructor(
    public id: number,
    public name: string,
    public owner: number,
    public levelPlace: number) { }
}


@Injectable()
export class InstructionDTO {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public levelOwner: number,
    public constructDTO: ConstructDTO) { }
}

@Injectable()
export class InstructStepService {

  getStepsEventSubject = new Subject();

  getInstructionsTrigger = new Subject();

  clearNewInstructionFormTrigger = new Subject();

  newInstructionByLevel = new Subject();

  redactionInstructionTrigger = new Subject();

}