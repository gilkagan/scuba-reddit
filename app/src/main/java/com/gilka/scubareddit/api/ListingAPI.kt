package com.gilka.scubareddit.api

import retrofit2.Call

interface ListingAPI {
    fun getListing(reddit: String, after: String, limit: String): Call<RedditListingResponse>
}