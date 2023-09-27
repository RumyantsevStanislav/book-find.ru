// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

import {Environment} from "./interface";

export const environment: Environment = {
  production: false,
  apiKey: 'Вставить ключ реальный',
  serverUrl: 'http://localhost:8189/book-find/api/v1',
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

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
