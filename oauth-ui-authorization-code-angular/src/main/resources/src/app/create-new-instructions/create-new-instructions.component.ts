import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, FormArray } from '@angular/forms';
import { AppService } from 'app/app/app.service';
import { ConstructDTO, InstructionDTO, InstructStepService, StepInfoDTO, StepTextDTO } from 'app/services/InstructStepsService';
import { HomeComponent } from '../home/home.component';



@Component({
  selector: 'app-create-new-instructions',
  templateUrl: './create-new-instructions.component.html',
  styleUrls: ['./create-new-instructions.component.css'],
})
export class CreateNewComponent implements OnInit {

  private createInstURL = 'http://localhost:8089/instruction-server/api/v1/instructions';
  private stepURL = 'http://localhost:8089/instruction-server/api/v1/steps';
  instructionInfoForm: FormGroup;
  contactForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private _appService: AppService, private _home: HomeComponent,
    private _shareInstStepServ: InstructStepService) {
    this._shareInstStepServ.newInstructionByLevel.subscribe((data: number) => {
      this.createContactForm();
      this.createInstructionInfoForm();
      this.instructionInfoForm.get("instructionLevel").setValue(data, { emitEvent: true });
    })
    this._shareInstStepServ.clearNewInstructionFormTrigger.subscribe(() => {
      this.createContactForm();
      this.createInstructionInfoForm();
    })
    this._shareInstStepServ.redactionInstructionTrigger.subscribe((data: InstructionDTO) => {
      this.createContactForm();
      this.createInstructionFormFromInstruction(data);
      this.createContactFormFromInstruct(data);
    })
    this.createContactForm();
    this.createInstructionInfoForm();
  }

  ngOnInit(): void {
  }

  createInstructionFormFromInstruction(instruct: InstructionDTO) {
    this.instructionInfoForm = new FormGroup({
      id: new FormControl(instruct.id),
      name: new FormControl(instruct.name),
      description: new FormControl(instruct.description),
      instructionLevel: new FormControl(instruct.levelOwner),
      constructDTO: new FormGroup({
        name: new FormControl(instruct.constructDTO.name),
        value: new FormControl(instruct.constructDTO.value)
      }),
    })
  }

  createContactFormFromInstruct(instruct: InstructionDTO) {
    this._appService.getResource(this.stepURL + '/' + instruct.id)
      .subscribe(
        data => this.saveStepsInForm(data),
        (error) => {
          for (var i = 0; i < error.error.messages.length; i++) {
            this._home.showTextMessageErr(error.error.messages[i])
          }
        }
      );
  }

  saveStepsInForm(steps: Array<StepInfoDTO>) {
    const control = <FormArray>this.contactForm.get('sections');
    control.clear();
    for (var i = 0; i < steps.length; i++) {
      control.insert(steps[i].stepPlace, this.initStepSectionFromStepDTO(steps[i]));
    }
  }

  createInstructionInfoForm() {
    this.instructionInfoForm = new FormGroup({
      id: new FormControl('0'),
      name: new FormControl(''),
      description: new FormControl(''),
      instructionLevel: new FormControl('0'),
      constructDTO: new FormGroup({
        name: new FormControl('unknown'),
        value: new FormControl('0')
      }),
    })
  }

  createContactForm() {
    this.contactForm = new FormGroup({
      sections: new FormArray([
        this.initNewStepSection()
      ]),
    })
  }

  initStepSectionFromStepDTO(stepInfoDTO: StepInfoDTO) {
    return new FormGroup({
      id: new FormControl(stepInfoDTO.id),
      name: new FormControl(stepInfoDTO.name),
      StepTextDTO: new FormGroup({
        text: new FormControl(stepInfoDTO.stepTextDTO.text)
      })
    })
  }

  initNewStepSection() {
    return new FormGroup({
      id: new FormControl(0),
      name: new FormControl(''),
      StepTextDTO: new FormGroup({
        text: new FormControl('')
      })
    })
  }

  putNewInstruction(inst: InstructionDTO) {
    this._appService.putResource(this.createInstURL, inst).subscribe(
      data => {
        this.showTab(data)
        this._home.showTextMessageSuccess('The instruction "' + data.name + '" is successfully save')
      },
      (error) => {
        for (var i = 0; i < error.error.messages.length; i++) {
          this._home.showTextMessageErr(error.error.messages[i])
        }
      }
    )
  }

  putStep(stepInfoDTO: StepInfoDTO) {
    this._appService.putResource(this.stepURL, stepInfoDTO).subscribe(
      (data) => this._home.showTextMessageSuccess('The step "' + data.name + '" is successfully save'),
      (error) => {
        for (var i = 0; i < error.error.messages.length; i++) {
          this._home.showTextMessageErr(error.error.messages[i])
        }
      }
    )
  }

  showTab(inst: InstructionDTO) {
    this.instructionInfoForm.get("id").setValue(inst.id, { emitEvent: true });
    this._home.addNewStepViewerWithoutChangePage(inst);
  }

  addSteps() {
    const control = <FormArray>this.contactForm.get('sections');
    control.push(this.initNewStepSection());
  }

  getSteps(form) {
    return form.controls.sections.controls;
  }

  onSubmitStep(index, value) {
    console.log(value)
    let stepTextDTO: StepTextDTO = { text: value.StepTextDTO.value.text };
    let stepInfoDTO: StepInfoDTO = {
      id: value.id.value,
      name: value.name.value, stepPlace: index, infoId: this.instructionInfoForm.value.id,
      stepTextDTO: stepTextDTO
    };
    this.putStep(stepInfoDTO);
  }


  createInstruction(instruction) {
    let construct: ConstructDTO = { name: instruction.constructDTO.value.name, value: instruction.constructDTO.value.value };
    let instructionInfo: InstructionDTO = {
      id: instruction.id.value, name: instruction.name.value,
      description: instruction.description.value, levelOwner: instruction.instructionLevel.value, constructDTO: construct
    };
    this.putNewInstruction(instructionInfo);
  }

  isInstructionCreate() {
    if (this.instructionInfoForm.value.id == '0') {
      return false;
    } else {
      return true;
    }
  }


  flipNewInst() {

    if (document.getElementById("flipNewInst").style.transform == 'rotateY(180deg)') {
      document.getElementById("flipNewInst").style.transform = 'rotateY(0deg)'
    } else {
      document.getElementById("flipNewInst").style.transform = 'rotateY(180deg)'
    }

  }

  flipCard(id) {

    if (document.getElementById("flipNewStep" + id).style.transform == 'rotateY(180deg)') {
      document.getElementById("flipNewStep" + id).style.transform = 'rotateY(0deg)'
    } else {
      document.getElementById("flipNewStep" + id).style.transform = 'rotateY(180deg)'
    }

  }




}
