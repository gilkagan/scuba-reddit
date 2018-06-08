package com.gilka.scubareddit.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RedditRestAPI : ListingAPI {

    private val redditApi: RedditApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        redditApi = retrofit.create(RedditApi::class.java)
    }

    override fun getListing(reddit: String, after: String, limit: String): Call<RedditListingResponse> {
        return redditApi.getListing(reddit, after, limit)
    }
}
