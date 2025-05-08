import {Component, OnInit} from '@angular/core';
import {Book} from '../../model/book';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {FormBuilder, FormGroup, Validators, ReactiveFormsModule} from '@angular/forms';
import {BooksService} from '../../services/books.service';
import {CommonModule} from '@angular/common';

@Component({
    selector: 'bs-book-edit',
    templateUrl: './book-edit.component.html',
    standalone: true,
    imports: [ReactiveFormsModule, CommonModule]
})
export class BookEditComponent implements OnInit {
    book!: Book;
    editForm!: FormGroup;
    private initialFormValueString!: string;

    constructor(
        private readonly activatedRoute: ActivatedRoute,
        private readonly router: Router,
        private readonly fb: FormBuilder,
        private readonly booksService: BooksService
    ) {
    }

    ngOnInit(): void {
        this.book = this.activatedRoute.snapshot.data['book'];

        if (!this.book) {
            console.error('Book data not resolved!');
            this.router.navigate(['/books']);
            return;
        }
        this.initForm();
    }

    private initForm(): void {
        this.editForm = this.fb.group({
            title: [this.book.title, [Validators.required, Validators.maxLength(50)]],
            author: [this.book.author, [Validators.required, Validators.maxLength(50)]],
            year: [
                this.book.year,
                [Validators.required, Validators.min(1000), Validators.max(2023)]
            ],
            description: [this.book.description || '', [Validators.maxLength(1000)]]
        });
        this.storeInitialFormValue();
    }

    private storeInitialFormValue(): void {
        this.initialFormValueString = JSON.stringify(this.editForm.value);
    }

    get title() {
        return this.editForm.get('title');
    }

    get author() {
        return this.editForm.get('author');
    }

    get year() {
        return this.editForm.get('year');
    }

    get description() {
        return this.editForm.get('description');
    }

    isFormUnchanged(): boolean {
        if (!this.editForm) {
            return true;
        }
        return this.initialFormValueString === JSON.stringify(this.editForm.value);
    }

    onSave(): void {
        if (this.editForm.valid && !this.isFormUnchanged()) {
            const formValues = this.editForm.value;
            const updatedBook: Book = {
                id: this.book.id,
                title: formValues.title,
                author: formValues.author,
                year: Number(formValues.year),
                description: formValues.description
            };

            this.booksService.saveBook(updatedBook).subscribe({
                next: () => {
                    this.router.navigate(['/books']);
                },
                error: (err) => {
                    console.error('Error saving book:', err);
                }
            });
        }
    }

    onCancel(): void {
        this.router.navigate(['/books']);
    }
}