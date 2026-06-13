## 2024-06-13 - [MEDIUM] Fix Exception Details Leakage to UI
**Vulnerability:** UI-facing components (ViewModels, API clients) catching exceptions and displaying their messages/stacktraces directly to users (e.g. `_reportAnalysisResponse.value = "System processing failure: ${e.message}"`).
**Learning:** Returning `e.message` or `e.stack` to the UI can expose sensitive internal system details, API specifics, or configuration paths to users.
**Prevention:** Always log the full exception securely using `android.util.Log` and return generic, safe error messages to the user (e.g. "System processing failure. Please try again later.").
