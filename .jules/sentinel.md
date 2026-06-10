## 2024-05-23 - Plaintext Password Exposure in Auth Dialog
**Vulnerability:** The password field in the RBAC authentication dialog (`HelpScreen.kt`) was implemented using an `OutlinedTextField` without a visual transformation, causing the user's password to be typed and displayed in plaintext.
**Learning:** Even internal or pseudo-authentication forms must be properly secured to avoid leaking sensitive inputs visually and to prevent software keyboards from caching credentials.
**Prevention:** Always apply `visualTransformation = PasswordVisualTransformation()` and `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)` to all sensitive text input fields.
