import {Component} from '@angular/core';
import {consultarCep} from 'correios-brasil';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import {Router} from '@angular/router';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  form: FormGroup;
  street = ''
  neighborhood = ''
  city = ''
  state = ''
  constructor(
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.form = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      // password: new FormControl('', [
      //   Validators.required,
      //   Validators.pattern(
      //     /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$/
      //   ),
      // ]),
      // passwordConfirmation: new FormControl('', [
      //   Validators.required,
      //   Validators.pattern(
      //     /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$/
      //   ),
      // ]),
      role: new FormControl('User', Validators.required),
      cpf: new FormControl('', [
        Validators.required,
      ]),
      phone: new FormControl('', [
        Validators.required,
      ]),
      salary: new FormControl('', Validators.required),
      state: new FormControl('', Validators.required),
      city: new FormControl({value: '', disabled: true}, Validators.required),
      cep: new FormControl('', [
        Validators.required,
        Validators.pattern(/^(\d{2}\.)\d{3}\-\d{3}$/),
        Validators.minLength(10),
        Validators.maxLength(10),
      ]),
      street: new FormControl('', Validators.required),
      number: new FormControl('', Validators.required),
      complement: new FormControl(''),
    });
  }
  getAddress(cep:string) {
    cep = cep.replace(/\D/g, '')
    consultarCep(cep).then(response => {
      this.neighborhood = response.bairro
      this.street = response.logradouro
      this.city = response.localidade
      this.state = response.uf
    })
  }
}
