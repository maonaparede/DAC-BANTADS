import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss', '../../../../_utils.scss']
})
export class ListComponent {

  constructor(private http: HttpClient) {}

  fetchData() {
    this.http.get('http://localhost:3000/api/data').subscribe(
        (response) => {
          // Manipule a resposta aqui
        },
        (error) => {
          // Manipule o erro aqui
        }
    );
  }
}
