## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2025-02-12 - Added Keyboard Action mapping for ImeAction.Send to Chat input
**Learning:** For mobile text inputs, particularly conversational UI chat boxes, missing explicit `keyboardOptions` with an associated `keyboardActions` mapping causes friction, as users cannot natively "Send" via the keyboard. Adding `ImeAction.Send` and hooking it up directly to the message send function creates a smoother and expected UX experience.
**Action:** Always provide explicit keyboardOptions (like `ImeAction.Send` or `ImeAction.Done`) and corresponding `keyboardActions` handlers on `OutlinedTextField` or `TextField` instances used for submissions.
