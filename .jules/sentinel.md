
## 2024-06-04 - Missing Password Masking in UI
**Vulnerability:** The password authentication field in the HelpScreen (`OutlinedTextField`) was not masking input characters, leaving the password visible in plaintext on the screen.
**Learning:** In Jetpack Compose, `OutlinedTextField` does not automatically mask sensitive input. Omitting `visualTransformation = PasswordVisualTransformation()` leaves the application vulnerable to shoulder surfing and credential exposure on UI.
**Prevention:** Always apply `visualTransformation = PasswordVisualTransformation()` and `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)` to all password, PIN, and sensitive credential input fields across the application.
