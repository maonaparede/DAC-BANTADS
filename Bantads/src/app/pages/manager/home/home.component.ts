import { Component } from '@angular/core';

@Component({
  selector: 'manager-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss', '../../../../_utils.scss']
})
export class ManagerHomeComponent {

  showModal = true;
  list = false;
  deny = false;
  profile = true;


  openModal(show: number) {
    switch(show){
      case 1: this.list = true; break;
      case 2: this.deny = true; this.list = false; break;
      case 3: this.profile = true; break;
    }
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
    this.list = false;
    this.deny = false;
    this.profile = false;
  }

  returnModal() {
    this.list = true;
    this.showModal = true;
    this.deny = false
  }

  modalStatus(){
    return this.showModal;
  }
}

