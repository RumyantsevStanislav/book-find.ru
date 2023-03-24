import {Environment} from './interface';

export const environment: Environment = {
  production: false,
  serverUrl: '',
  authUrl: '/users/auth',
  registerUrl: '/users/register',
  accountUrl: '/users/account',
  updateUserUrl: '/users/update',
  updatePasswordUrl: '/users/updatepassword',
  personalBooksUrl: '/library',
  booksUrl: '/books',
  reviewsUrl: '/reviews',
  resetPasswordUrl: '/users/resetPassword',
  changePasswordUrl: '/users/changePassword',
  savePasswordUrl: '/users/savePassword'
};
