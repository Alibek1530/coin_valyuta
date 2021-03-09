package uz.ali.kurstvalyuta.ModelServer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataModelItem2(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "key")
    val key: Int,

    @ColumnInfo(name = "id1")
    val id: Int,

    @ColumnInfo(name = "Ccy1")
    val Ccy: String,

    @ColumnInfo(name = "CcyNm_EN1")
    val CcyNm_EN: String,

    @ColumnInfo(name = "CcyNm_RU1")
    val CcyNm_RU: String,

    @ColumnInfo(name = "CcyNm_UZ1")
    val CcyNm_UZ: String,

    @ColumnInfo(name = "CcyNm_UZC1")
    val CcyNm_UZC: String,

    @ColumnInfo(name = "Code1")
    val Code: String,

    @ColumnInfo(name = "Date1")
    val Date: String,

    @ColumnInfo(name = "Diff1")
    val Diff: String,

    @ColumnInfo(name = "Nominal1")
    val Nominal: String,

    @ColumnInfo(name = "Rate1")
    val Rate: String
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