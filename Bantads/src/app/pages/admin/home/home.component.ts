import { Component } from '@angular/core';

@Component({
  selector: 'admin-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss', '../../../../_utils.scss']
})
export class AdminHomeComponent {

  showModal = true;
  add = false;
  profile = true;


  openModal(show: number) {
    switch(show){
      case 1: this.add = true; break;
      case 2: this.profile = true; break;
    }
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
    this.add = false;
    this.profile = false;
  }

  returnModal() {
    this.showModal = true;
    this.add = true;
    this.profile = false
  }

  modalStatus(){
    return this.showModal;
  }

}
