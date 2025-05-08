import { ComponentFixture, TestBed } from '@angular/core/testing';

import {ActivatedRoute} from "@angular/router";
import {BookEditComponent} from "./book-edit.component";

describe('BookListComponent', () => {
  let component: BookEditComponent;
  let fixture: ComponentFixture<BookEditComponent>;
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
    imports: [BookEditComponent],
    providers: [
        { provide: ActivatedRoute, useValue: activatedRouteMock }
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
