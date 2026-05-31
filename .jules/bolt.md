## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2026-05-31 - Jetpack Compose Canvas GC Thrashing
**Learning:** Instantiating `Path` and `PathEffect` inside a `Canvas` block in Jetpack Compose leads to GC thrashing because the `Canvas` body is repeatedly called during rendering frames.
**Action:** Always hoist object creations like `Path` and `PathEffect` using `remember` outside of the `Canvas` drawing scope and reuse them by calling `.reset()` instead of allocating new instances.
