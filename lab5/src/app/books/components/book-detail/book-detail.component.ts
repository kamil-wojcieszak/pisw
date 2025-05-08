import {Component} from '@angular/core';
import {Book} from '../../model/book';
import {ActivatedRoute, RouterLink} from '@angular/router';

@Component({
    selector: 'bs-book-detail',
    templateUrl: './book-detail.component.html',
    styleUrls: ['./book-detail.component.scss'],
    standalone: true,
    imports: [RouterLink]
})
export class BookComponent {

    readonly book: Book;

    constructor(private readonly activatedRoute: ActivatedRoute) {
        this.book = this.activatedRoute.snapshot.data['book'];
    }
}
