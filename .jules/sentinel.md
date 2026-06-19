## 2024-05-24 - Unmasked Sensitive Input (Android)
**Vulnerability:** A password entry field (`OutlinedTextField` in `HelpScreen`) was collecting sensitive authentication data in plaintext without input masking.
**Learning:** Even internal mock or pseudo-authentication forms must adhere to visual transformation standards to prevent over-the-shoulder plaintext exposure and caching in predictive text dictionaries.
**Prevention:** Always apply `visualTransformation = PasswordVisualTransformation()` and `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)` to sensitive input fields in Jetpack Compose.
