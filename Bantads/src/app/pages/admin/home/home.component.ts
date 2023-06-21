import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-admin-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss', '../../../../_utils.scss']
})
export class AdminHomeComponent {

  addManagerForm = new FormGroup({
    nome: new FormControl(''),
    email: new FormControl(''),
    senha: new FormControl(''),
    confirmarSenha: new FormControl(''),
    cpf: new FormControl(''),
    telefone: new FormControl(''),
    salario: new FormControl(''),
    cep: new FormControl(''),
    endereco: new FormControl(''),
    numero: new FormControl(''),
    complemento: new FormControl(''),
    cidade: new FormControl(''),
    estado: new FormControl(''),
  });

  updateProfileForm = new FormGroup({
    nome: new FormControl(''),
    email: new FormControl(''),
    senha: new FormControl(''),
    confirmarSenha: new FormControl(''),
    cpf: new FormControl(''),
    telefone: new FormControl(''),
    salario: new FormControl(''),
    cep: new FormControl(''),
    endereco: new FormControl(''),
    numero: new FormControl(''),
    complemento: new FormControl(''),
    cidade: new FormControl(''),
    estado: new FormControl(''),
  });

  constructor(private http: HttpClient) {}
  addManager() {
    this.http.post('http://localhost:3000/api/waiting', {}).subscribe(
        (response) => {
          // Manipule a resposta aqui
        },
        (error) => {
          // Manipule o erro aqui
        }
    );
  }

  updateProfileManager() {
    this.http.post('http://localhost:3000/api/waiting', {}).subscribe(
        (response) => {
          // Manipule a resposta aqui
        },
        (error) => {
          // Manipule o erro aqui
        }
    );
  }

  getManager() {
    this.http.get('http://localhost:3000/api/deny').subscribe(
        (response) => {
          // Manipule a resposta aqui
        },
        (error) => {
          // Manipule o erro aqui
        }
    );
  }

  getManagers() {
    this.http.post('http://localhost:3000/api/approve', {}).subscribe(
        (response) => {
          // Manipule a resposta aqui
        },
        (error) => {
          // Manipule o erro aqui
        }
    );
  }

  getClientProfile() {
    this.http.get('http://localhost:3000/api/client-profile').subscribe(
        (response) => {
          // Manipule a resposta aqui
        },
        (error) => {
          // Manipule o erro aqui
        }
    );
  }

  getClients() {
    this.http.get('http://localhost:3000/api/clients').subscribe(
        (response) => {
          // Manipule a resposta aqui
        },
        (error) => {
          // Manipule o erro aqui
        }
    );
  }


  showModal = false;
  add = false;
  list = false;
  deny = false;
  profile = true;

  openModal(show: number) {
    switch (show) {
      case 1:
        this.list = true;
        break;
      case 2:
        this.deny = true;
        this.list = false;
        break;
      case 3:
        this.profile = true;
        break;
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
    this.deny = false;
  }

  modalStatus() {
    return this.showModal;
  }
}
