package sinno.seacs.mvvmappdemo.api

import retrofit2.Response
import retrofit2.http.GET
import sinno.seacs.mvvmappdemo.database.Comments

interface Api {
    @GET("comments")
    suspend fun getComments() : Response<List<Comments>>
}