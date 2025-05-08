import {ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot} from "@angular/router";
import {Book} from "../model/book";
import {inject} from "@angular/core";
import {BooksService} from "../services/books.service";
import {ReviewsService} from "../services/reviews.service";
import {Review} from "../model/review";

export const reviewsResolver: ResolveFn<Review[]> = (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
) => {
    return inject(ReviewsService).getReviewsForBook(Number(route.paramMap.get('bookId')));
};