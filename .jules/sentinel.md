## 2024-05-23 - Plaintext Password Exposure in Pseudo-Authentication Forms
**Vulnerability:** A password field used for internal authorization state was using a standard `OutlinedTextField` without a visual transformation or keyboard type specified.
**Learning:** Even internal mock or pseudo-authentication forms must adhere to visual transformation standards. Unmasked inputs expose internal passcodes to over-the-shoulder viewing and custom keyboard dictionaries (predictive text caching).
**Prevention:** Apply `visualTransformation = PasswordVisualTransformation()` and `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)` to all sensitive input fields, regardless of backend validation presence.
