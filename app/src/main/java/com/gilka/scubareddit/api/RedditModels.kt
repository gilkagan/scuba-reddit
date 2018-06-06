package com.gilka.scubareddit.api

class RedditListingResponse(val data: RedditDataResponse)

class RedditDataResponse(
        val children: List<RedditChildrenResponse>,
        val after: String?,
        val before: String?
)

class RedditChildrenResponse(val data: RedditListingDataResponse)

class RedditListingDataResponse(
        val title: String,
        val thumbnail: String,
        val url: String
)