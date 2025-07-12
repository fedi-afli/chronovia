export interface User {
  username: string;
  email: string;
  password: string;
}

export interface UserSearchFilter {
  username?: string;
  email?: string;
}