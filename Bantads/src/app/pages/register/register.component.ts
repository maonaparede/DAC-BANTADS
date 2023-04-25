import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { consultarCep } from 'correios-brasil';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';
import { concatAll, last, lastValueFrom, map, of } from 'rxjs';
import { AccountModel } from '../../models/account.model';
import { ClientService } from '../../services/client.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss', './../../../_utils.scss'],
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  address = '';
  neighborhood = '';
  city = '';
  state = '';

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private clientService: ClientService,
    private router: Router,
  ) {}

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

  async register(): Promise<void> {
    let invalidUser: boolean = false;
    await lastValueFrom(
      this.userService.getUserByCPF(this.registerForm.get('cpf')?.value).pipe(
        map((users: User[]) => {
          if (users.length > 0) {
            invalidUser = true;
            confirm('CPF já foi utilizado!');
          }
        }),
      ),
    );
    await lastValueFrom(
      this.userService.getUserByEmail(this.registerForm.get('email')?.value).pipe(
        map((users: User[]) => {
          if (users.length > 0) {
            invalidUser = true;
            confirm('Email já foi utilizado!');
          }
        }),
      ),
    );
    if (invalidUser) return;

    const user = new User();
    user.name = this.registerForm.get('name')?.value;
    user.password = this.registerForm.get('password')?.value;
    user.email = this.registerForm.get('email')?.value;
    user.role = 'user';
    user.cpf = this.registerForm.get('cpf')?.value;
    user.phone = this.registerForm.get('phone')?.value;
    user.state = this.registerForm.get('state')?.value;
    user.city = this.registerForm.get('city')?.value;
    user.cep = this.registerForm.get('cep')?.value;
    user.street = this.registerForm.get('street')?.value;
    user.number = this.registerForm.get('number')?.value;
    user.complement = this.registerForm.get('complement')?.value;

    this.userService.create(user).subscribe(() => {
      let user: User;
      let managers: User[];
      let minorManager: User;

      const $getUserByCpfObservable = this.userService
        .getUserByCPF(this.registerForm.get('cpf')?.value)
        .pipe(map((users: User[]) => (user = users[0])));
      const $getManagersObservable = this.userService.getManagers().pipe(map((users: User[]) => (managers = users)));
      const $getMinorManagerObservable = this.clientService.getAll().pipe(
        map((accounts: AccountModel[]) => {
          let minorAccount: Number;
          managers.map((manager: User) => {
            let count = accounts.filter((account: AccountModel) => account.idManager === manager.id).length;
            if (!minorAccount || count < minorAccount) {
              minorManager = manager;
            }
          });
        }),
      );

      const tasks$ = of($getUserByCpfObservable, $getManagersObservable, $getMinorManagerObservable);

      tasks$.pipe(concatAll(), last()).subscribe({
        next: () => {
          const account = new AccountModel();
          account.idUser = user.id;
          account.date = new Date();
          account.salary = +this.registerForm.get('salary')?.value;
          if (account.salary > 2000) account.limit = +this.registerForm.get('salary')?.value / 2;
          account.active = false;
          account.balance = 0;
          account.idManager = minorManager.id;

          this.clientService.create(account).subscribe(() => {
            confirm('Conta criada com sucesso!');
            this.router.navigate(['/login']);
          });
        },
        error: () => {
          // send email to user
        },
      });
    });
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
