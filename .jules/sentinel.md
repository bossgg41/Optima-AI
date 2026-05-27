## 2024-05-27 - [Masked Password Input]
**Vulnerability:** Insecure password input without `visualTransformation = PasswordVisualTransformation()`. The auth password text field on `HelpScreen.kt` was in plain text, making it vulnerable to shoulder surfing.
**Learning:** Hardcoded passwords and plain text inputs are common for prototyping/testing but pose critical security risks when left in the codebase.
**Prevention:** Always use `visualTransformation = PasswordVisualTransformation()` alongside `KeyboardType.Password` in Jetpack Compose for sensitive text inputs. Avoid using hardcoded passwords or hardcoded password hashes in the application source code.
