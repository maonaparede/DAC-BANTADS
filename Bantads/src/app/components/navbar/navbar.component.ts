import { Component } from '@angular/core';
import { IUser } from '../../DTOs/IUser';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
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
}
