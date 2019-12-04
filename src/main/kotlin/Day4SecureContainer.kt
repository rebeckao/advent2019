class Day4SecureContainer {

    fun numberOfPasswordsMatching(passwords: List<String>): Long {
        return passwords.stream()
            .filter { it.length == 6 }
            .filter { sorted(it) == it }
            .filter { hasAtLeastTwoOfSomething(it) }
            .count()
    }

    private fun hasAtLeastTwoOfSomething(password: String): Boolean {
        return password.groupBy { it }.values.any { it.size >= 2 }
    }

    fun numberOfPasswordsMatchingWithOnlyTwoOfSomething(passwords: List<String>): Long {
        return passwords.stream()
            .filter { it.length == 6 }
            .filter { sorted(it) == it }
            .filter { hasExactlyTwoOfSomething(it) }
            .count()
    }

    private fun hasExactlyTwoOfSomething(password: String): Boolean {
        return password.groupBy { it }.values.any { it.size == 2 }
    }

    private fun sorted(originalString: String): String {
        return originalString.toCharArray().sorted().joinToString("")
    }
}