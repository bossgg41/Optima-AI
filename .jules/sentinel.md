## 2026-06-12 - Password Plaintext Exposure Prevention
**Vulnerability:** Pseudo-authentication dialog for role upgrading exposed the entered password in plaintext.
**Learning:** Even internal mock or pseudo-authentication forms must adhere to visual transformation standards. Failing to do so exposes sensitive input fields to shoulder surfing and predictive text caching of credentials.
**Prevention:** Always apply `visualTransformation = PasswordVisualTransformation()` and `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)` to sensitive input fields (e.g., using Jetpack Compose `TextField` or `OutlinedTextField`).
