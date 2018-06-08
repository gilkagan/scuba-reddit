package com.gilka.scubareddit.mvp.listing.model

import com.gilka.scubareddit.api.RedditRestAPI
import com.gilka.scubareddit.api.RedditListingResponse
import com.gilka.scubareddit.models.RedditEntry
import com.gilka.scubareddit.mvp.listing.contracts.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListingModel : com.gilka.scubareddit.mvp.listing.contracts.Model {

    override fun getRedditEntries(onFinishedListener: Model.OnFinishedListener, channel: String, afterTag: String) {

        val api = RedditRestAPI()
        val call = api.getListing(channel, afterTag, "25")
        call.enqueue(object : Callback<RedditListingResponse> {
            override fun onResponse(call: Call<RedditListingResponse>?, response: Response<RedditListingResponse>) {
                val entries = response.body().data.children.map {
                    val item = it.data
                    RedditEntry(item.title, item.thumbnail, item.url)
                }

                val after = response.body().data.after
                onFinishedListener.onFinished(entries, after!!)
            }

            override fun onFailure(call: Call<RedditListingResponse>?, t: Throwable) {
                onFinishedListener.onFailure(t)
            }

        })
    }
}