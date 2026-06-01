## 2025-02-23 - [Security Theater in Android]
**Vulnerability:** Client-side RBAC using a hardcoded SHA-256 hash.
**Learning:** Moving client-side hardcoded authentication hashes or secrets into `BuildConfig` via the Secrets Gradle plugin (e.g., `.env`) provides zero additional security for an Android app, as they remain easily extractable from the compiled APK bytecode.
**Prevention:** Avoid "security theater" fixes. Focus on genuine client-side mitigations like UI input masking (`PasswordVisualTransformation`) or safe error handling when true secure server-side authentication is unavailable.
