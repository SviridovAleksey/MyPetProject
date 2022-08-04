import { Component, Injectable, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AppService } from 'app/app/app.service';
import { HomeComponent } from 'app/home/home.component';
import { InstructionDTO, InstructStepService, LevelsDTO } from 'app/services/InstructStepsService';


@Injectable()
export class InnersInstLevStorage {
  constructor(
    public level: Array<LevelsDTO>,
    public instructions: Array<InstructionDTO>) { }
}

@Component({
  selector: 'app-levels',
  templateUrl: './levels.component.html',
  styleUrls: ['./levels.component.css']
})
export class LevelsComponent implements OnInit {

  private redactLevel: LevelsDTO;
  private listInstructionsUrl = 'http://localhost:8089/instruction-server/api/v1/instructions';
  public instructions: Array<InstructionDTO> = []
  public noSortInstructions: Array<InstructionDTO> = []
  prepareForDel: InstructionDTO = new InstructionDTO(null, 'UNKNOWN', 'UNKNOWN', null, null);
  private levelsUrl: String = 'http://localhost:8089/instruction-server/api/v1/levels';
  public levels: Map<Number, Map<LevelsDTO, InnersInstLevStorage>> = new Map();
  public showLevel: Map<LevelsDTO, InnersInstLevStorage> = new Map();
  public historyShow: Array<LevelsDTO> = new Array();
  levelInfoForm: FormGroup;


  constructor(private _service: AppService, private _home: HomeComponent,
    private _shareInstStepServ: InstructStepService) {
    this._shareInstStepServ.getInstructionsTrigger.subscribe(() => {
      this.getInstructions();
    })

  }

  ngOnInit(): void {
    this.getInstructions()
    this.createLevelForm()
  }

  clearAll() {
    this.instructions.length = 0;
    this.noSortInstructions.length = 0;
    this.levels.clear();
    this.showLevel.clear();
    this.historyShow.length = 0;
  }

  createLevelForm() {
    this.levelInfoForm = new FormGroup({
      id: new FormControl(''),
      name: new FormControl(''),
      owner: new FormControl(''),
      levelPlace: new FormControl('')
    })
  }

  fillLevelForm(level: LevelsDTO) {
    this.levelInfoForm.get("id").setValue(level.id, { emitEvent: true });
    this.levelInfoForm.get("name").setValue(level.name, { emitEvent: true });
    this.levelInfoForm.get("owner").setValue(level.owner, { emitEvent: true });
    this.levelInfoForm.get("levelPlace").setValue(level.levelPlace, { emitEvent: true });
  }

  sortInstructions(inst: Array<InstructionDTO>) {
    for (var i = 0; i < inst.length; i++) {
      if (inst[i].levelOwner === 0) {
        this.noSortInstructions.push(inst[i])
      } else {
        this.instructions.push(inst[i])
      }
    }
    this.getLevels();
  }


  sortLevels(levels: Array<LevelsDTO>) {
    let sortLevels: Set<number> = new Set();
    for (var i = 0; i < levels.length; i++) {
      sortLevels.add(levels[i].levelPlace)
    }
    for (let place of sortLevels) {
      this.levels.set(place, this.getLevelsByPlace(place, levels))
    }
    this.setMainLevel(0);
  }

  getLevelsByPlace(place: number, levels: Array<LevelsDTO>) {
    let retMap: Map<LevelsDTO, InnersInstLevStorage> = new Map();
    for (var i = 0; i < levels.length; i++) {
      if (levels[i].levelPlace === place) {
        retMap.set(levels[i], new InnersInstLevStorage(this.getSlaveLevels(levels[i], levels), this.getInstructionsByLevel(levels[i])))
      }
    } return retMap;
  }

  getSlaveLevels(level: LevelsDTO, levels: Array<LevelsDTO>) {
    let retArr: Array<LevelsDTO> = new Array();
    for (var i = 0; i < levels.length; i++) {
      if (level.id == levels[i].owner) {
        retArr.push(levels[i]);
      }
    } return retArr;

  }

  getInstructionsByLevel(level: LevelsDTO) {
    let arrRet: Array<InstructionDTO> = new Array();
    for (var i = 0; i < this.instructions.length; i++) {
      if (this.instructions[i].levelOwner == level.id) {
        arrRet.push(this.instructions[i])
      }
    } return arrRet;
  }

  saveNewLevel(level: LevelsDTO) {
    for (let lev of this.levels.get(level.levelPlace).keys()) {
      if (lev.id === level.id) {
        this.saveUpdateLevel(lev, level)
        return;
      }
    }
    this.levels.get(level.levelPlace).set(level, new InnersInstLevStorage([], []))
    if (level.levelPlace === 0) {
      this.setMainLevel(0)
    } else {
      for (let lev of this.levels.get(level.levelPlace - 1).keys()) {
        if (lev.id === level.owner) {
          for (let updateLev of this.levels.get(level.levelPlace - 1).get(lev).level) {
            if (updateLev.id === level.id) {
              this.saveUpdateLevel(updateLev, level)
              return;
            }
          }
          this.levels.get(level.levelPlace - 1).get(lev).level.push(level)
        }
      }
      this.openLevel(level)
    }
  }

  private saveUpdateLevel(oldLev: LevelsDTO, newLev: LevelsDTO) {
    oldLev.name = newLev.name
    oldLev.owner = newLev.owner
    oldLev.levelPlace = newLev.levelPlace
  }

  setMainLevel(levelNumber: number) {
    this.showLevel.clear()
    for (let level of this.levels.get(levelNumber).keys()) {
      this.showLevel.set(level, this.levels.get(levelNumber).get(level))
    }
  }

  openLevel(level: LevelsDTO) {
    this.showLevel.clear()
    this.showLevel.set(level, this.levels.get(level.levelPlace).get(level))
    this.historyShow.push(level);
  }

  turnBackShowLevel() {
    this.historyShow.length = this.historyShow.length - 1;
    if (this.historyShow.length < 1) {
      this.setMainLevel(0);
    } else {
      this.openLevel(this.historyShow[this.historyShow.length - 1])
      this.historyShow.length = this.historyShow.length - 1;
    }
  }

  isHistoryExist() {
    if (this.historyShow.length > 0) {
      return true;
    }
  }


  openRedactionInstruction(instruction: InstructionDTO) {
    this._home.addNewStepViewerWithoutChangePage(instruction);
    this._home.showTab('createNewInstructions')
    this._shareInstStepServ.redactionInstructionTrigger.next(instruction)
  }

  deleteInstruction() {
    document.getElementById("myModal").style.display = "none";
    this._service.delResource(this.listInstructionsUrl, this.prepareForDel.id).subscribe(
      () => { this._home.showTextMessageSuccess(' delete the instruction "' + this.prepareForDel.name) + '"' },
      (error) => {
        for (var i = 0; i < error.error.messages.length; i++) {
          this._home.showTextMessageErr(error.error.messages[i])
        }
      }
    );
  }

  openDelInstructWindow(instruction: InstructionDTO) {
    document.getElementById("myModalF").style.display = "block";
    this.prepareForDel = instruction;
  }

  addMainFolder() {
    let newLevel: LevelsDTO = new LevelsDTO(0, 'New Folder', 0, 0);
    this.putLevel(newLevel);
  }

  addFolder(level: LevelsDTO) {
    let newLevel: LevelsDTO = new LevelsDTO(0, 'New Folder', level.id, level.levelPlace + 1);
    this.putLevel(newLevel);
  }


  addSteps(idOwner: number) {
    this._home.showTab('createNewInstructions');
    this._shareInstStepServ.newInstructionByLevel.next(idOwner)
  }

  showStepsInstruction(instruct: InstructionDTO) {
    this._home.addNewStepViewer(instruct);
    this._shareInstStepServ.getStepsEventSubject.next(instruct.id);
  }

  getInstructions() {
    this.clearAll();
    this._service.getResource(this.listInstructionsUrl)
      .subscribe(
        data => this.sortInstructions(data),
        (error) => {
          for (var i = 0; i < error.error.messages.length; i++) {
            this._home.showTextMessageErr(error.error.messages[i])
          }
        }
      );
  }

  getLevels() {
    this._service.getResource(this.levelsUrl)
      .subscribe(
        data => this.sortLevels(data),
        (error) => {
          for (var i = 0; i < error.error.messages.length; i++) {
            this._home.showTextMessageErr(error.error.messages[i])
          }
        }
      );
  }

  putLevel(level: LevelsDTO) {
    this._service.putResource(this.levelsUrl, level).subscribe(
      data => {
        this.saveNewLevel(data)
        this._home.showTextMessageSuccess('The folder "' + data.name + '" is successfully save')
      },
      (error) => {
        for (var i = 0; i < error.error.messages.length; i++) {
          this._home.showTextMessageErr(error.error.messages[i])
        }
      }
    )

  }

  activateRedactFolderName(i, j) {
    let arrForm = document.getElementsByClassName('formFolderName')
    let arrDef = document.getElementsByClassName('defaultNameFolderContainer')
    for (var k = 0; k < arrForm.length; k++) {
      if (arrForm[k].id !== "TextAreaFolder" + i + j) {
        document.getElementById(arrForm[k].id).hidden = true
        document.getElementById(arrDef[k].id).hidden = false
      }
    }
    if (document.getElementById("TextAreaFolder" + i + j).hidden == true) {
      document.getElementById("TextAreaFolder" + i + j).hidden = false
      document.getElementById("nameFolder" + i + j).hidden = true
    } else {
      document.getElementById("TextAreaFolder" + i + j).hidden = true
      document.getElementById("nameFolder" + i + j).hidden = false
    }
  }


  openRedactionLevelName(level: LevelsDTO) {
    let updateLevel: LevelsDTO = Object.assign({}, this.getLevelFromForm());
    this.fillLevelForm(level);
    if (this.redactLevel == undefined) {
      this.redactLevel = Object.assign({}, this.getLevelFromForm());
      return;
    }
    if (this.redactLevel.id !== level.id) {
      this.putNewLevel(updateLevel, this.redactLevel)
      this.redactLevel = level
    } else {
      this.putNewLevel(updateLevel, level)
    }

  }

  private getLevelFromForm() {
    return new LevelsDTO(this.levelInfoForm.get('id').value,
      this.levelInfoForm.get('name').value,
      this.levelInfoForm.get('owner').value,
      this.levelInfoForm.get('levelPlace').value);
  }

  private putNewLevel(updateLevel: LevelsDTO, level: LevelsDTO) {
    if (level.name !== updateLevel.name || level.owner !== updateLevel.owner || level.levelPlace !== updateLevel.levelPlace) {
      this.putLevel(updateLevel);
    }
  }


  openCloseCollaps(id) {
    for (var i = 0; i < this.showLevel.size; i++) {
      if (i !== id) {
        document.getElementById("collaps" + i).style.display = "none";
      }
    }
    if (document.getElementById("collaps" + id).style.display === "block") {
      document.getElementById("collaps" + id).style.display = "none";
    } else {
      document.getElementById("collaps" + id).style.display = "block";
    }
  }

  flipCardF(id) {
    if (document.getElementById("flipInstF" + id).style.transform == 'rotateY(180deg)') {
      document.getElementById("flipInstF" + id).style.transform = 'rotateY(0deg)'
    } else {
      document.getElementById("flipInstF" + id).style.transform = 'rotateY(180deg)'
    }
  }

  flipCardNoSort(id) {
    if (document.getElementById("flipNoSort" + id).style.transform == 'rotateY(180deg)') {
      document.getElementById("flipNoSort" + id).style.transform = 'rotateY(0deg)'
    } else {
      document.getElementById("flipNoSort" + id).style.transform = 'rotateY(180deg)'
    }
  }

}