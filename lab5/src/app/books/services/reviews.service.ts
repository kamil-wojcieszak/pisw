import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Review} from '../model/review';
import {Book} from "../model/book";

const apiPrefix = '/api/reviews';

@Injectable({
    providedIn: 'root'
})
export class ReviewsService {

    constructor(private readonly http: HttpClient) {
    }

    getReviewsForBook(bookId: number): Observable<Review[]> {
        return this.http.get<Review[]>(`${apiPrefix}?forBook=${bookId}`);
    }

    saveReview(review: Review): Observable<Book> {
        return this.http.put<Book>(`${apiPrefix}`, review);
    }
}