## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.
## 2026-06-15 - Compose Canvas DrawScope Allocations
**Learning:** Found heavy allocations happening inside Compose Canvas DrawScope during ComparativeForecastChart rendering, causing GC thrashing. Discovered Path/PathEffect instantiation, KFunction allocation (::getPointOffset), and iterative Offset instantiations.
**Action:** Hoist Path and PathEffect out of DrawScope using remember. Avoid KFunction allocations inside DrawScope and calculate x and y coordinates natively to prevent object allocations on every frame.
