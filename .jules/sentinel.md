
## 2025-05-24 - [UI Password Input Masking]
**Vulnerability:** The password entry field in the HelpScreen (`authPassword`) was an `OutlinedTextField` that did not specify a `visualTransformation` or `KeyboardType.Password`. This resulted in the user's password being displayed in plaintext during entry, and potentially cached or corrected by the keyboard's predictive text.
**Learning:** Even though the entered password was hashed before validation and check, the UI component itself failed to mask the input. Any on-screen observer or background keyboard telemetry could intercept or read the plaintext credentials.
**Prevention:** Always apply `visualTransformation = PasswordVisualTransformation()` and `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)` on `OutlinedTextField` or `TextField` instances intended for password or sensitive secret entry to ensure masking and disable predictive text.
