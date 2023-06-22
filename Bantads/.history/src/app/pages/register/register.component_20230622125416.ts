import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { consultarCep } from 'correios-brasil';
import { ClientService } from '../../services/client.service';
import { AuthService } from '../../services/auth.service';
import { IUser } from '../../DTOs/IUser';
import { IUserAutocadastro } from 'src/app/DTOs/IUserAutocadastro';

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

  registerForm = this.formBuilder.group(
    {
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: [{ value: '', disabled: true }],
      role: ['user'],
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
      city: [{ value: '', disabled: true }],
      address: [{ value: '', disabled: true }],
      neighborhood: [{ value: '', disabled: true }],
      state: [{ value: '', disabled: true }],
      complement: ['', Validators.required],
    },
    { validators: [] },
  );

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AuthService,
    private clientService: ClientService,
    private router: Router,
  ) {}

  checkPasswords(group: FormGroup) {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('passwordConfirmation')?.value;
    console.log(password === confirmPassword ? 'okok' : 'burro');
    return password === confirmPassword ? null : { notSame: true };
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

  async register() {
    console.log(this.registerForm.valid);

    if (this.registerForm.valid)
      try {
        const user: IUserAutocadastro = {
          nome: this.registerForm.value.name || '',
          email: this.registerForm.value.email || '',
          tipo: this.registerForm.value.role || '',
          cpf: this.registerForm.value.cpf || '',
          telefone: this.registerForm.value.phone || '',
          salario:  Number(this.registerForm.value.salary) || 0,
          cep: this.registerForm.value.cep || '',
          numero: Number(this.registerForm.value.number) || 0,
          estado: this.state || '',
          cidade: this.city || '',
          logradouro: this.address || '',
          complemento: this.registerForm.value.complement  || '',
        };
        await this.accountService.createAccount2(user).subscribe({
          next: response => this.router.navigate(['/login']),
          error: error => console.log(error),
        });
      } catch (err) {
        console.log(err);
        this.this.router.navigate(['/login'])
      }
  }

  async register2() {
    console.log(this.registerForm.valid);

    if (this.registerForm.valid)
      try {
        const user: IUser = {
          nome: this.registerForm.value.name || '',
          email: this.registerForm.value.email || '',
          senha: this.registerForm.value.password || '',
          tipo: this.registerForm.value.role || 'user',
          cpf: this.registerForm.value.cpf || '',
          telefone: this.registerForm.value.phone || '',
          salario: this.registerForm.value.salary || '',
          cep: this.registerForm.value.cep || '',
          numero: this.registerForm.value.number || '',
          estado: this.state || '',
          cidade: this.city || '',
          logradouro: this.address || '',
        };
        await this.accountService.createAccount(user).subscribe({
          next: response => this.router.navigate(['/login']),
          error: error => console.log(error),
        });
      } catch (err) {
        console.log(err);
      }
  }
}
