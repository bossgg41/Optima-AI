## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2026-06-08 - Added Chat Empty State & Keyboard Send Action
**Learning:** Implementing an empty state inside a chat interface provides better guidance, while supporting the keyboard 'Send' action significantly improves conversational UX on mobile devices.
**Action:** Always provide empty states for dynamic lists and configure keyboard actions for text inputs.
