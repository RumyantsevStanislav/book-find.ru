export const regExpPatterns = {
  phonePattern: RegExp('^(\\+7|7|8)?\\d{10}$'),
  emailPattern: RegExp('^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$'),
  passwordPattern: RegExp('^.*(?=.*\\d)(?=.*[a-zа-яё])(?=.*[A-ZА-ЯЁ]).*$')
}
