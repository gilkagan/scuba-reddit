package com.gilka.scubareddit.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApi {

    @GET("/r/{reddit}.json")
    fun getListing(
            @Path("reddit") reddit : String,
            @Query("after") after: String,
            @Query("limit") limit: String): Call<RedditListingResponse>
}