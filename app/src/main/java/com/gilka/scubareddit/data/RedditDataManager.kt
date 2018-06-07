package com.gilka.scubareddit.data

import com.gilka.scubareddit.api.ListingAPI
import com.gilka.scubareddit.models.RedditEntry
import com.gilka.scubareddit.api.RedditRestAPI
import com.gilka.scubareddit.models.RedditListing
import rx.Observable

class RedditDataManager(private val reddit: String, private val api: ListingAPI = RedditRestAPI()) {

    fun getListing(after: String, limit: String = "25"): Observable<RedditListing> {
        return Observable.create  {
            subscriber ->
            val callResponse = api.getListing(reddit, after, limit)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val entries = response.body().data.children.map {
                    val item = it.data
                    RedditEntry(item.title, item.thumbnail, item.url)
                }
                subscriber.onNext(
                        RedditListing(
                                response.body().data.after ?: "",
                                response.body().data.before ?: "",
                                entries))
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

}
