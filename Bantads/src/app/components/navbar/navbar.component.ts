import { Component } from '@angular/core';
import { IUser } from '../../DTOs/IUser';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss',' ./../../../../_utils.scss'],
})
export class NavbarComponent {
  currentUser: IUser | undefined;

  constructor(private authService: AuthService) {
    this.currentUser = this.authService.getCurrentUser();
  }

  logOut() {
    this.authService.logout();
    window.location.reload();
  }

  showModal = false;

  openModal() {

    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  modalStatus(){
    return this.showModal;
  }
}
