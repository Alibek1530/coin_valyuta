package uz.ali.kursvalyuta.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import uz.ali.kursvalyuta.ModelServer.DataModel
import uz.ali.kursvalyuta.ModelServer.DataModel2

interface ApiService {

    @GET("json/")
    fun getHomeListAllFinishDay(): Call<DataModel>

    @GET("json/all/{data}/")
    fun getAllDavlatListData(@Path("data") data: String): Call<DataModel>

    @GET("json/{rub}/{data}/")
    fun getHomeRubOneDay(@Path("rub") rub: String,@Path("data") data: String): Call<DataModel2>
  // json/all/2019-02-21/
}