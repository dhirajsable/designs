package com.example.apiusage.config;

import java.util.HashMap;
import java.util.Map;

public class MaxSubstringFrequency {

    // Method to find the maximum frequency of substrings meeting the specified conditions
    public static int findMaxFrequencySubstring(
            String input, int minLength, int maxLength, int maxUnique) {
        Map<String, Integer> substringFrequency =
                new HashMap<>(); // Map to store substring frequencies
        int maxFrequency = 0; // Variable to track the maximum frequency

        // Outer loop to define the starting point of substrings
        for (int start = 0; start < input.length(); start++) {
            Map<Character, Integer> charCount =
                    new HashMap<>(); // Map to track character counts in the current window

            // Inner loop to extend the substring from the starting point
            for (int end = start; end < input.length() && (end - start + 1) <= maxLength; end++) {
                char currentChar =
                        input.charAt(end); // Current character being added to the substring
                charCount.put(
                        currentChar,
                        charCount.getOrDefault(currentChar, 0) + 1); // Update character count

                // If the number of unique characters exceeds maxUnique, break out of the loop
                if (charCount.size() > maxUnique) {
                    break;
                }

                int currentLength = end - start + 1; // Calculate the current substring length
                // Check if the substring length is within the allowed range
                if (currentLength >= minLength) {
                    String substring = input.substring(start, end + 1); // Extract the substring
                    // Update the frequency of the substring in the map
                    substringFrequency.put(
                            substring, substringFrequency.getOrDefault(substring, 0) + 1);
                    // Update the maximum frequency if needed
                    maxFrequency = Math.max(maxFrequency, substringFrequency.get(substring));
                }
            }
        }

        return maxFrequency; // Return the maximum frequency of substrings
    }

    public static void main(String[] args) {
        // Input string and constraints
        String input = "ababab";
        int minLength = 2; // Minimum length of substrings
        int maxLength = 3; // Maximum length of substrings
        int maxUnique = 4; // Maximum number of unique characters allowed

        // Find and print the maximum frequency of valid substrings
        int result = findMaxFrequencySubstring(input, minLength, maxLength, maxUnique);
        System.out.println("Maximum Frequency: " + result);
    }
}
