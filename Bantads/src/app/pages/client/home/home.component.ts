import { Component } from '@angular/core';
import { ModalComponent } from 'src/app/components/modal/modal.component';

@Component({
  selector: 'client-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})

export class ClientHomeComponent {

  showModal = false;
  deposito = false;
  saque = false;
  transferencia = false;
  extrato = false;

  openModal(show: number) {
    switch(show){
      case 1: this.deposito = true; break;
      case 2: this.saque = true; break;
      case 3: this.transferencia = true; break;
      case 4: this.extrato = true; break;
    }
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
    this.deposito = false;
    this.saque = false;
    this.transferencia = false;
    this.extrato = false;
  }

  modalStatus(){
    return this.showModal;
  }
}

