package uz.ali.kurstvalyuta.utils

class DataMoonDay {
    fun getMoon(moon: String): String {
        var Mon: String = ""
        when (moon) {
            "01" -> {
                Mon = "Yanvar"
            }
            "02" -> {
                Mon = "Fevral"
            }
            "03" -> {
                Mon = "Mart"
            }
            "04" -> {
                Mon = "Aprel"
            }
            "05" -> {
                Mon = "May"
            }
            "06" -> {
                Mon = "Iyun"
            }
            "07" -> {
                Mon = "Iyul"
            }
            "08" -> {
                Mon = "Avgust"
            }
            "09" -> {
                Mon = "Sentabr"
            }
            "10" -> {
                Mon = "Oktabr"
            }
            "11" -> {
                Mon = "Noyabr"
            }
            "12" -> {
                Mon = "Dekabr"
            }
            else -> {
                Mon = "xato"
            }
        }
        return Mon
    }

}