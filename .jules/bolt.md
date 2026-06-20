## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2024-06-20 - DrawScope Local Function and Object Allocation Avoidance
**Learning:** Found that passing a local function reference (`::getPointOffset`) to helper drawing functions inside `DrawScope` triggers `KFunction` allocations on every frame. In addition, iterating and instantiating `Offset` objects in the drawing loop causes unnecessary GC thrashing.
**Action:** Always inline coordinate calculations or pass primitive calculation parameters into drawing helpers instead of passing function references, and avoid instantiating wrapper objects like `Offset` during tight loops in `Canvas`.
