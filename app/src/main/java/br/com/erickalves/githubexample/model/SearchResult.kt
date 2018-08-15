package br.com.erickalves.githubexample.model

data class SearchResult(
        val total_count: Int = 0,
        val incomplete_results: Boolean = false,
        val items: List<Repository> = listOf()
)