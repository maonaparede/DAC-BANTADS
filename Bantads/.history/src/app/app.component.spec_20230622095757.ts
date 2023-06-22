import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { AuthService } from './services/auth.service';
import { IUser, IUserLogin } from './DTOs/IUser';
import { IUserAutocadastro } from './DTOs/IUserAutocadastro';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';


describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        AppComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'Bantads'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('Bantads');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    //expect(compiled.querySelector('.content span')?.textContent).toContain('Bantads app is running!');
  });
});

describe('AuthServiice', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService],
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should Login', () => {
    const user: IUserLogin = {
      email: 'root@root',
      senha: 'root'
    };
    service.login2(user).subscribe(
      response => {
        console.log('Status da requisição:', response.status);
        // Faça algo com a resposta
        expect(false).toBeTruthy();
      },
      error => {
        console.error('Erro na requisição:', error);
        // Trate o erro
      }
    );

  });

  it('should Autocadastro', () => {
    const user: IUserAutocadastro = {
      nome: "João Silva",
      email: "joao@example.com",
      telefone: "(11) 98765-4321",
      salario: 5000,
      cpf: "123.456.789-00",
      logradouro: "Rua das Flores",
      complemento: "Apto 123",
      cidade: "São Paulo",
      estado: "SP",
      cep: "12345-678",
      tipo: "Cliente",
      numero: 123
    };
    service.createAccount2(user).subscribe(
      response => {
        console.log('Status da requisição:', response.status);
        // Faça algo com a resposta
      },
      error => {
        console.error('Erro na requisição:', error);
        // Trate o erro
      }
    );
    
  });

});