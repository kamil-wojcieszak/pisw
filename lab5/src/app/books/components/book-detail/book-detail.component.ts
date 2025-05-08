import {Component, OnInit} from '@angular/core';
import {Book} from '../../model/book';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {Review} from "../../model/review";
import {ReviewItemComponent} from "../review-item/review-item.component";
import {NgForOf, NgIf} from "@angular/common";

@Component({
    selector: 'bs-book-detail',
    templateUrl: './book-detail.component.html',
    styleUrls: ['./book-detail.component.scss'],
    standalone: true,
    imports: [RouterLink, ReviewItemComponent, NgIf, NgForOf]
})
export class BookComponent implements OnInit {

    book: Book;
    reviews: Review[] = [];

    constructor(private readonly activatedRoute: ActivatedRoute) {
        this.book = this.activatedRoute.snapshot.data['book'];
        this.reviews = this.activatedRoute.snapshot.data['reviews'];
    }

    ngOnInit(): void {
        this.reviews = this.activatedRoute.snapshot.data['reviews'];
    }
}