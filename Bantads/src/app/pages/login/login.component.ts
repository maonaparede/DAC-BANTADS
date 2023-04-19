import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery';
import 'bootstrap';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss', '../../../_utils.scss'],
})
export class LoginComponent implements OnInit {
  isSubmitted: boolean = false;
  isValidUser: boolean = false;
  loginForm: FormGroup = new FormGroup({
    email: new FormControl(null, Validators.required),
    password: new FormControl(null, Validators.required),
  });

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    $('.input100').each(function (_index: number, element: HTMLElement) {
      const input = element as HTMLInputElement;
      input.addEventListener('blur', function () {
        if (input.value.trim() !== '') {
          input.classList.add('has-val');
        } else {
          input.classList.remove('has-val');
        }
      });
    });

    const input = $('.validate-input .input100');

    $('.validate-form').on('submit', function () {
      let check = true;

      for (let i = 0; i < input.length; i++) {
        if (!validate(input[i])) {
          showValidate(input[i]);
          check = false;
        }
      }

      return check;
    });

    $('.validate-form .input100').each(function () {
      $(this).focus(function () {
        hideValidate(this);
      });
    });

    function validate(input: any) {
      const value = $(input).val();

      if (
        input.name === 'email' &&
        typeof value === 'string' &&
        value
          .trim()
          .match(
            /^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/,
          ) == null
      ) {
        return false;
      }

      return !(
        input.name === 'password' &&
        typeof value === 'string' &&
        (value.trim().length < 6 || value.trim().length > 18)
      );
    }

    function showValidate(input: any) {
      const thisAlert = $(input).parent();

      $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input: any) {
      const thisAlert = $(input).parent();

      $(thisAlert).removeClass('alert-validate');
    }

    let showPass = 0;
    $('.btn-show-pass').on('click', function () {
      if (showPass == 0) {
        $(this).next('input').attr('type', 'text');
        $(this).find('i').removeClass('zmdi-eye');
        $(this).find('i').addClass('zmdi-eye-off');
        showPass = 1;
      } else {
        $(this).next('input').attr('type', 'password');
        $(this).find('i').addClass('zmdi-eye');
        $(this).find('i').removeClass('zmdi-eye-off');
        showPass = 0;
      }
    });
  }

  async login() {
    if (this.loginForm.invalid) {
      return false;
    } else {
      try {
        await this.authService.login(this.loginForm);

        return this.router.navigate(['']);
      } catch (err) {
        return console.error(err);
      }
    }
  }
}
