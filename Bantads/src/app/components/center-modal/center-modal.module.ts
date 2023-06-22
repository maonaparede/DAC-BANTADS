import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CenterModalComponent } from './center-modal.component';

@NgModule({
  declarations: [
    CenterModalComponent,
  ],
  imports: [
    CommonModule,
  ],
  exports: [
    CenterModalComponent,
  ]
})
export class ModalModule {

}
