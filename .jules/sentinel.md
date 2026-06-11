## 2024-05-24 - Masking Mock Authentication Fields
**Vulnerability:** Internal mock or pseudo-authentication forms left plaintext, potentially exposing credentials to shoulder surfing or predictive text caching.
**Learning:** Even pseudo-authentication forms (like those in HelpScreen.kt) must adhere to visual transformation standards.
**Prevention:** Always apply `visualTransformation = PasswordVisualTransformation()` and `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)` to sensitive input fields.
