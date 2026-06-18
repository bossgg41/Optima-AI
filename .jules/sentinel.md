## 2024-05-23 - Plaintext Password Exposure in Mock Auth
**Vulnerability:** The password field in the `HelpScreen` authentication dialog lacked `visualTransformation` and `keyboardOptions`, exposing the password in plaintext and allowing it to be cached by the keyboard.
**Learning:** Even mock or internal authentication flows must adhere to security standards regarding sensitive input fields to prevent accidental exposure and to build robust security habits.
**Prevention:** Always apply `visualTransformation = PasswordVisualTransformation()` and `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)` to all sensitive input fields (`TextField`, `OutlinedTextField`), regardless of their perceived criticality in the UI.
