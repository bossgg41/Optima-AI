## 2024-06-02 - Information Disclosure in UI Error Handling
**Vulnerability:** The application was exposing internal exception details (`e.message`) directly to the user interface in fallback strings and UI state variables (`reportAnalysisResponse`, `portfolioAnalysisResponse`, `chatMessages`).
**Learning:** Returning `e.message` or stack traces to UI layers can leak sensitive internal state, network configurations, or API error details to end-users, potentially aiding attackers in recon.
**Prevention:** Always catch exceptions in UI-facing code, log the full error securely using `Log.e(TAG, "...", e)`, and return a generic, safe error message to the user (e.g., "A network or processing error occurred").
