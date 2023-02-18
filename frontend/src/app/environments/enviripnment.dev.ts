import {Environment} from './interface';

export const environment: Environment = {
  production: false,
  serverUrl: 'http://localhost:8189/book-find/api/v1',
  authUrl: '/users/auth',
  registerUrl: '/users/register',
  accountUrl: '/users/account',
  updateUserUrl: '/users/update',
  updatePasswordUrl: '/users/updatePassword',
  personalBooksUrl: '/library',
  booksUrl: '/books',
  reviewsEndpoint: '/reviews'
};
