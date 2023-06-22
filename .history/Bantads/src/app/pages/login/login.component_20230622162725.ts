import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery';
import 'bootstrap';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { IUserLogin } from 'src/app/DTOs/IUser';
import { response } from 'express';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss', '../../../_utils.scss'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.loginForm = new FormGroup({
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });

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
            /^([a-zA-Z0-9_\-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/,
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

  login() {
    const login: IUserLogin = {
      email: this.loginForm.value.email,
      senha: this.loginForm.value.password,
    }
    this.authService.login2(login).subscribe(() => {
      () => {
        // Caso de sucesso (resposta OK)
        this.router.navigate(['/']);
      },
      error => {
        // Caso de erro
        alert('Erro ao fazer login');
      }
    });
  }
}
