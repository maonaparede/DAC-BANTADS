import { Component } from '@angular/core';
import { ModalComponent } from 'src/app/components/modal/modal.component';
import { AuthService } from '../../../services/auth.service';
import { HttpClient } from '@angular/common/http';
import { IUser } from '../../../DTOs/IUser';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-client-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss', './../../../../_utils.scss'],
})
export class ClientHomeComponent {
  currentUser: IUser | undefined;
  depositValue: number;
  valorSaque: number;
  contaDestino: string;
  valorTransferencia: number;

  depositoForm = new FormGroup({
    depositValue: new FormControl(''),
  });

  saqueForm = new FormGroup({
    valorSaque: new FormControl(''),
  });

  transferenciaForm = new FormGroup({
    contaDestino: new FormControl(''),
    valorTransferencia: new FormControl(''),
  });

  constructor(private authService: AuthService, private http: HttpClient) {
    this.currentUser = this.authService.getCurrentUser();
  }

  getUser() {
    this.http.get('http://localhost:3000/user').subscribe((response) => {
      // Manipule a resposta aqui
    }, (error) => {
      // Manipule o erro aqui
    });
  }

  getExtrato() {
    this.http.get('http://localhost:3000/extrato').subscribe((response) => {
      // Manipule a resposta aqui
    }, (error) => {
      // Manipule o erro aqui
    });
  }

  doDeposito() {
    const data = {
      "id": null,
      "dataTempo": null,
      "valor": this.depositoForm.value.depositValue,
      "operacao": "D",
      "paraUser": 1,
      "deUser": null
    }
    this.http.post('http://localhost:5003/api/user/op', data).subscribe(
        (response) => {
          // Manipule a resposta aqui
        },
        (error) => {
          // Manipule o erro aqui
        }
    );
  }

  doSaque() {
    const data = { valor: this.valorSaque }; // Insira os dados do formulário que deseja enviar para o backend

    this.http.post('http://localhost:3000/saque', data).subscribe(
        (response) => {
          // Manipule a resposta aqui
        },
        (error) => {
          // Manipule o erro aqui
        }
    );
  }

  doTransferencia() {
    const data = {
      contaDestino: this.contaDestino,
      valor: this.valorTransferencia
    }; // Insira os dados do formulário que deseja enviar para o backend

    this.http.post('http://localhost:3000/transferencia', data).subscribe(
        (response) => {
          // Manipule a resposta aqui
        },
        (error) => {
          // Manipule o erro aqui
        }
    );
  }


  showModal = false;
  deposito = false;
  saque = false;
  transferencia = false;
  extrato = true;

  openModal(show: number) {
    switch (show) {
      case 1:
        this.deposito = true;
        break;
      case 2:
        this.saque = true;
        break;
      case 3:
        this.transferencia = true;
        break;
      case 4:
        this.extrato = true;
        this.getExtrato();
        break;
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

  modalStatus() {
    return this.showModal;
  }
}
