## 2025-05-23 - [Authentication] Secure Input Fields
**Vulnerability:** Even internal or mock authentication inputs were displaying passwords in plaintext.
**Learning:** Client-side authentication hashes shouldn't lead us to ignore basic UI security properties. All sensitive text fields must mask input to prevent shoulder surfing and predictive text caching, avoiding "security theater" solutions that only focus on the hash.
**Prevention:** Always apply `visualTransformation = PasswordVisualTransformation()` and `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)` to password `TextField` components.
