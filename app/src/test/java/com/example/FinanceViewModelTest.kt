package com.example

import com.example.data.FinanceViewModel
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.system.measureTimeMillis

class FinanceViewModelTest {

    @Test
    fun testRegexReDoSVulnerability() {
        val vm = FinanceViewModel()

        // Construct a string that would cause catastrophic backtracking in the old regex
        // The old regex was: \d+[\d,\s]*\.?\d*
        // The pattern is: start with a digit, then many repeats of digits/commas/spaces, ending with something else
        val sb = StringBuilder("1")
        for (i in 0 until 50) {
            sb.append(" 1 1,")
        }
        sb.append("a")
        val maliciousPayload = sb.toString()

        // Measure execution time
        val duration = measureTimeMillis {
            // importDocumentData will attempt to match the regex against our payload through heuristicExtract
            vm.importDocumentData("text", "malicious.txt", maliciousPayload)
        }

        // On a vulnerable regex, this would hang indefinitely (or take many seconds/minutes).
        // With the fix, it should complete in a few milliseconds.
        // We assert it completes in under 500ms as a reasonable upper bound for a unit test.
        println("Regex execution duration: \$duration ms")
        assertTrue("Regex parsing took too long (\$duration ms), possible ReDoS vulnerability", duration < 500)
    }
}
