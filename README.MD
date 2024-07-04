
### Връзка между екраните
```mermaid
graph TD;
    SplashActivity --> LoginActivity;
    LoginActivity --> RegisterActivity;
    RegisterActivity --> LoginActivity;
    LoginActivity --> ForgotPasswordActivity;
    ForgotPasswordActivity --> LoginActivity;
```