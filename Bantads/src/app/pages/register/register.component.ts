import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { consultarCep } from 'correios-brasil';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  address = '';
  neighborhood = '';
  city = '';
  state = '';

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {}

  get f() {
    return this.registerForm.controls;
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [
        Validators.required,
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$/),
      ]),
      passwordConfirmation: new FormControl('', [
        Validators.required,
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$/),
      ]),
      role: new FormControl('User', Validators.required),
      cpf: new FormControl('', [Validators.required]),
      phone: new FormControl('', [Validators.required]),
      salary: new FormControl('', Validators.required),
      cep: new FormControl('', [
        Validators.required,
        Validators.pattern(/^(\d{2}\.)\d{3}\-\d{3}$/),
        Validators.minLength(10),
        Validators.maxLength(10),
      ]),
      number: new FormControl('', Validators.required),
      complement: new FormControl(null),
      state: new FormControl(this.state),
      city: new FormControl(this.city),
      neighborhood: new FormControl(this.neighborhood),
      address: new FormControl(this.address),
    });
  }

  async signUp() {
    try {
      await this.authService.createAccount(this.registerForm);

      this.router.navigate(['']);
    } catch (err) {
      console.error(err);
    }
  }

  getAddress(cep: string) {
    cep = cep.replace(/\D/g, '');
    consultarCep(cep).then(response => {
      this.neighborhood = response.bairro;
      this.address = response.logradouro;
      this.city = response.localidade;
      this.state = response.uf;
    });
  }
}
