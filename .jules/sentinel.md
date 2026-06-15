
## 2024-06-15 - Securing Jetpack Compose Password Inputs
**Vulnerability:** A mock authentication dialog in `HelpScreen.kt` collected user passwords via an `OutlinedTextField` without obfuscating the text or informing the software keyboard about the sensitive nature of the input.
**Learning:** This exposes passwords in plaintext on the screen and can lead to them being cached by the user's predictive text keyboard dictionary.
**Prevention:** Always apply `visualTransformation = PasswordVisualTransformation()` and `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)` to `TextField` or `OutlinedTextField` components that handle passwords or sensitive authentication tokens in Jetpack Compose.
