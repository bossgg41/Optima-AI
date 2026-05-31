## 2024-05-31 - Information Leakage via Exception Messages
**Vulnerability:** UI states and fallback return messages were directly exposing the raw exception message (`e.message`), which can leak internal stack details, API limits, or configuration logic to the user interface.
**Learning:** Returning `e.message` to a ViewModel state flow directly populates user-facing elements. This breaks defense-in-depth since system errors shouldn't be parsed by users.
**Prevention:** Always log exceptions safely using `Log.e(TAG, "Description", e)` for telemetry, and return generic, non-descript error fallback messages to the UI.
