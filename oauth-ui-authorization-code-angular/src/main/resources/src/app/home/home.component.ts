import { Component } from '@angular/core';
import { InstructionDTO, InstructStepService } from 'app/services/InstructStepsService';
import { AppService } from '../app/app.service'

@Component({
    selector: 'home-header',
    providers: [AppService],
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})

export class HomeComponent {


    public isLoggedIn = false;
    public tabs: Array<{ key: String, value: Object }> = [
        { key: 'homePage', value: true },
        { key: 'fooPage', value: false },
        { key: 'instructionPage', value: false },
        { key: 'stepsPage', value: false },
        { key: 'createNewInstructions', value: false },
        { key: 'levels', value: false }
    ]
    public singleInstruction: Array<{ key: InstructionDTO, value: boolean }> = [];
    public messageText: Array<{ text: String, type: String }> = []


    constructor(private _service: AppService, private _shareInstStepServ: InstructStepService) {


    }

    createNewInstruction(instructionInfo) {
        let instInfo: InstructionDTO = instructionInfo;
        if (instInfo.name == '') {
            instInfo.name = "New instruction";
        }
        this.showNewStepViewer(instInfo);

    }

    ngOnInit() {
        this.isLoggedIn = this._service.checkCredentials();
        let i = window.location.href.indexOf('code');
        if (!this.isLoggedIn && i != -1) {
            this._service.retrieveToken(window.location.href.substring(i + 5));
        }
    }

    login() {
        window.location.href = 'http://localhost:8083/auth/realms/sviridov/protocol/openid-connect/auth?response_type=code&&scope=openid%20write%20read&client_id=' +
            this._service.clientId + '&redirect_uri=' + this._service.redirectUri;
    }

    logout() {
        this._service.logout();
    }

    showTab(tab) {
        for (var i = 0; i < this.tabs.length; i++) {
            if (this.tabs[i].key == tab) {
                if (this.tabs[i].key == 'instructionPage' || this.tabs[i].key == 'levels') {
                    this._shareInstStepServ.getInstructionsTrigger.next();
                }
                if (this.tabs[i].key == 'createNewInstructions') {
                    this._shareInstStepServ.clearNewInstructionFormTrigger.next();
                }
                this.tabs[i].value = true;
            } else {
                this.tabs[i].value = false;
            }
        }
    }

    openCloseNav() {
        if (document.getElementById("mySidebar").style.width == '250px') {
            document.getElementById("mySidebar").style.width = '0'
        } else {
            document.getElementById("mySidebar").style.width = '250px'
        }
    }

    closeStepViewer(i) {
        this.singleInstruction.splice(i, 1);
        this.showTab('levels');
    }

    addNewStepViewer(instruct: InstructionDTO) {
        this.addStepsViewer(instruct);
        this.showTab('stepsPage');
    }

    addNewStepViewerWithoutChangePage(instruct: InstructionDTO) {
        this.addStepsViewer(instruct);
    }

    addStepsViewer(instruct: InstructionDTO) {
        if (!this.forEachStepViewer(instruct)) {
            this.singleInstruction.unshift({ key: instruct, value: true });
        }
        if (this.singleInstruction.length > 5) {
            this.singleInstruction.length = 5;
        }
    }

    showNewStepViewer(instruct: InstructionDTO) {
        this.forEachStepViewer(instruct);
        this._shareInstStepServ.getStepsEventSubject.next(instruct.id);
        this.showTab('stepsPage');
    }

    forEachStepViewer(instruct: InstructionDTO) {
        let isExist: boolean = false;
        for (var i = 0; i < this.singleInstruction.length; i++) {
            if (this.singleInstruction[i].key.id == instruct.id) {
                this.singleInstruction[i].key.name = instruct.name
                this.singleInstruction[i].value = true;
                isExist = true;
            }
        } return isExist;
    }

    closeNewStepViewer(name) {
        for (var i = 0; i < this.singleInstruction.length; i++) {
            if (this.singleInstruction[i].key.name == name) {
                this.singleInstruction[i].value = false;
            }
        }
    }

    closeMessage(id) {
        if (this.messageText.length < 2) {
            this.messageText.length = 0;
        } else {
            this.messageText.splice(id, 1);
        }
    }

    showMessage(message: String, type: String) {
        this.messageText.push({ text: message, type: type });
        this.hideMessage(this.messageText.findIndex(x => x.text == message && x.type == type))
    }

    showTextMessageErr(message: String) {
        this.showMessage(message, 'error')
    }

    showTextMessageSuccess(message: String) {
        this.showMessage(message, 'success')
    }

    showTextMessageMessage(message: String) {
        this.showMessage(message, 'message')
    }

    hideMessage(id) {
        setTimeout(() => {
            this.closeMessage(id)
        }, 3000)
    }


}
