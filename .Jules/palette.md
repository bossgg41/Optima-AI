## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-20 - Adding Empty State for Chat Screen
**Learning:** The `LazyColumn` for chat messages did not have a fallback for when the initial `messages` state is empty. Additionally, hooking into `KeyboardOptions` and `KeyboardActions` with `ImeAction.Send` allows virtual keyboard input submission to function identically to an on-screen "Send" Fab button.
**Action:** When implementing any dynamically loaded user messaging or item boards, always provide an empty state (with an icon and helper label) and hook up proper keyboard interaction events if there's text input.
