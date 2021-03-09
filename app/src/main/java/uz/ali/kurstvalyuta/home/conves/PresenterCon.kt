package uz.ali.kurstvalyuta.home.conves

import uz.ali.kurstvalyuta.network.ApiService
import uz.ali.kurstvalyuta.room.UserDao

class PresenterCon {
    lateinit var api: ApiService
    lateinit var roomDao: UserDao

//    fun aa(date:String) {
//        roomDao = AppDatabase.getInstance()!!
//        api = NetworkConnection.getInstance().getApiClient()
//
//        api.getHomeRubOneDay("USD", "2021-04-19").enqueue(object : Callback<DataModelItem> {
//            override fun onFailure(call: Call<DataModelItem>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<DataModelItem>, response: Response<DataModelItem>) {
//                if (response.isSuccessful && response.code() == 200) {
//                //    roomDao?.insert(response.body()!!)
//                  //  Toast.makeText(this,""+response.body(),Toast.LENGTH_SHORT).show()
//
//                }
//            }
//
//        })
//        api.getHomeListAllFinishDay().enqueue(object : Callback<DataModel> {
//
//            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
//                if (response.isSuccessful && response.code() == 200) {
//                    roomDao?.insert(response.body()!!)
//                }
//            }
//
//            override fun onFailure(call: Call<DataModel>, t: Throwable) {
//
//            }
//
//        })
//
//    }

    fun getDate(date: String): String {
        //  2020-12-36 return

        //05.01.2021 date
        var day = date.substring(0..1).toInt()
        var moon = date.substring(3..4)
        var year = date.substring(6..9)
        if (day - 7 >= 0) {
            var dayRet = year + "-" + moon + "-" + (day - 7)
            return dayRet
        } else {
            var dayRet = year + "-" + moon + "-01"
            return dayRet
        }
    }
}