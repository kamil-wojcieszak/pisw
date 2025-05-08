import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookComponent } from './book-detail.component';
import {ActivatedRoute} from "@angular/router";

describe('BookListComponent', () => {
  let component: BookComponent;
  let fixture: ComponentFixture<BookComponent>;
  let activatedRouteMock: any;

  beforeEach(() => {
    activatedRouteMock = {
      snapshot: {
        data: {
          books: []
        }
      }
    };
  });

  beforeEach(async () => {
    await TestBed.configureTestingModule({
    imports: [BookComponent],
    providers: [
        { provide: ActivatedRoute, useValue: activatedRouteMock }
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
