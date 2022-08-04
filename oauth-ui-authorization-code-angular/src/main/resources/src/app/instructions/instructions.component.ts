import { Component, OnInit } from '@angular/core';
import { InstructionDTO, InstructStepService } from 'app/services/InstructStepsService';
import { AppService } from '../app/app.service';
import { HomeComponent } from '../home/home.component';


@Component({
  selector: 'app-instructions',
  templateUrl: './instructions.component.html',
  styleUrls: ['./instructions.component.css'],
})


export class InstructionsComponent implements OnInit {


  private listInstructionsUrl = 'http://localhost:8089/instruction-server/api/v1/instructions';
  public instructions: Array<InstructionDTO> = []
  prepareForDel: InstructionDTO = new InstructionDTO(null, 'UNKNOWN', 'UNKNOWN', null, null);



  constructor(private _service: AppService, private _home: HomeComponent,
    private _shareInstStepServ: InstructStepService) {
    this._shareInstStepServ.getInstructionsTrigger.subscribe(() => {
      this.getInstructions();
    })

  }

  ngOnInit(): void {
    this.getInstructions()
  }

  openRedactionInstruction(instruction: InstructionDTO) {
    this._home.addNewStepViewerWithoutChangePage(instruction);
    this._home.showTab('createNewInstructions')
    this._shareInstStepServ.redactionInstructionTrigger.next(instruction)
  }

  deleteInstruction() {
    document.getElementById("myModal").style.display = "none";
    this._service.delResource(this.listInstructionsUrl, this.prepareForDel.id).subscribe(
      () => { this.getInstructions(), this._home.showTextMessageSuccess(' delete the instruction "' + this.prepareForDel.name) + '"' },
      (error) => {
        for (var i = 0; i < error.error.messages.length; i++) {
          this._home.showTextMessageErr(error.error.messages[i])
        }
      }
    );

  }

  openDelInstructWindow(instruction: InstructionDTO) {
    document.getElementById("myModal").style.display = "block";
    this.prepareForDel = instruction;
  }

  showStepsInstruction(instruct: InstructionDTO) {
    this._home.addNewStepViewer(instruct);
    this._shareInstStepServ.getStepsEventSubject.next(instruct.id);
  }

  getInstructions() {
    this._service.getResource(this.listInstructionsUrl)
      .subscribe(
        data => this.instructions = data,
        (error) => {
          for (var i = 0; i < error.error.messages.length; i++) {
            this._home.showTextMessageErr(error.error.messages[i])
          }
        }
      );
  }

  flipCard(id) {
    if (document.getElementById("flipInst" + id).style.transform == 'rotateY(180deg)') {
      document.getElementById("flipInst" + id).style.transform = 'rotateY(0deg)'
    } else {
      document.getElementById("flipInst" + id).style.transform = 'rotateY(180deg)'
    }
  }

}

