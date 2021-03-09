package uz.ali.kurstvalyuta.ModelServer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataModelItem(

    @ColumnInfo(name = "Ccy")
    val Ccy: String,

    @ColumnInfo(name = "CcyNm_EN")
    val CcyNm_EN: String,

    @ColumnInfo(name = "CcyNm_RU")
    val CcyNm_RU: String,

    @ColumnInfo(name = "CcyNm_UZ")
    val CcyNm_UZ: String,

    @ColumnInfo(name = "CcyNm_UZC")
    val CcyNm_UZC: String,

    @ColumnInfo(name = "Code")
    val Code: String,

    @ColumnInfo(name = "Date")
    val Date: String,

    @ColumnInfo(name = "Diff")
    val Diff: String,

    @ColumnInfo(name = "Nominal")
    val Nominal: String,

    @ColumnInfo(name = "Rate")
    val Rate: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int
)
//
//@Entity
//data class User(
//    var userId: Int,
//    @ColumnInfo(name = "user_id")
//    @PrimaryKey(autoGenerate = false)
//    var id: Int,
//    @ColumnInfo(name = "title")
//    var title: String,
//    @ColumnInfo(name = "sodi")
//    @SerializedName(value = "body")
//    var massage: String
//)