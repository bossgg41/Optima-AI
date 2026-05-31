## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.
## 2024-05-31 - Keyboard Options for Better UX
**Learning:** Adding the proper KeyboardOptions to TextField/OutlinedTextField components (e.g. KeyboardCapitalization.Characters for stock tickers, and KeyboardType.Decimal for numbers) dramatically improves UX on mobile by preventing users from having to manually switch keyboard types.
**Action:** Always set the correct KeyboardOptions matching the expected data format for TextFields in Compose to remove friction in data entry.
