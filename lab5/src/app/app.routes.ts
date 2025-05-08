import {Routes} from '@angular/router';
import {BookListComponent} from "./books/components/book-list/book-list.component";
import {bookListResolver} from "./books/resolvers/book-list.resolver";
import {bookResolver} from "./books/resolvers/book.resolver";
import {BookComponent} from "./books/components/book-detail/book-detail.component";
import {BookEditComponent} from "./books/components/book-edit/book-edit.component";
import {reviewsResolver} from "./books/resolvers/reviews.resolver";

export const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        redirectTo: '/books'
    },
    {
        path: 'books',
        component: BookListComponent,
        resolve: {
            books: bookListResolver
        }
    },
    {
        path: 'books/:bookId/reviews',
        component: BookComponent,
        resolve: {
            book: bookResolver,
            reviews: reviewsResolver
        }
    },
    {
        path: 'books/:bookId/edit',
        component: BookEditComponent,
        resolve: {
            book: bookResolver
        }
    }
];
