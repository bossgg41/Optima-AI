package com.example.data.api

import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GeminiClientTest {

    @Test
    fun testGetAnalysisErrorCase() = runTest {
        val originalApiService = GeminiClient.apiService
        val originalApiKey = GeminiClient.apiKey

        try {
            val mockService = object : GeminiApiService {
                override suspend fun generateContent(apiKey: String, request: GenerateContentRequest): GenerateContentResponse {
                    throw RuntimeException("Simulated API Error")
                }
            }

            // Set the mock service
            GeminiClient.apiService = mockService

            // Provide a fake valid key to bypass the fallback simulation
            GeminiClient.apiKey = "VALID_FAKE_KEY"

            // Trigger the function
            val result = GeminiClient.getAnalysis("test prompt")

            // Verify that the result contains our mocked exception message, thus testing the error case
            assertTrue("Expected result to contain 'AI Service Error'", result.contains("AI Service Error"))
            assertTrue("Expected result to contain 'Simulated API Error'", result.contains("Simulated API Error"))
            assertTrue("Expected result to fallback to local intelligence", result.contains("DeepOptima Quantitative Summary"))

        } finally {
            // Restore the original service and key
            GeminiClient.apiService = originalApiService
            GeminiClient.apiKey = originalApiKey
        }
    }
}
