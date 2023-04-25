import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { consultarCep } from 'correios-brasil';
import { ClientService } from '../../services/client.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss', './../../../_utils.scss'],
})
export class RegisterComponent {
  address = '';
  neighborhood = '';
  city = '';
  state = '';

  registerForm = this.formBuilder.group({
    name: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: [
      '',
      [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$/)],
    ],
    passwordConfirmation: [
      '',
      [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$/)],
    ],
    role: ['user', Validators.required],
    cpf: ['', [Validators.required]],
    phone: ['', [Validators.required]],
    salary: ['', Validators.required],
    cep: [
      '',
      [
        Validators.required,
        Validators.pattern(/^(\d{2}\.)\d{3}-\d{3}$/),
        Validators.minLength(10),
        Validators.maxLength(10),
      ],
    ],
    number: ['', Validators.required],
    complement: [null],
    state: [this.state],
    city: [this.city],
    neighborhood: [this.neighborhood],
    address: [this.address],
  });

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AuthService,
    private clientService: ClientService,
    private router: Router,
  ) {}

  getAddress(cep: string) {
    cep = cep.replace(/\D/g, '');
    consultarCep(cep).then(response => {
      this.neighborhood = response.bairro;
      this.address = response.logradouro;
      this.city = response.localidade;
      this.state = response.uf;
    });
  }

  async register() {
    try {
      await this.accountService.createAccount(this.registerForm);
    } catch (err) {
      console.log(err);
    }
  }
}
