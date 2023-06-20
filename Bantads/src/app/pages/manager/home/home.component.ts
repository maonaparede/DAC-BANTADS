import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'manager-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss', '../../../../_utils.scss']
})
export class ManagerHomeComponent {
  inputValue = '';

  constructor(private http: HttpClient) {}

  getWaiting() {
    this.http.get('http://localhost:3000/api/waiting').subscribe(
        (response) => {
          // Manipule a resposta aqui
        },
        (error) => {
          // Manipule o erro aqui
        }
    );
  }

  doDeny() {
    this.http.post('http://localhost:3000/api/deny', { value: this.inputValue }).subscribe(
        (response) => {
          // Manipule a resposta aqui
        },
        (error) => {
          // Manipule o erro aqui
        }
    );
  }

  doApprove() {
    this.http.post('http://localhost:3000/api/approve', { value: this.inputValue }).subscribe(
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


  showModal = true;
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
