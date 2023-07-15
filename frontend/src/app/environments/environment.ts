import {Environment} from './interface';

export const environment: Environment = {
  production: false,
  serverUrl: 'https://book-find.ru/backend/book-find/api/v1',
  authUrl: '/users/auth',
  registerUrl: '/users/register',
  accountUrl: '/users/account',
  updateUserUrl: '/users/update',
  updatePasswordUrl: '/users/updatePassword',
  personalBooksUrl: '/library',
  booksUrl: '/books',
  reviewsUrl: '/reviews',
  resetPasswordUrl: '/users/resetPassword',
  changePasswordUrl: '/users/changePassword',
  savePasswordUrl: '/users/savePassword'
};
